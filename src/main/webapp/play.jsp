<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.stream.Stream"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="gen.*"%>

<%@page import="java.sql.ResultSet"%>
<!DOCTYPE html>

<%

    String id = request.getParameter("id");
    String cat = request.getParameter("cat");
	String ani = (String) session.getAttribute("user");
	String ageid=(String)session.getAttribute("ageid");
	
	if(ani==null || ageid==null)
	 {
		 response.sendRedirect("landing.jsp");	
		 return;
	 }
	
     DataCollector coll = new DataCollector();
	ResultSet res = coll.getvideoDetailsbyId(id, ani,"DreamStream");
	String name = "", description = "", genres = "";
	
	
	if (res.next()) {
		name = res.getString("name");
		description = res.getString("description");
		genres = res.getString("genres");

	}
	
%>
<%


Map<String,String> m=new HashMap();

m.put("id", id);
m.put("cat", cat);
session.setAttribute("url", m);
String age=DataCollector.getAgeStatusfromSubcat(cat);
String url="VideoPlay?id="+id;
// System.out.println("Duplicate Age*******/"+age+"Agid#########"+ageid);
try{
		if(ageid.equalsIgnoreCase("2")){
			
			
			String dummy="Test?userid="+ani;	
			response.sendRedirect(dummy);
			
		}
		 else if(ageid.equalsIgnoreCase("1") && age.equalsIgnoreCase("1"))
			{
			     		out.print("<script>alert('You are Under 18 !')</script>");
						RequestDispatcher rd=request.getRequestDispatcher("/index.jsp");
						rd.include(request , response);
			            return;
			}
}catch(Exception e)
{
	e.printStackTrace();
}
		
			
%>
<html lang="en">
<jsp:include page="header.jsp" />
<script async defer crossorigin="anonymous" src="https://connect.facebook.net/pa_IN/sdk.js#xfbml=1&version=v10.0" nonce="umNnUVqB"></script>

<style>

.modal-body i {
        	 font-size: 25px;
        	 padding: 0px 25px;
        }
	.modal-body a {
      	  	 color: #000;
	}
	.modal-body i.fab.fa-facebook{
  		  color: #4267B2;
	}
	.modal-body i.fab.fa-whatsapp{
  		  color: green;
	}
	
	.modal-body i.fab.fa-twitter-square{
  		  color: #1DA1F2;
	}
	.modal-body i.fab.fa-telegram{
  		  color: #4267B2;
	}
	
	.modal-content {
		width:auto;
	}

	@media (min-width: 576px){
	.modal-dialog {
  		 margin: 0 auto;
   		 display: flex;
   		 justify-content: center;
	}
	}
	
	.modal-dialog.modal-dialog-centered {
    display: flex;
    justify-content: center;
}


/* Comment Section Css*/

.comment-section input {
		height: 28px;
		resize: none;
		width: 100%;
		max-width: 64%;
		padding: 5px 11px;
		font-size: 13px;
	}
	.comment-section label {
		color: #fff;
	}
	.comment-section button {
		padding: 0px 10px;
		font-size: 14px;
		margin-left: 10px;
	}
	.comments p {
		width: 100%;
		max-width: 64%;
	}
	p.cus-color {
		color: #a29090 !important;
	}
	.scroll-comment {
		max-height: 125px;
		overflow: auto;
		box-shadow: 0px 0px 4px -1px #fff;
		border-radius: 4px;
		padding: 7px 7px;
	}

</style>

<body class="d-flex min-vh-100 flex-column" >




	<jsp:include page="nav.jsp" />
	<div class="body-wrapper flex-grow-1">
		<section class="view-video pb-3">
			<iframe width="100%" height="270px"
