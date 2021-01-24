<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources.application" /> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Menu Administrador</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> 

</head>
<body>
	<jsp:include page="/WEB-INF/view/menu_admin.jsp" flush="true">
		<jsp:param value="menu" name="pagina" /></jsp:include>
<%-- <div class="container-fluid">	
 <nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">segurJava</a>
    </div>
    <ul class="nav navbar-nav">
      <li><a href="#" class="active">Inicio</a></li>
	<li><a href="toAdminAltaCliente">Dar de alta cliente</a></li>
	<li><a href="toAdminInformeTemporal">Generar informe de un intervalo temporal</a></li>
	<li><a href="toAdminInformeFullCliente">Generar informe de un cliente</a></li>
    </ul>
     <ul class="nav navbar-nav navbar-right">
      <li><a href="#"><span class="glyphicon glyphicon-user"></span> ${sessionScope.persona.nombre}</a></li>
      <li><a href="#"><span class="glyphicon glyphicon-log-in"></span> Salir</a></li>
    </ul>
  </div>
</nav>
</div> --%>
	<div class="container">
		<h3>Menu Administrador</h3>
		<!-- <br />Opciones:<br /> <br /> -->
		<br />
		<c:if test="${requestScope.encript_toast == true}">
			<div class="alert alert-warning alert-dismissible" >
				<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
				<strong><fmt:message key="menu.encript-toast" /></strong>
			</div>
		</c:if>
	</div>
</body>
</html>