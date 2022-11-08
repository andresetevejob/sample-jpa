package com.nextu.controllers;

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
 * Servlet implementation class DeleteSport
 */
@WebServlet("/deleteSport")
public class DeleteSport extends HttpServlet {
   private static final long serialVersionUID = 1L;
   @PersistenceContext(unitName = "sample-jpa")
   private EntityManager em;
   @Resource
   private UserTransaction userTransaction;

   /**
    * Default constructor.
    */
   public DeleteSport() {
      // TODO Auto-generated constructor stub
   }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      // TODO Auto-generated method stub
      String message = "";
      String uriRedirect = """
            sports.jsp?errorMessage=%s
            """;
      try {
         Long codeSport = Long.parseLong(request.getParameter("codeSport"));
         Sport sport = em.find(Sport.class, codeSport);
         if (sport == null) {
            message = "Aucun sport correspondant à ce code";
            redirectWithErrorMessage(response, uriRedirect, message);
         } else {

            boolean transactionOk = false;
            try {
               userTransaction.begin();
               em.remove(em.merge(sport));
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
      } catch (Exception ex) {
         message =
               "Une erreur est survenue lors de la suppression veuillez contacter l'administrateur";
         redirectWithErrorMessage(response, uriRedirect, message);
      }


   }

   private void redirectWithErrorMessage(HttpServletResponse response, String uriRedirect,
         String message) throws IOException {

      String.format(uriRedirect, message);
      response.sendRedirect(uriRedirect);
   }
}
