package com.nextu.controllers.sport;

import java.io.IOException;
import com.nextu.entities.Sport;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.UserTransaction;

/**
 * Servlet implementation class ModifierSport
 */
@WebServlet("/modifierSport")
public class ModifierSport extends HttpServlet {
   private static final long serialVersionUID = 1L;
   @PersistenceContext(unitName = "sample-jpa")
   private EntityManager em;
   @Resource
   private UserTransaction userTransaction;

   /**
    * Default constructor.
    */
   public ModifierSport() {
      // TODO Auto-generated constructor stub
   }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      Long codeSport = Long.valueOf(request.getParameter("codeSport"));
      Sport sport = em.find(Sport.class, codeSport);
      request.setAttribute("sport", sport);
      String errorMessage = request.getParameter("errorMessage");
      request.setAttribute("errorMessage", errorMessage);
      this.getServletContext().getRequestDispatcher("/modifier-sport.jsp").forward(request,
            response);
   }

   /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      // TODO Auto-generated method stub
      String libelle = request.getParameter("libelle");
      String message = "";
      Long codeSport = Long.valueOf(request.getParameter("codeSport"));
      String uriRedirect = "modifierSport?codeSport=" + codeSport + "&errorMessage=%s";
      if (libelle == null || libelle.isEmpty() || libelle.isBlank()) {
         message = "Veuillez renseignez le libellé du sport";
         redirectWithErrorMessage(response, uriRedirect, message);

      } else {
         Sport sport = em.find(Sport.class, codeSport);
         if (sport == null) {
            message = "Aucun sport correspondant à ce code";
            redirectWithErrorMessage(response, uriRedirect, message);
         } else {
            boolean transactionOk = false;
            try {
               userTransaction.begin();
               sport.setLibelle(libelle);
               em.merge(sport);
               transactionOk = true;
            } catch (Exception e) {
               System.out.print("Une erreur est survennue lors de l'enregistrement");
            } finally {
               try {
                  if (transactionOk) {
                     userTransaction.commit();
                     response.sendRedirect("");
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
   }

   private void redirectWithErrorMessage(HttpServletResponse response, String uriRedirect,
         String message) throws IOException {
      String urlRediret = String.format(uriRedirect, message);
      response.sendRedirect(urlRediret);
   }

}
