<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
<head>
<title>Seleccione el cliente</title>
<meta http-equiv="Content-Type" content="text/html;  charset=ISO-8859-1" >
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> 

<script type="text/javascript">
function valorDNI(valor){
	if(valor != ""){	
// 		var selDni = document.getElementById("dni");
		if(valor == "-1") {
			console.log("Valor: "+valor);
// 			document.querySelector("input[type='submit']").disabled = true;
		} else {
// 			selDni.value=valor;
// 		selDni.options[selDni.options.selectedIndex].selected = true;
// 			document.querySelector("input[type='submit']").disabled = false;
		}
	} 
}
function comprobarOpcion(valor) {
	console.log("Valor: "+valor);
/*   	if(document.getElementById("dni").value != "Seleccione el cliente") document.querySelector("input[type='submit']").disabled = false;
	else document.querySelector("input[type='submit']").disabled = true;   */
}
console.log("Valor seleccionado: ${param.dni}");
</script>		
</head>
<c:set var="seleccionado" value="${param.dni}"/> 
<body onload="valorDNI('${seleccionado}');">
	<jsp:include page="/WEB-INF/view/menu_admin.jsp" flush="true">
		<jsp:param value="informe_cliente" name="pagina" /></jsp:include>
<%-- <div class="container-fluid">	
 <nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">segurJava</a>
    </div>
    <ul class="nav navbar-nav">
      <li><a href="toMenu" class="active">Inicio</a></li>
	<li><a href="toAdminAltaCliente">Dar de alta cliente</a></li>
<li><a href="toAdminInformeTemporal">Generar informe de un intervalo temporal</a></li>
<li><a href="#" class="active">Generar informe de un cliente</a></li>
    </ul>
     <ul class="nav navbar-nav navbar-right">
      <li><a href="#"><span class="glyphicon glyphicon-user"></span> ${sessionScope.persona.nombre}</a></li>
      <li><a href="<c:url value="/j_spring_security_logout" />"><span class="glyphicon glyphicon-log-in"></span> Salir</a></li>
    </ul>
  </div>
</nav>
</div> --%>
<div class="container">
Seleccione el cliente para crear el informe<br/><br/>
<c:set var="clientes" value="${requestScope.clientes}"/> 
<form class="form-inline" action="adminInformeFullCliente.do" method="post">
		<div class="form-group">
	<select class="form-control" name="dni" id="dni" onchange="comprobarOpcion(this.value)">
		<option value="-1">Seleccione el cliente</option>
		<c:forEach var="c" items="${clientes}"> <%-- varStatus="ops" ops.index--%>
			<option value="${c.dni}" ${not empty param.dni && param.dni == c.dni ? 'selected' : ''}>${c.dni} - ${c.usuario} - ${c.nombre}</option>				
		</c:forEach>
	</select></div>
	<input type="submit" class="btn btn-primary" value="Generar informe de cliente"/>
</form><br/><br/>
		<c:choose>
			<c:when test="${seleccionado != '-1'}">
				<c:set var="sensores" value="${requestScope.sensores}" />
			</c:when>
			<c:otherwise>
				<div class="alert alert-info">Seleccione un cliente del menú desplegable</div>
			</c:otherwise>
		</c:choose>
		<c:choose>
	<c:when test="${not empty sensores}">
<div class="panel-group" id="accordion">
		<!-- <table class="table table-stripped">
		    <tr><td width="300px" align="center"><br/><b>Sensor</b><br/>&nbsp;</td><td width="200px" align="center"><br/><b>Fecha de la alarma</b><br/>&nbsp;</td></tr></table> -->		    	
  					<c:forEach var="sens" items="${sensores}">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#collapse${sens.idSensor}">Sensor:&nbsp;
										${sens.idSensor} </a>
								</h4>
							</div>
							<div id="collapse${sens.idSensor}"
								class="panel-collapse collapse in">
								<div class="panel-body">
									<table class="table table-stripped">
										<tr>
											<td width="300px" align="center"><br />
											<b>Ubicación</b><br />&nbsp;</td>
											<td width="200px" align="center"><br />
											<b>Fecha de la alarma</b><br />&nbsp;</td>
										</tr>
										<tr>
											<td align="center">${sens.ubicacion} &nbsp; <br /></td>
											<td align="center"><c:forEach var="alm" items="${sens.alarmas}">
												<br /> &nbsp;<fmt:formatDate pattern="yyyy-MM-dd : HH:mm:ss" value="${alm.id.fecha}" /><br />&nbsp;
											</c:forEach></td>
										</tr>
									</table>
								</div>
							</div>
					</c:forEach>
		</div>
	</c:when>
	<c:otherwise>
		<c:if test="${!empty seleccionado}">
			<c:if test="${empty alarmas}">
				<div class="alert alert-warning">No hay alarmas para este cliente</div>
			</c:if>
		</c:if>
	</c:otherwise>		
</c:choose>
</div>

</body>
</html>