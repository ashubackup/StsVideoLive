<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Ndoto Stream</title>
 <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,200;0,300;0,400;0,500;1,600;1,700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="css/login.css" />
<style>
body {
    background: #fb624f url(https://content.ndotomobile.com/Images/20200220082038.png);
    background-size: contain;
    background-position: center;
    background-repeat: no-repeat;
    position: relative;
    z-index: 1;
    margin: 0;
    padding:0px;
}
body:before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    height: 100%;
    width: 100%;
    background-color: #00000075;
    pointer-events: none;
    z-index: -1;
}
.loginmsg {
    background-color: #ffffffde;
    padding: 6px 40px;
    font-family: 'Poppins', sans-serif;
    line-height: normal;
    border-radius: 7px;
}
.loginmsg h4 {
    font-family: inherit;
    line-height: normal;
}
</style>
</head>
<body>
	<div class="loginmsg">
		<h4><%=(request.getAttribute("message")!=null)? request.getAttribute("message"):"" %></h4>
	</div>
</body>
</html>