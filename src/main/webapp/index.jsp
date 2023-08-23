<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="gen.*"%>
<%@page import="java.sql.*"%>
<%@page import="org.json.JSONArray"%>
<%@page import="java.util.*"%>

<html lang="en">

<%
DataCollector coll = new DataCollector();

	
	String ani = (String) session.getAttribute("user");
	String age = (String) session.getAttribute("ageid");

	// For localhost comments this 
	  if(ani==null)
	 {
		 response.sendRedirect("landing.jsp");		 

		 return;
	 } 

	String cat = "20";

	String agent = request.getHeader("User-Agent");
	// System.out.println("$$$$$"+agent);

	DataCollector.insertUserDevice(agent, ani);


%>






</script>
<jsp:include page="header.jsp" />

<body class="d-flex min-vh-100 flex-column" >
	<div class="se-pre-con" id="se-pre-con" >
	<img src="images/logo.png" class="img-fluid"></div>
		<jsp:include page="nav.jsp" />
		<div class="body-wrapper flex-grow-1">

			<section class="slider-bignew mb-4">
			<div class="owl-carousel owl-theme">
				<%
					ResultSet res4 = coll.getTeaser();
				while (res4.next()) {
					
					String imgurl = res4.getString("imgurl");
					String videoid = res4.getString("teaser_id");
					String video_sub_cat = res4.getString("video_sub_cat");
				%>

				<div class="item">
					<a
						onclick="addlogging('<%=ani%>','<%=videoid%>','teaser','<%=video_sub_cat %>')"
						href="#" data-toggle="modal"> <img src="images/load_thumb.gif"  data-src="<%=imgurl%>"
						alt="Slider" class="img-fluid w-100" />
						<div class="hover-posts d-flex justify-content-center">
							<button class="btn btn-light px-4 mr-2">
								<i class="fa fa-play small"></i> Play
							</button>
							<button class="btn btn-light px-4">
								<i class="fa fa-info small"></i>info
							</button>
						</div>
					</a>
				</div>
				<%
					}
				res4.close();
				%>
			</div>
		</section>



		<%
			ResultSet resW = coll.getWatching("DreamStream");
		if (resW.next()) {
		%>
		<section class="movie-content dollystyle py-3">
			<div class="container">
				<div class="row">
					<div class="col-12">
						<div class="cat-heading">
							<h2>Continue Watching</h2>

						</div>
					</div>
				</div>

				<div class="owl-carousel owl-theme">
					<%
						resW.beforeFirst();
					while (resW.next()) {
						String imgurl = resW.getString("imgurl");
						String videoid = resW.getString("videoid");

						String percentage = resW.getString("percentage");
						String sub_cat = resW.getString("sub_cat_id");
						String sub_cat_name = resW.getString("sub_cat_name");
					%>
					<div class="item">
						<div class="movie-thumbnail">
							<a
								onclick="addlogging('<%=ani%>','<%=videoid%>','DreamStream')"
								href="Play?id=<%=videoid%>&cat=<%=sub_cat%>"> <img
								src="images/load_thumb.gif"  data-src="<%=imgurl%>" alt="Slider" class="img-fluid w-100" />
								<div class="bg-black">
									<div class="percentage">
										<div class="value" style="width: <%=percentage%>%;"></div>
									</div>
									<div class="content bg-black">
										<h4 class="text-white"><%=sub_cat_name%></h4>
									</div>
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


		<!-- video -->
		<%
			ResultSet res3 = coll.getLatestVideos();
		if (res3.next()) {
		%>
		<section class="movie-content dollystyle py-3">
			<div class="container">
				<div class="cat-heading">
					<h2>New Releases</h2>

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
							<a
								onclick="addlogging('<%=ani%>','<%=videoid%>','DreamStream')"
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

		<section class="movie-content dollystyle py-3">
			<div class="container">
				<div class="cat-heading">
					<h2>Recommended videos</h2>

				</div>
				<div class="owl-carousel owl-theme">
					<%
						ResultSet res5 = coll.getRCat();
					List<String> gamesArray = new ArrayList<String>();
					while (res5.next()) {

						if (gamesArray.contains(res5.getString("category"))) {
							continue;
						}

						ResultSet res6 = coll.getRVideo(res5.getString("category"));
						while (res6.next()) {
							String imgurl = res6.getString("imgurl");
							String videoid = res6.getString("videoid");
							String name = res6.getString("name");
							String sub_cat = res6.getString("sub_cat_id");
					%>
					<div class="item">
						<div class="movie-thumbnail">
							<a
								onclick="addlogging('<%=ani%>','<%=videoid%>','DreamStream')"
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
					gamesArray.add(res5.getString("category"));
					}
					%>
				</div>
			</div>
		</section>


		<%
			ResultSet restop = coll.getTop("DreamStream");
		int topcount = 1;
		if (restop.next()) {
		%>
		<section class="movie-content dollystyle py-3">
			<div class="container">
				<div class="cat-heading">
					<h2>Top 10 shows</h2>

				</div>
				<div class="owl-carousel top-list owl-theme">
					<%
						restop.beforeFirst();
					while (restop.next()) {
						String imgurl = restop.getString("imgurl");
						String videoid = restop.getString("videoid");
						String name = restop.getString("name");
						String sub_cat = restop.getString("sub_cat_id");
					%>
					<div class="item">
						<div class="movie-thumbnail">
							<a
								onclick="addlogging('<%=ani%>','<%=videoid%>','DreamStream')"
								href="Play?id=<%=videoid%>&cat=<%=sub_cat%>"> <img
								src="images/load_thumb.gif"  data-src="<%=imgurl%>" alt="Slider" class="img-fluid w-100" />
								<div class="content bg-black">
									<h4 class="text-white"><%=topcount%></h4>
								</div>

							</a>
						</div>
					</div>
					<%
						topcount++;
					}
					%>
				</div>
			</div>
		</section>
		<%
			}
		%>

		<%
			ResultSet resWl = coll.getwhishlist("DreamStream");
		if (resWl.next()) {
		%>
		<section class="movie-content dollystyle py-3">
			<div class="container">
				<div class="row">
					<div class="col-12">
						<div class="cat-heading">
							<h2>Wishlist</h2>

						</div>
					</div>
				</div>

				<div class="owl-carousel owl-theme">
					<%
						resWl.beforeFirst();
					while (resWl.next()) {
						String imgurl = resWl.getString("imgurl");
						String videoid = resWl.getString("videoid");
						String sub_cat = resWl.getString("sub_cat_id");
						String sub_cat_name = resWl.getString("sub_cat_name");
					%>
					<div class="item">
						<div class="movie-thumbnail">
							<a
								onclick="addlogging('<%=ani%>','<%=videoid%>','DreamStream')"
								href="Play?id=<%=videoid%>&cat=<%=sub_cat%>"> <img
								src="images/load_thumb.gif"  data-src="<%=imgurl%>" alt="Slider" class="img-fluid w-100" />
								<div class="content bg-black">
									<h4 class="text-white"><%=sub_cat_name%></h4>
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
		<!-- video -->
		<%
			}
		%>
		
	 	<%
	 	try{
	 		
	 		
			ResultSet res = coll.getMainCat(age);
			
		while (res.next()) {
			String sub_cat_id = res.getString("sub_cat_id");
			String sub_cat_name = res.getString("sub_cat_name");
			ResultSet genreRes = coll.getGenre();
		%>

		<section class="movie-content dollystyle py-3">
			<div class="container">
				<div class="row">
					<div class="col-12">
						<div class="cat-heading">
							<h2>

								<%=sub_cat_name%>
							</h2>
							<a href="showAll.jsp?c=<%=cat%>&s=<%=sub_cat_id%>">More</a>
						</div>
					</div>
				</div>

				<div class="owl-carousel owl-theme">
					<%
						ResultSet res1 = coll.getVideos(sub_cat_id);
					while (res1.next()) {
						String imgurl = res1.getString("imgurl");
						String videoid = res1.getString("videoid");
						String name = res1.getString("name");
						String description = res1.getString("description");
						String datetime = res1.getString("datetime");
						datetime = datetime.substring(0, 11);
					%>
					<div class="item">
						<div class="movie-thumbnail">
							<a onclick="addlogging('<%=ani%>','<%=videoid%>','video')"
								href="Play?id=<%=videoid%>&cat=<%=sub_cat_id%>"> <img
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
		res.close();
	 	}catch(Exception e)
	 	{
	 		e.printStackTrace();
	 	}
		
		
		%> 
		
	</div>
	
		
	<jsp:include page="footer.jsp" />


  <script type="text/javascript">
  
  let ani=<%=ani%>
  addTracklogging("<%=ani%>")
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
	           
			   "action":"1",
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

       
     function addTracklogging(ani) {

    	   let date=new Date();
    	   let hour=date.getHours();
    	   let min=date.getMinutes();
    	   sessionStorage.setItem("minhour",hour+':'+min);
    	   
			var jsonR = {
				action :'5',
				ani : ani,
				portal:'mtn'
			};
      console.log(jsonR);
	  let query=new URLSearchParams(jsonR);
	       fetch('ApiServlet',{method:'POST',body:query}).then(resp=>{return resp.json()}).
	       then(data=>console.log(data)).catch(error=>console.log("error"))
       }
          </script>
</body>
</html>