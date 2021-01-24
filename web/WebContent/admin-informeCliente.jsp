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

	<center>
            <h1>Seleccione el cliente para crear el informe</h1>
            <br/><br/>
		<form  action="adminInformeCliente.do" method="post">
			<select name="dni">
				<c:forEach var="c" items="${sessionScope.clientes}">
					<option value="${c.dni}" ${not empty param.dni && param.dni == c.dni ? 'selected' : ''}>${c.dni} - ${c.usuario} - ${c.nombre}</option>				
				</c:forEach>
				
			</select>
			<br/><br/>
			<input type="submit" value="Generar informe de cliente"/>
			
		</form>
		<br/><br/>
		<c:set var="alarmas" value="${requestScope.alarmas}"/>   
		<c:choose>
			<c:when test="${!empty alarmas}">
		
				<table border="1">
				    <tr><th>Sensor</th><th>Fecha de la alarma</th></tr>		    	
				    	<c:forEach var="alm" items="${alarmas}">
				    		<tr>
				    			<td>${alm.idSensor}</td>
								<td>${alm.id.fecha}</td>
								
							</tr>		    	
				    	</c:forEach>
				</table>
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