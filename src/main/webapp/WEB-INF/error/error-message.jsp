<div class="row">
		 <% 
          String message = request.getParameter("errorMessage")!=null?request.getParameter("errorMessage"):(String)request.getAttribute("errorMessage");
		  
          if(message!=null && message!="") {
        %>
        <div class="col-lg-12 col-md-12 col-sm-12 pr-0 alert alert-danger">
            <%=message %>
        </div>
        <% } %>
</div>