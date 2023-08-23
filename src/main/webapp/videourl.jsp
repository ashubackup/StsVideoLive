<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page language="java" import="java.net.URLEncoder"%>
<%@page import="gen.*"%>

<%@page import="java.sql.ResultSet"%>
<%
String user = (String) session.getAttribute("user");
if(user==null)
{
request.setAttribute("message", "Login First");
request.getRequestDispatcher("/login.jsp").forward(request, response);
}
	String vurl = "", catid = "",imgurl="";
String id = request.getParameter("id");
DataCollector coll = new DataCollector();
ResultSet res = coll.getvideoDetailsbyId(id);
if (res.next()) {
	vurl = res.getString("vurl");
	catid = res.getString("category");
	imgurl=res.getString("imgurl");
%>
<!DOCTYPE html>
<html class="h-100">
<head>
<link href="https://vjs.zencdn.net/7.8.4/video-js.css" rel="stylesheet" />

<!-- If you'd like to support IE8 (for Video.js versions prior to v7) -->
<script src="https://vjs.zencdn.net/ie8/1.1.2/videojs-ie8.min.js"></script>
<style>
body {
	padding: 0;
	margin: 0;
}

video {
	border: none !important;
	outline: none !important;
}

.h-100 {
	height: 100%;
}

.video-js .vjs-big-play-button {
	font-size: 24px;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	margin: 0 !important;
	padding: 0 !important;
	background-color: #ff1b1b;
	border-color: #ff1b1b;
}

.my-video-dimensions {
	height: 100%;
}
</style>
</head>

<body class="h-100">
	<video id="my-video" class="video-js" controls preload="auto"  muted=true loop
		style="width: 100%;" onclick="update()" poster=<%=imgurl %>
		data-setup='{"controls": true ,"autoplay":true,"preload": "auto" }' >
		<source src="<%=vurl%>" type="video/mp4"/>
		<p class="vjs-no-js">
			To view this video please enable JavaScript, and consider upgrading
			to a web browser that <a href="" target="_blank">supports HTML5
				video</a>
		</p>
	</video>

	<script src="https://vjs.zencdn.net/7.8.4/video.js"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

	<script src="js/index.js"></script>

	<script>
	var id = "<%=id%>";
	var ani = "<%=user%>";
	var myPlayer = videojs('my-video');

	 async function  getDuration(ani,id,duration){

			var jsonR ={action:'13',videoid:id,duration:duration,channel:"DreamStream",ani:ani};
			$.ajax({
					type: "POST",
			        url: "/CommonApi",
			        data: jsonR,
			       success:function(result){
			    	   console.log(result);
			    	   	var JsonR = JSON.parse(result);
			    	   	var status = JsonR.status;
			    	   	if(status == '1'){
			    	   		var data = JsonR.data
			    	   		console.log(data);
			    	   		myPlayer.currentTime(data.duration);
			    	   		return data.duration;
			    	   	}else{
			    	   		return 0;
			    	   	}
			    }
			});
		}
	
	var value = localStorage.getItem(""+id+"");
	async function test(value){
		if(value == "" || value == undefined)
		{
			value=await getDuration(ani,id);
		}

	
		
	    myPlayer.currentTime(value);
	}
	test(value);

	 
	
	function myFunction() {
	  setInterval(function(){ 
			update();
	  }, 10000);
	}
	
	
	function update(){
		  localStorage.setItem(""+id+"", myPlayer.currentTime());
		  var duration = myPlayer.currentTime();
		  var percentage = myPlayer.currentTime()/myPlayer.duration()*100;
		  addtimelogging(ani,id,duration,percentage,'<%=catid%>');
		  addPlaylogging(ani, id, duration, percentage,'<%=catid%>');
	}

		myFunction();

		myPlayer.on('progress', checkBuffered);
		count = 0;
		function checkBuffered() {
			var diff = parseInt(myPlayer.currentTime() / myPlayer.duration()
					* 100);
			if (diff >= 25 && count == 0) {
				count++;
				console.log(diff);
				addViews(id, ani);
			}
		}
		
		
	</script>

</body>
</html>
<%
	}
res.close();
%>