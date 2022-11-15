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
 * Servlet implementation class CreerDiscipline
 */
@WebServlet("/creerDiscipline")
public class CreerDiscipline extends HttpServlet {
   private static final long serialVersionUID = 1L;
   @PersistenceContext(unitName = "sample-jpa")
   private EntityManager em;
   @Resource
   private UserTransaction userTransaction;

   /**
    * Default constructor.
    */
   public CreerDiscipline() {
      // TODO Auto-generated constructor stub
   }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      TypedQuery<Sport> query = em.createQuery("SELECT s FROM Sport s", Sport.class);
      List<Sport> sports = query.getResultList();
      request.setAttribute("sports", sports);
      this.getServletContext().getRequestDispatcher("/form-discipline.jsp").forward(request,
            response);
   }

   /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      String message = "Veuillez selectionner un sport";
      String libelle = request.getParameter("libelle");
      if (libelle == null || libelle.isEmpty() || libelle.isBlank()) {
         message = "Veuillez renseignez le libellé de la discipline";
         request.setAttribute("errorMessage", message);
         redirectWithErrorMessage(response, request);
      } else {
         Sport sport = this.findSport(request.getParameter("sport"));
         if (sport == null) {
            message = "Aucun enregistrement de sport trouvé";
            request.setAttribute("errorMessage", message);
            redirectWithErrorMessage(response, request);
         } else {
            saveDiscipline(response, request, libelle, sport);
         }

      }


   }

   private void saveDiscipline(HttpServletResponse response, HttpServletRequest request,
         String libelle, Sport sport) throws IOException {
      String message;
      boolean transactionOk = false;
      try {
         userTransaction.begin();
         Discipline discipline = new Discipline();
         discipline.setLibelle(libelle);
         discipline.setSport(em.merge(sport));
         em.persist(discipline);
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
               request.setAttribute("errorMessage", message);
               redirectWithErrorMessage(response, request);
            }
         } catch (Exception e) {
            message =
                  "Une erreur est survenue lors de l'enregistrement veuillez contactez l'administrateur";
            request.setAttribute("errorMessage", message);
            redirectWithErrorMessage(response, request);
         }
      }
   }

   private Sport findSport(String codeSport) throws IOException {
      Sport sport = null;
      try {
         sport = em.find(Sport.class, Long.valueOf(codeSport));
      } catch (EntityNotFoundException e) {
         System.out.print("Aucune entité trouvé");
      } catch (Exception ex) {
         System.out.print(
               "Une erreur est survenue lors de l'enregistrement veuillez contactez l'administrateur ");
      }
      return sport;
   }

   private void redirectWithErrorMessage(HttpServletResponse response, HttpServletRequest request)
         throws IOException {
      try {
         this.getServletContext().getRequestDispatcher("/form-discipline.jsp").forward(request,
               response);
      } catch (ServletException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

}