<%-- 				src="/VideoPlay?id=<%=id%>" frameborder="0" --%>
                src="<%=url%>" frameborder="0" 
				allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
				allowfullscreen  onload="getComment('<%=id%>')"></iframe>

			<div class="container py-3">
				<div class="row">
					<div class="col-12">
						<div class="video-details d-md-flex mb-2">
							<h4 class="flex-grow-1 text-white text-uppercase"><%=name%></h4>
							<div class="meta">
								<span class="views text-white"><small><i
										class="fa fa-eye"></i> <%=res.getString("views")%></small></span>
							</div>
							<div class="likebtn">
								<%
								if (res.getString("userlike").equalsIgnoreCase("0")) {
									%>
									<button id="like" onclick="UpdateStatus('<%=id %>','<%=ani %>','<%=cat %>')" class="btn btn-dark text-uppercase py-1 px-3">
									<small><i class="fas fa-thumbs-up"></i> &nbsp; <span>Like</span>
										<span id ="likecnt" class="likes"> (<%=res.getString("likes")%>)</span></small>
								</button>
									<%
								}else{
									%>
									<button id="like" onclick="UpdateStatus('<%=id %>','<%=ani %>','<%=cat %>')" class="btn btn-primary text-uppercase py-1 px-3">
									<small><i class="fas fa-thumbs-up"></i> &nbsp; <span>Like</span>
										<span id ="likecnt" class="likes"> (<%=res.getString("likes")%>)</span></small>
									</button>
									<%
								}
								%>
								
								<%
									if (res.getString("whishlist").equalsIgnoreCase("0")) {
								%>
								<button id ="plus" class="btn btn-dark text-uppercase py-1 px-3"
									onclick="addWhishlist('<%=id%>','<%=ani%>')">
									<small><i class="fas fa-plus"></i> &nbsp; <span>Watchlist</span></small>
								</button>
									<button id ="check" style="display:none;" class="btn btn-dark text-uppercase py-1 px-3">
									<small><i class="fas fa-check"></i> &nbsp; <span>Watchlist</span></small>
								</button>
								<%
									} else {
								%>
								<button id ="check" class="btn btn-dark text-uppercase py-1 px-3">
									<small><i class="fas fa-check"></i> &nbsp; <span>Watchlist</span></small>
								</button>

								<%
									}
								%>
								<!-- Button trigger modal -->
                                <button type="button" class="btn btn-dark py-1 px-3" data-toggle="modal" data-target="#exampleModal">
                                    <i class="fas fa-share"></i> Share
                                </button>
                                <!-- Model -->
                                <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered" role="document">
                                    <div class="modal-content">
                                        <div class="modal-body">
                                       	 <a  onclick="addshare('<%=ani %>','facebook')" href="https://www.facebook.com/sharer/sharer.php?u=<%=url%>&amp;src=sdkpreparse"><i class="fab fa-facebook"></i></a>
                                         <a  onclick="addshare('<%=ani %>','twitter')" href="https://twitter.com/intent/tweet?text=&url=<%=url %>"><i class="fab fa-twitter-square"></i></a>
                                         <a  onclick="addshare('<%=ani %>','whatsapp')" href="https://api.whatsapp.com/send?text=<%=url %>" data-action="share/whatsapp/share" ><i class="fab fa-whatsapp"></i></a>
<%--                                     <a  onclick="addshare('<%=ani %>','telegram')"   href=" https://t.me/share/url?url=<%=url %>" ><i class="fab fa-telegram"></i></a> --%>
<!--                    						 <a  onclick="addshare('','')"  ><i class="fab fa-telegram"></i></a> -->
                                         
                                       </div>
                                     </div>
                                    </div>
                                </div>

							</div>

						</div>
						<div class="video-details d-md-flex mb-2">
							<ul class="nav">
								<li class="text-white"><%=genres%></li>
							</ul>
						</div>
						<div class="row pt-3">
							<div class="col-md-10">
								<div class="video-details text-white">
									<p>
										<span class="text-white"><i class="fa fa-info-circle"></i>
											<%=description%>
									</p>

								</div>
							</div>
						</div>
<!-- 						Comment Section Start  -->		
	
		             	<div class="comment-section">
<!-- 							<form> -->
								<div class="form-group">
									<label for="exampleFormControlTextarea1">Comment</label>
									<div class="d-flex">
										<input class="form-control" id="comment" rows="2" placeholder="Add Comment...."/>
										<button  class="btn btn-primary" onclick="addcomment('<%= ani%>','<%=id%>')">Add</button>
										
									</div>
								</div>
<!-- 							</form> -->
							<div class="scroll-comment" id="comments">
								
<!-- 								<div class="comments"> -->
<!-- 									<p class="text-white mb-0">Alexzendar</p> -->
<!-- 									<p class="text-white cus-color">Hello, excellent videos.</p> -->
<!-- 								</div> -->
								
							</div>
						</div>
						
						
						
						
					</div>
				</div>
			</div>
		</section>



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
								ResultSet ress = coll.getVideos(cat);
								while (ress.next()) {
									String imgurl = ress.getString("imgurl");
									String videoid = ress.getString("videoid");
									String datetime = ress.getString("datetime");
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
								ress.close();
							%>

						</div>
					</div>
				</div>
			</div>
		</section>
	</div>
	<div class="container">
<!--   <h2>Modal Example</h2> -->
  <!-- Trigger the modal with a button -->
<!--   <button type="button" class="btn btn-info btn-lg" id="alertbox">Click here</button> -->

  <!-- Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Modal Header</h4>
        </div>
        <div class="modal-body">
          <p id="error"></p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>
  
</div>
<jsp:include page="footer.jsp" />
<script type="text/javascript">

function addshare(ani,mod)
{
alert("inside share log");
var jsonR = {
		action: '23',
		mod:mod,
		ani: ani,
		portal:'mtn',
	};
	
	$.ajax({
		type: "POST",
		url: "/CommonApi",
		data: jsonR,
		success: function(result) {
			console.log(result);

		}
	});
	
}



  
  let ani=<%=ani%>
  activityWatcher('<%=ani%>');
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
	  then(datar=>console.log(datar)).
	  catch(erorr=>console.log(erorr));
	   
  }
  </script>

	
	

</body>
</html>