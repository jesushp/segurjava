<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication var="userPrincipal" property="principal" />
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert error here</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container-fluid">	
 <nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">segurJava</a>
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#sjNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span> 
      </button>
    </div>
    <div class="collapse navbar-collapse" id="sjNavbar">
     <ul class="nav navbar-nav navbar-right">
      <li><a href="#"><span class="glyphicon glyphicon-user"></span> <sec:authentication property="name" /> </a>
      <li><a href="j_spring_security_logout"><span class="glyphicon glyphicon-log-in"></span> <fmt:message key="menu.log_out"/></a></li>
    </ul>
    </div>  </div>
</nav>
</div>
	<div class="container">
		<div class="alert alert-warning"><!-- ${pageContext.request.queryString} -->
			<c:choose>
				<c:when test="${empty exception }">
					<c:if test="${param.status == '400'}"><strong>La dirección de la págnia no es correcta.</strong></c:if>
					<c:if test="${param.status == '403'}"><strong>Acceso prohibido para el rol del usuario ${userPrincipal.username}.</strong> <br /> ${userPrincipal.authorities} </c:if>
					<c:if test="${param.status == '404'}"><strong>Págnia no encontrada en el servidor.</strong></c:if>
					<c:if test="${param.status == '500' || empty param.status}"><strong>Se ha producido un error en el servidor.</strong></c:if>
				</c:when>
				<c:otherwise><strong>Se ha producido un error en el servidor.</strong> <br />
				<sec:authorize access="hasRole('ROLE_ADMIN')">Failed URL: <%-- ${pageContext.request.requestURI}/ --%>${url}<br />
    			Exception:  ${exception}</sec:authorize></c:otherwise>
			</c:choose><br /> <br />
			<a href="${pageContext.request.contextPath}/login.do" class="alert-link">Volver al inicio.</a>
		</div>
	</div>
</body>
</html>