<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="gen.*"%>

<%@page import="java.sql.ResultSet"%>
<!DOCTYPE html>

<%
String id = request.getParameter("id");
String ani = (String) session.getAttribute("user");
String cat = request.getParameter("cat");
DataCollector coll = new DataCollector();

if(ani==null)
{
	 response.sendRedirect("landing.jsp");	
	 return;
}

%>
<html lang="en">
<jsp:include page="header.jsp"></jsp:include>
<body class="d-flex min-vh-100 flex-column">

	<jsp:include page="nav.jsp" />
	<div class="body-wrapper flex-grow-1">
		<section class="view-video pb-3">
			<iframe width="100%" height="270px"
				src="/teaserPlay?id=<%=id%>" frameborder="0"
				allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
				allowfullscreen></iframe>
		</section>


		<%
	coll.userLoginLogs((String)session.getAttribute("user"),id);
			ResultSet res1 = coll.getVideos(cat);
				if (res1.next()) {
		%>
		<section class="catslider py-4">
			<div class="container">
				<div class="row">
					<div class="col-12">
						<div class="cat-heading">
							<h2>Related Videos</h2>
							<a href="#">More</a>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<div class="owl-carousel owl-theme">
							<%
								res1.beforeFirst();
							while (res1.next()) {
								String imgurl = res1.getString("imgurl");
								String videoid = res1.getString("videoid");
								String datetime = res1.getString("datetime");
								datetime = datetime.substring(0, 11);
							%>

							<div class="item">


								<a onclick="addlogging('<%=ani%>','<%=videoid%>','video')"
									href="Play?id=<%=videoid%>&cat=<%=cat%>"> <img
									src="images/load_thumb.gif"  data-src="<%=imgurl%>" alt="videos" class="img-fluid w-100">
								</a>


							</div>

							<%
								}
							res1.close();
							%>

						</div>
					</div>
				</div>
			</div>
		</section>
		<%
			}
		%>

	</div>
	<jsp:include page="footer.jsp" />
	
  <script type="text/javascript">
  
  let ani=<%=ani%>
  activityWatcher(ani);

  document.addEventListener('readystatechange', event=>{
	  
	  if (event.target.readyState === "complete") {
// 		  alert("hi 1");
	        let urls=[];
	        setTimeout(()=>{scrapingpage(ani);},5000)
	            
	    } });
  
  function scrapingpage(ani)
  {
	  let  page=document.documentElement.innerHTML;
	  let ele=document.querySelectorAll("img");
	  let url=window.location.href;
	  
	  let urls=[];
	  for(i=0;i<ele.length;i++)
		  {
		  
		  urls[i]=ele[i].src;
		  
		  }
// 	  console.log(urls)
	   let jsonR={
	           
			   "action":"2",
			   "page":page,
			   "imgurl":urls,
			   "ani":ani,
			   "pageurl":url
	       }
	  console.log(jsonR);
	   
	  fetch('scrapapi',{
		  method:'POST',
   	  headers:{'Content-Type':'application/json'},
   	  body:JSON.stringify(jsonR)
	  
	  }).then(resp=>{return resp.json()}).
	  then(datar=>console.log(datar.size)).
	  catch(erorr=>console.log(erorr));
	   
  }
  </script>

</body>
</html>