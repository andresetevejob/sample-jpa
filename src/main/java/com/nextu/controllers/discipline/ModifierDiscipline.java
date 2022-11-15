package com.nextu.controllers.discipline;

import java.io.IOException;
import java.util.List;
import com.nextu.entities.Discipline;
import com.nextu.entities.Sport;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.UserTransaction;

/**
 * Servlet implementation class ModifierDiscipline
 */
@WebServlet("/modifierDiscipline")
public class ModifierDiscipline extends HttpServlet {
   private static final long serialVersionUID = 1L;
   @PersistenceContext(unitName = "sample-jpa")
   private EntityManager em;
   @Resource
   private UserTransaction userTransaction;

   /**
    * Default constructor.
    */
   public ModifierDiscipline() {
      // TODO Auto-generated constructor stub
   }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      Long codeDiscipline = Long.valueOf(request.getParameter("codeDiscipline"));
      Discipline discipline = em.find(Discipline.class, codeDiscipline);
      request.setAttribute("discipline", discipline);
      String errorMessage = request.getParameter("errorMessage");
      request.setAttribute("errorMessage", errorMessage);
      TypedQuery<Sport> query = em.createQuery("SELECT s FROM Sport s", Sport.class);
      List<Sport> sports = query.getResultList();
      request.setAttribute("sports", sports);
      this.getServletContext().getRequestDispatcher("/modifier-discipline.jsp").forward(request,
            response);
   }

   /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      // TODO Auto-generated method stub
      String message;
      String uriRedirect = "modifierDiscipline?codeDiscipline="
            + request.getParameter("codeDiscipline") + "error=%s";
      Discipline discipline;
      Sport sport;
      discipline = getDiscipline(request);
      sport = getSport(request);
      if (discipline == null) {
         message = "Aucune discipline trouvé";
         redirectWithErrorMessage(response, uriRedirect, message);
      }
      if (sport == null) {
         message = "Aucune sport trouvé";
         redirectWithErrorMessage(response, uriRedirect, message);
      }
      String libelle = request.getParameter("libelle");

      if (libelle == null || libelle.isEmpty() || libelle.isBlank()) {
         message = "Veuillez renseignez le libellé de la discipline";
         redirectWithErrorMessage(response, uriRedirect, message);

      } else {
         boolean transactionOk = false;
         try {
            userTransaction.begin();
            discipline.setLibelle(libelle);
            discipline.setSport(em.merge(sport));
            em.merge(discipline);
            transactionOk = true;
         } catch (Exception e) {
            System.out.print("Une erreur est survennue lors de l'enregistrement");
         } finally {
            try {
               if (transactionOk) {
                  userTransaction.commit();
                  response.sendRedirect("disciplines");
               } else {
                  message =
                        "Une erreur est survenue lors de l'enregistrement veuillez contactez l'administrateur";
                  userTransaction.rollback();
                  redirectWithErrorMessage(response, uriRedirect, message);

               }
            } catch (Exception e) {
               message =
                     "Une erreur est survenue lors de l'enregistrement veuillez contactez l'administrateur";
               redirectWithErrorMessage(response, uriRedirect, message);
            }
         }
      }
   }

   private Discipline getDiscipline(HttpServletRequest request) {
      Discipline discipline = null;
      try {
         Long codeDiscipline = Long.valueOf(request.getParameter("codeDiscipline"));
         discipline = em.find(Discipline.class, codeDiscipline);
      } catch (EntityNotFoundException ex) {

      }
      return discipline;
   }

   private Sport getSport(HttpServletRequest request) {
      Sport sport = null;
      try {
         Long codeSport = Long.valueOf(request.getParameter("codeSport"));
         sport = em.find(Sport.class, codeSport);
      } catch (EntityNotFoundException ex) {

      }
      return sport;
   }

   private void redirectWithErrorMessage(HttpServletResponse response, String uriRedirect,
         String message) throws IOException {
      String urlRediret = String.format(uriRedirect, message);
      response.sendRedirect(urlRediret);
   }
}
