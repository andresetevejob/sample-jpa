<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ page 
    
    import="java.util.ArrayList,java.util.List,com.nextu.entities.Sport"
 %>
 <%
  List<Sport> sports = new ArrayList<Sport>();
  if(request.getAttribute("sports")!=null){
	  sports = (ArrayList<Sport>)request.getAttribute("sports");
  }
%>
<div class="container-fluid">
    <div class="row">
     <%@ include file="/WEB-INF/template/menu.jsp" %>
      <main class="col-md-10 ml-sm-auto col-lg-10 pt-3 px-4">
        <%@ include file="/WEB-INF/error/error-message.jsp" %>
        <div class="row">
        <div class="col-lg-12 col-md-12 col-sm-12 pr-0 mb-3">
            <div class="card-collapsible card">
              <div class="card-header">
                Enregistrer une discipline <i class="fa fa-caret-down caret"></i>
              </div>
              <div class="card-body">
                <form method="post" action="creerDiscipline">
                  <div class="form-group">
                    <input type="text" class="form-control" placeholder="Intitul? du sport" name="libelle">
                  </div>
                  <div class="form-group">
                    <select name="sport" id="sport">
                       <option value="-1">Choix du sport</option>
                       	<%
                    		 for(Sport sp :sports){
                  	    %>
                       		<option value="<%=sp.getCode() %>"><%=sp.getLibelle() %></option>
                        <%} %>		
                    </select>
                  </div>
                  <div class="form-group row">
                    <div class="col-sm-10">
                      <button type="submit" class="btn btn-primary">
                        <i class="fa fa-send"></i>
                        Enregistrer
                      </button>
                      <button type="submit" class="btn btn-primary">
                        <i class="fa fa-send"></i>
                        Annuler
                      </button>
                    </div>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
  </div>
</body>
</html>