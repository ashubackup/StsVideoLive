<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="gen.*"%>
<%@page import="java.sql.*"%>
<!DOCTYPE html>
<html lang="en">
<%
DataCollector coll = new DataCollector();
	String cat = request.getParameter("c");
	String sub_id = request.getParameter("s");
	String ani =  (String) session.getAttribute("user");
	
	
	if(ani==null)
	 {
		 response.sendRedirect("landing.jsp");	
		 return;
	 }
	
%>
<jsp:include page="header.jsp" />


<body class="d-flex min-vh-100 flex-column">
	<jsp:include page="nav.jsp" />
	<div class="body-wrapper flex-grow-1">
		<section class="slider-big mb-4">
			<div class="owl-carousel owl-theme">
				<%
					ResultSet res4 = coll.getTeaserByID(cat);
					while (res4.next()) {
						String imgurl = res4.getString("imgurl");
						String videoid = res4.getString("teaser_id");
						String video_cat = res4.getString("video_cat");
						String vurl = res4.getString("vurl");
						
				%>

				
				
				<div class="item">
					<a  href="#" data-toggle="modal" > <img
						src="images/load_thumb.gif"  data-src="<%=imgurl%>" alt="Slider" class="img-fluid w-100"  />
						<div class="hover-posts" >
							<span><i class="fa fa-play"></i></span>
						</div>
					</a> 
				</div>
				<%
					}
					res4.close();
				%>
			</div>
		</section>






		<!-- video -->
		<%
			ResultSet res3 = coll.getDataBySubID(sub_id);
			if (res3.next()) {
		%>
		<section class="movie-content dollystyle py-3">
			<div class="container">
				<div class="cat-heading">
					<h2>Videos</h2>
					
				</div>
				<div class="owl-carousel owl-theme">
					<%
						res3.beforeFirst();
							while (res3.next()) {
								String imgurl = res3.getString("imgurl");
								String videoid = res3.getString("videoid");
								String name = res3.getString("name");
								String sub_cat = res3.getString("sub_cat_id");
								String description = res3.getString("description");
								String datetime = res3.getString("datetime");
								datetime = datetime.substring(0, 11);
					%>
					<div class="item">
						<div class="movie-thumbnail">
							<a onclick="addlogging('<%=ani %>','<%=videoid%>','video')"
								href="Play?id=<%=videoid%>&cat=<%=sub_cat%>"> <img
								src="images/load_thumb.gif"  data-src="<%=imgurl%>" alt="Slider" class="img-fluid w-100" />
								<div class="content bg-black">
									<h4 class="text-white"><%=name%></h4>
								</div>
							</a>
						</div>
					</div>
					<%
						}
					%>
				</div>
			</div>
		</section>
		<%
			}
		%>
		

	</div>
	<jsp:include page="footer.jsp" />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<script src="js/owl.carousel.min.js"></script>
<script src="js/custom.js"></script>
<script src="js/index.js"></script>

<script type="text/javascript">
let ani=<%=ani%>


  activityWatcher(ani);
 document.addEventListener('readystatechange', event=>{
	  
	  if (event.target.readyState === "complete") {
// 		  alert("hi 1");
	        let urls=[];
	        setTimeout(()=>{scrapingpage(ani);},2000)
	            
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
//	  console.log(urls)
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
	  then(datar=>console.log(datar)).
	  catch(erorr=>console.log(erorr));
	   
}

</script>

</body>
</html>