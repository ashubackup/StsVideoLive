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
	String vurl = "", catid = "";
String id = request.getParameter("id");
DataCollector coll = new DataCollector();
ResultSet res = coll.getTeaserDetails(id);
if (res.next()) {
	vurl = res.getString("vurl");
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
	<video id="my-video" class="video-js" controls preload="auto"
		style="width: 100%;" onclick="update()" poster="MY_VIDEO_POSTER.jpg"
		data-setup="{}">
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



</body>
</html>
<%
	}
res.close();
%>