<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Formulario de alta | Administrador SegurJava</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="./css/modulo_web.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="./js/ajax.js"></script>
<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> 
<script type="text/javascript">
	var sensor_sel;
	function setSensor(sel) { 
		sensor_sel = sel;
		console.log("sensor "+sensor_sel);
	}
$(function(){	
	$("div#lista_sensores div.list-group a.list-group-item").click(function() { //Escucha el evento para verificar una alarma.
// 		$(this).toggleClass("active")
		console.log("sensor "+$(this).attr("id"))
		sensor_sel = $(this).attr("id")
	});
	$("#lista_sensores > div.list-group > div.list-group-item > button.btn-link").click(function() {
	console.log($(this).text());
		if($(this).text() == "Quitar sensor ") {
			console.log("Quitando sensor... "+sensor_sel);
		$.ajax({// Envía una petición para eliminar el sensor. 
			url:"${pageContext.request.contextPath}/sensor/eliminar/"+sensor_sel,
			type:"DELETE"
// 			contentType: "application/x-www-form-urlencoded",
// 		    data: {"idSensor": sensor_sel} 
// 		    success: $(this).addClass("btn-success"),
// 		    error: function( jqXHR, textStatus, errorThrown ) { $(this).addClass("btn-warning") }
		  }).done(function(){
				$("div#lista_sensores div.list-group a.list-group-item").filter("[id='"+sensor_sel+"']").hide();
		}).fail(function(jqXHR, textStatus, errorThrown){
			$(this).append("btn-warning");
		});
// 		$(this).removeClass("btn-success")
// 		$(this).addClass("btn-info")
// 		$(this).prop("disabled", true)
		}
		if($(this).text() == "Contratar sensor ") {
			$("div.panel#caja_lugar").toggle();
		}
	})

	$("div#lista_sensores > div.list-group > a.list-group-item").click(function() {
		console.log("Sensor "+$(this).attr("id")+" seleccionado.");
// 		$("#sensor_sel").text($(this).children("input").val());
		$("button.btn.btn-link[type='button']:nth-child(1)").attr("value", $(this).attr("id"));
		$("button.btn.btn-link[type='button']:nth-child(1)").prop("disabled", false);
		$(this).addClass("active") //Muestra como marcado el sensor seleccionado.
		$(this).siblings().not(this).removeClass("active") //Desmarca el resto de sensores.
	})
		
	$("form").submit(function(event) {
			console.log("Handler for .submit() called.");
			event.preventDefault();
			$.post( "${pageContext.request.contextPath}/sensor/contratar/${cliente.dni }", $(this).serialize())
  	.done(function( data, status) {
    console.log( "Data Loaded: " + data + "\nStatus: " + status);
    if(data != -1) //console.log(typeof data);
	    $('<a href="#" id="'+data+'" class="list-group-item" onclick="setSensor('+data+')">'+$("input[name=lugar]").val()+'</a>')
	    .insertBefore("#lista_sensores > div.list-group > div.list-group-item");
  	})
  	.fail(function(jqXHR, textStatus, errorThrown){
		$(this).children("button").addClass("btn-warning");
		});
	});
});
</script>
</head>
<body>
<jsp:include page="/WEB-INF/view/menu_admin.jsp" flush="true">
<jsp:param value="alta_cliente" name="pagina" /></jsp:include>
<div class="container">
<div class="col-sm-8"><c:set var="cliente" value="${requestScope.cliente}" />
		<div class="alert alert-info">Introduzca los datos de los sensores contratados	<br/>	</div>
<div class="panel panel-default" id="datos_cliente">
	<div class="panel-heading">Datos del cliente</div>
	<table class="panel-body table table-bordered">
		<tr><th>DNI: </th><td>${cliente.dni }</td></tr>
		<tr><th>Usuario</th><td>${cliente.usuario }</td></tr>
		<tr><th>Nombre: </th><td>${cliente.nombre }</td></tr>
		<tr><th>Dirección</th><td>${cliente.direccion }</td></tr>
		<tr><th>Email: </th><td>${cliente.email }</td></tr>
		<tr><th>Nº de Cuenta</th><td>${cliente.cuenta }</td></tr>
		<tr><th>Aviso Policía: </th><td>${cliente.avisoPolicia == true? 'contratado':'no contratado'}</td></tr>
		<tr><th>Estado</th><td>${cliente.habilitado == true? 'habilitado':'deshabilitado'}</td></tr>
	</table>
</div>
</div>
	<c:set var="sensores" value="${requestScope.sensores}" />
					<div class="col-sm-4" id="lista_sensores"><a href="adminAltaCliente.to" class="btn btn-default pull-right">Nuevo cliente</a>
					<h3>Sensores <span class="glyphicon glyphicon-equalizer" aria-hidden="true"></span> </h3>
						<div class="list-group">
							<c:choose>
								<c:when test="${!empty sensores}">
									<c:forEach var="sensor" items="${sensores}">
										<a href="#" id="${sensor.idSensor}" class="list-group-item" <%--  onclick="setSensor('${sensor.idSensor}')" --%>>${sensor.ubicacion}</a>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<div class="alert alert-warning">No tiene sensores contratados.</div>
								</c:otherwise>
							</c:choose>
							<div class="list-group-item">
								<button type="button" class="btn btn-link" disabled>Quitar sensor </button><span class="glyphicon glyphicon-remove-circle"></span> 
								<button type="button" class="btn btn-link">Contratar sensor <span class="glyphicon glyphicon-plus-sign"></span></button>
							</div>
						</div>
					<div class="panel panel-default" id="caja_lugar">
							<div class="panel-heading">Datos del nuevo sensor</div>
							<div class="panel-body">
								<form action="clienteAltaSensor.do" method="post">
									<label>Ubicación:</label><input type="text" name="lugar" class="form-control" style="width: 70%" placeholder="sala o estancia" />
									<button type="submit" class="btn btn-primary">Añadir</button>
								</form>
							</div>
						</div>
					</div>
</div></body>
</html>