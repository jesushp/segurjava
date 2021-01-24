<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources.application" /> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><fmt:message key="welcome.heading"/> | <fmt:message key="welcome.title"/></title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> 
</head>
<body>
<div class="container">
	<div class="col-sm-3 col-md-4"><h3><fmt:message key="welcome.heading"/></h3>
	<form action="login" method="post">
		<div class="form-group">
			<label><fmt:message key="welcome.user"/></label><input type="text" name="username" class="form-control" placeholder="Usuario" autocomplete="name"/>
		</div>
		<div class="form-group">
			<label><fmt:message key="welcome.password"/></label><input type="password" name="password" class="form-control" placeholder="Contraseña"/>
		</div>
		
	<!-- <a href="toRegistro">Registrarse</a> -->
		<button name="submit" type="submit" value="submit" class="btn btn-primary pull-right">Enviar</button>
	</form>
	<br/><br/>
		<c:if test="${param.error != null}">
			<div class="alert alert-danger alert-dismissible" >
				<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
				<strong><fmt:message key="login.error" /></strong>
			</div>
		</c:if>
	</div>
	</div>
</body>
</html>