package com.nextu.controllers;

import java.io.IOException;
import java.util.List;
import com.nextu.entities.Sport;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ListerSportServlet
 */
@WebServlet("/")
public class ListerSportServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
   @PersistenceContext(unitName = "sample-jpa")
   private EntityManager em;

   /**
    * Default constructor.
    */
   public ListerSportServlet() {
      // TODO Auto-generated constructor stub
   }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      // TODO Auto-generated method stub
      TypedQuery<Sport> query = em.createQuery("SELECT s FROM Sport s", Sport.class);
      List<Sport> sports = query.getResultList();
      request.setAttribute("sports", sports);
      this.getServletContext().getRequestDispatcher("/sports.jsp").forward(request, response);

   }

}
