<!DOCTYPE HTML >
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>Administrador</title>
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> 

<c:set var="seleccionado" value="${param.fecIni}"/><!-- ${requestScope.valorFecIni} -->
<script type="text/javascript">
function valorFechas(){
 	if("${param.fecIni}"!=""){
		document.getElementById("fecIni").value = "${param.fecIni}";
		document.getElementById("fecFin").value = "${param.fecFin}";
	} 
	console.log("Función valor fechas invocada...");
}
console.log("${tipo_informe}");
</script>		

</head>
<body onload="valorFechas();">
	<jsp:include page="/WEB-INF/view/menu_admin.jsp" flush="true">
		<jsp:param value="informe_temporal" name="pagina" /></jsp:include>
<%-- <div class="container-fluid">	
 <nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">segurJava</a>
    </div>
    <ul class="nav navbar-nav">
      <li><a href="toMenu" class="active">Inicio</a></li>
	<li><a href="toAdminAltaCliente">Dar de alta cliente</a></li>
<li><a href="#" class="active">Generar informe de un intervalo temporal</a></li>
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


Seleccione el intervalo de fechas para crear el informe<br/><br/>
		<form class="form-inline" action="${pageContext.request.contextPath}/adminInformeTemporal/1" method="get">
			<div class="form-group">
				<label>Fecha de inicio:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="date" class="form-control" id="fecIni" name="fecIni" value="2019-10-01"></label>
			</div>
			<div class="form-group">
				<label>Fecha de fin:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="date" class="form-control" id="fecFin" name="fecFin" value="2019-12-31"></label>
			</div>
			<div class="form-group">
			<button type="submit" class="btn btn-primary">Generar informe temporal</button> <label><input type="checkbox" name="agrupar"> Agrupar por cliente</label>
			<!-- <button type="submit" formaction="adminInformeTemporalClientes.do" class="btn btn-default">Agrupado por Cliente</button> -->
			</div><br/>
			<div class="form-group pull-right">
				<label>Nº de filas: <select class="form-control" name="n_filas"><option ${param.n_filas == '5'?'selected': ''}>5</option><option ${param.n_filas == '10'?'selected':''}>10</option><option ${param.n_filas == '15'?'selected':''}>15</option></select></label>
			</div>
		</form>
		<br/><br/>
<c:set var="informe" value="${informe}"/> 
<c:choose>
	<c:when test="${!empty informe}">
		<table class="table table-stripped">
		    <tr><td width="300px" align="center"><br/><b>Cliente</b><br/>&nbsp;</td><td width="300px" align="center"><br/><b>Sensor</b><br/>&nbsp;</td><td width="300px" align="center"><br/><b>Fecha de la alarma</b><br/>&nbsp;</td></tr>		    	
	    	<c:forEach var="inf" items="${informe}">
	    		<tr>
	    			<td align="center"><br/>${inf.dni} &diams; ${inf.nombre} (${inf.usuario })<br/>&nbsp;</td>
	    			<td align="center"><br/>${inf.idSensor} &diams; ${inf.ubicacion}<br/>&nbsp;</td>
					<td align="center"><br/><fmt:formatDate pattern="yyyy-MM-dd : HH:mm:ss" value="${inf.fecha}" /><br/>&nbsp;</td>
				</tr>
	    	</c:forEach>
		</table>
   <br/>    
<c:set var="currentPage" value="${requestScope['javax.servlet.forward.request_uri']}"/> <%--This will give you the path to current page  --%>
<c:set var="splitURI" value="${fn:split(currentPage, '/')}"/> <%--This will split the path of current page --%>
   <c:set var="pagActual" value="${splitURI[fn:length(splitURI)-1]}" /><%--This will give you the last value of url --%>
	<label>Página nº </label><br><jsp:include page="/WEB-INF/view/paginacion_b-fechas.jsp" flush="true">
		<jsp:param value="${pagActual}" name="pagina" /></jsp:include>
	</c:when>
	<c:otherwise>
		<c:if test="${!empty seleccionado}">
				No hay alarmas en este intervalo de tiempo
			</c:if>
		<c:if test="${empty seleccionado}">No se ha introducido la fecha inicial del intervalo.
			</c:if>
	</c:otherwise>	
</c:choose>	

</div>
</body>
</html>