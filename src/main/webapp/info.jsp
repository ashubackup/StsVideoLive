<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>GameHub</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
</head>
<style>
#loader {
	position: fixed;
	top: 0;
	left: 0;
	height: 100%;
	width: 100%;
	background-color: rgba(0, 0, 0, .5);
	backdrop-filter: blur(4px);
	display: flex;
	justify-content: center;
	align-items: center;
	text-align: center;
}

.loader p {
	font-size: 20px;
	margin-top: 6px;
	color: #fff;
}
</style>
<script type="text/javascript">
myFunction();
	function myFunction() {
		setTimeout(function() {
			window.location.href = "/redirect?msisdn=<%=request.getParameter("msisdn")%>&svc_id=<%=request.getParameter("svc_id")%>";
		}, 7000);
	}
</script>
<body>
	<div id="loader">
		<div class="loader">
			<div class="spinner-border text-white"
				style="width: 7rem; height: 7rem;" role="status">
				<span class="visually-hidden">Loading...</span>
			</div>
			<p>Please Wait...</p>
		</div>
	</div>
</body>
</html>