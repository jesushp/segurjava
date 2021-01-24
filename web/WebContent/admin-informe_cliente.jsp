<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	
	pageEncoding="ISO-8859-1" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<title>seleccion</title>
<meta http-equiv="Content-Type" content="text/html;  charset=ISO-8859-1" >
</head>
<body>
${param.dni }
	<center>
            <h1>Seleccione el cliente para crear el informe</h1>
            <br/><br/>
		<form  action="adminInformeCliente.to" method="GET">
			<select name="dni">
				<c:forEach var="c" items="${clientes}" varStatus="ops">
					<option value="${ops.index}" ${not empty param.dni && param.dni == ops.index ? 'selected' : ''}>${c.dni} - ${c.usuario} - ${c.nombre}</option>				
				</c:forEach>
				
			</select>
			${clientes.size()}<label><input type="checkbox" name="actualizar"> Actualizar consulta</label>
			<br/><br/>
			<input type="submit" value="Generar informe de cliente">
		</form>
		<br/><br/>
		<c:set var="sensores" value="${clientes[param.dni].sensores}"/>   
		<c:choose>
			<c:when test="${not empty sensores}">
				<c:forEach var="sen" items="${sensores}">
					<c:if test="${not empty sen.alarmas}">
					<table border="1">
						<tr>
							<th>Sensor</th>
							<th>Fecha de la alarma</th>
						</tr>
						<c:forEach var="alm" items="${sen.alarmas}">
							<tr>
								<td>${alm.idSensor}</td>
								<td>${alm.id.fecha}</td>

							</tr>
						</c:forEach>
					</table></c:if>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<h2>No hay alarmas para este cliente</h2>
			</c:otherwise>
		</c:choose>

		<br/><br/>
		<a href="admin-menu.jsp">Volver</a>
	
	</center>

</body>
</html>