<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ page 
    
    import="java.util.ArrayList,java.util.List,com.nextu.entities.Discipline"
 %>
 <%
  List<Discipline> disciplines = new ArrayList<Discipline>();
  if(request.getAttribute("disciplines")!=null){
     disciplines = (ArrayList<Discipline>)request.getAttribute("disciplines");
  }
%>
<div class="container-fluid">
    <div class="row">
      <%@ include file="/WEB-INF/template/menu.jsp" %>
      <main class="col-md-10 ml-sm-auto col-lg-10 pt-3 px-4">
         <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
          <div class="btn-toolbar mb-2 mb-md-0">
            <div class="btn-group mr-2">
		   		<a href="creerDiscipline" class="btn btn-sm btn-primary">Enregistrer</a>        
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-lg-12 col-md-12 col-sm-12 pr-0 mb-3">
            <div class="card-collapsible card">
              <div class="card-header">
                Table <i class="fa fa-caret-down caret"></i>
              </div>
              <div class="card-body">
                <table class="table">
                  <thead class="thead bg-primary text-white">
                    <tr>
                      <th scope="col">#</th>
                      <th scope="col">Code</th>
                      <th scope="col">Libelle</th>
                      <th scope="col">Sport</th>
                      <th scope="col">Actions</th>
                    </tr>
                  </thead>
                  <tbody>
                 	<%
                     for(Discipline dsp :disciplines){
                  %>
                    <tr>
                      <td><%= dsp.getLibelle()%></td>
                      <td><%= dsp.getSport().getLibelle()%></td>
                      <td>
                          <a class="btn btn-sm btn-success" href="modifierDiscipline?codeDiscipline=<%= dsp.getCode()%>">Modifier</a>
                            <!-- Button trigger modal -->
                          <a href="deleteSport?codeSport=<%= dsp.getCode()%>" class="btn btn-sm btn-danger">
                            supprimer
                          </a>
                      </td>
                    </tr>
                  <%
                    }
                  %>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
  </div>
</body>
</html>