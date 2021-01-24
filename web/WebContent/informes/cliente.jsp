<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert pages here</title>
</head>
<body>
	<div class="container" style="margin-top: 20px;">
<c:set var="seleccionado" value="${param.dni}"/> 
Seleccione el cliente para crear el informe<br/><br/>
<c:set var="clientes" value="${requestScope.clientes}"/> 
<form class="form-inline" action="informes" method="get">
		<div class="form-group">
	<select class="form-control" name="dni" id="dni" >
		<option value="-1">Seleccione el cliente</option>
		<c:forEach var="c" items="${clientes}"> <%-- varStatus="ops" ops.index--%>
			<option value="${c.dni}" ${not empty param.dni && param.dni == c.dni ? 'selected' : ''}>${c.dni} - ${c.usuario} - ${c.nombre}</option>				
		</c:forEach>
	</select></div>
	<input type="submit" class="btn btn-primary" value="Generar informe de cliente"/>
</form><br/><br/>
		<c:choose>
			<c:when test="${seleccionado != '-1'}">
			</c:when>
			<c:otherwise>
				<div class="alert alert-info">Seleccione un cliente del menú desplegable</div>
			</c:otherwise>
		</c:choose>
		<jsp:useBean id="pagedListHolder" scope="request" type="org.springframework.beans.support.PagedListHolder" />
		<c:url value="/informes" var="pagedLink">
			<c:param name="dni" value="${param.dni }" />
			<c:param name="p" value="~" />
		</c:url>
		<c:choose>
		<c:when test="${not empty pagedListHolder.pageList}">

		<tg:paging pagedListHolder="${pagedListHolder}" pagedLink="${pagedLink}" />
			<c:forEach items="${pagedListHolder.pageList}" var="sens">
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
											<fmt:formatDate pattern="yyyy-MM-dd : HH:mm:ss" value="${alm.id.fecha}" /><br><br />&nbsp;
											</c:forEach></td>
										</tr>
									</table>
								</div>
							</div>
						</div>
			</c:forEach>
		<tg:paging pagedListHolder="${pagedListHolder}" pagedLink="${pagedLink}" />
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