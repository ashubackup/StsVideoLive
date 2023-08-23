  
  
<%@page import="java.sql.*"%>
<%@page import="gen.*"%>

<%
String user = (String) session.getAttribute("user");
// For localhost 
   /*  String age = (String) session.getAttribute("ageid"); */
    String age = "0";
     
if(user==null)
{
// 	request.setAttribute("message", "Login First");
// 	request.getRequestDispatcher("/login.jsp").forward(request, response);
	
response.sendRedirect("");
	
}

// if(age==null)
// {
// /* 	request.setAttribute("msisdn", user);
// 	request.setAttribute("result", "Active"); */
// 	String redirect="redirect?msisdn="+user+"&result=Active";
// 	response.sendRedirect(redirect);
// /* 	request.getRequestDispatcher(redirect).forward(request, response);
//  */}


	DataCollector l1 = new DataCollector();
%>
    <header>
        <nav class="navbar px-md-0">
            <div class="container">
                <a class="navbar-brand header-logo" href="/"><img src="images/logo.png" alt="Logo" class="img-fluid" /></a>
                <button class="navbar-toggler border rounded-0 text-white" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <i class="fa fa-bars"></i>
                </button>
            
              <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav ml-auto">
                    
                    <%
						String cat = "";
						ResultSet rsCat = l1.getAllCat(age);
						while (rsCat.next()) {
							String catName = rsCat.getString("category_name");
							String catMain = rsCat.getString("name");
							ResultSet subCat = l1.getSubCat(catName);
					%>
													<li class="dropdown"><a
														class="dropdown-item dropdown-toggle" href="#"
														id="subnavbarDropdown" role="button"
														data-toggle="dropdown" aria-haspopup="true"
														aria-expanded="false"><%=catMain%></a>
														<div class="dropdown-menu"
															aria-labelledby="subnavbarDropdown">
															<%
																while (subCat.next()) {
																		String sub_cat_id = subCat.getString("sub_cat_id");
																		String sub_cat_name = subCat.getString("sub_cat_name");
															%>
															<a class="dropdown-item"
																href="showAll.jsp?c=<%=catName%>&s=<%=sub_cat_id%>"><%=sub_cat_name%></a>
															<%
																}
															%>
														</div></li>
													<%
														}
													%>
                     
                        
                    </ul>
                </div>
            </div>
        </nav>
    </header>
    <!-- <div id="loader">
		<img src="images/loader.gif" alt="loader" class="img-fluid">
	</div> -->