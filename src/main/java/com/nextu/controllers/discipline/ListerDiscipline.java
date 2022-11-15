package com.nextu.controllers.discipline;

import java.io.IOException;
import java.util.List;
import com.nextu.entities.Discipline;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ListerDiscipline
 */
@WebServlet("/disciplines")
public class ListerDiscipline extends HttpServlet {
   private static final long serialVersionUID = 1L;
   @PersistenceContext(unitName = "sample-jpa")
   private EntityManager em;

   /**
    * Default constructor.
    */
   public ListerDiscipline() {
      // TODO Auto-generated constructor stub
   }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      TypedQuery<Discipline> query = em.createQuery("SELECT d FROM Discipline d", Discipline.class);
      List<Discipline> disciplines = query.getResultList();
      request.setAttribute("disciplines", disciplines);
      this.getServletContext().getRequestDispatcher("/disciplines.jsp").forward(request, response);
   }

}
