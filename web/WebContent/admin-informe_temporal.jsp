<!DOCTYPE HTML >
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
<form class="form-inline" action="adminInformeTemporal.do" method="post">
		<div class="form-group">
	<label>Fecha de inicio:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input type="date" class="form-control" id="fecIni" name="fecIni" placeholder="2019-10-01"></div>		
	<div class="form-group"><label>Fecha de fin:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input type="date" class="form-control" id="fecFin" name="fecFin" placeholder="2019-12-31"></div>
	<button type="submit" class="btn btn-primary">Generar informe temporal</button>
	<button type="submit" formaction="adminInformeTemporalClientes.do" class="btn btn-default">Agrupado por Cliente</button>
</form><br/><br/>
<c:set var="informe" value="${informe}"/> 
<c:choose>
	<c:when test="${!empty informe}">
<div class="panel-group" id="accordion">
		    <b>Cliente</b>   &diams; &nbsp;<b>Nombre</b>&nbsp; (<b>Usuario</b>)		    	
	    	<c:forEach var="inf" items="${informe}">
  <div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-parent="#accordion" href="#collapse_${inf.usuario}">${inf.dni}  &diams; ${inf.nombre} (${inf.usuario})</a>
      </h4>
    </div>
	    <%-- Muestra las alarmas por Cliente --%>
		<div id="collapse_${inf.usuario}" class="panel-collapse collapse">
      <div class="panel-body"><table class="table table-stripped">
		    <tr><td width="300px" align="center"><br/><b>Sensor</b><br/>&nbsp;</td><td width="300px" align="center"><br/><b>Ubicación</b><br/>&nbsp;</td><td width="300px" align="center"><br/><b>Fecha de la alarma</b><br/>&nbsp;</td></tr>		    	
	    	<c:forEach var="als" items="${inf.alarmas}">
	    		<tr>
	    			<td align="center"><br/>${als.idSensor} &diams; </td>
					<td align="center"><br/>${als.ubicacion}&nbsp;</td>
					<td align="center"><br/><fmt:formatDate pattern="yyyy-MM-dd : HH:mm:ss" value="${als.fecha}" /><br/>&nbsp;</td>
				</tr>
	    	</c:forEach>
		</table></div>
    </div>
    </div>
	    	</c:forEach></div>
	</c:when>
	<c:otherwise>
		<c:if test="${!empty seleccionado}">
			<c:if test="${empty informe}">
				No hay alarmas en este intervalo de tiempo
			</c:if>
		</c:if>
	</c:otherwise>	
</c:choose>	

</div>
</body>
</html>