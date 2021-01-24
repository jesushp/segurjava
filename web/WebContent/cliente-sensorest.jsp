<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<head>
<title>Manage Sensors here</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="./css/modulo_web.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="./js/ajax.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">
	
$(function(){ /*
let menu_sensors = ${!empty sensores}
	if(menu_sensors) { //Para cambiar la selección del menu.
		$("ul.nav.navbar-nav").children().first().removeClass("active")
		$("ul.nav.navbar-nav").children().eq(1).addClass("active")
	} else {
	  	$("ul.nav.navbar-nav").children().first().addClass("active")
		$("ul.nav.navbar-nav").children().eq(1).removeClass("active")
	}*/
	function onBlur() {
		windowFocus=false;
		console.log("La ventana ha perdido el foco.")
		};
	function onFocus(){
		windowFocus=true;
		console.log("Esta ventana tiene el foco.")
		};
		 
		if (/*@cc_on!@*/false) { // check for Internet Explorer
		document.onfocusin = onFocus;
		document.onfocusout = onBlur;
		} else {
		window.onfocus = onFocus;
		window.onblur = onBlur;
		}
	var sensor_sel
	$("div#lista_sensores div.list-group a.list-group-item").click(function() { //Escucha el evento para verificar una alarma.
// 		$(this).toggleClass("active")
		console.log("sensor "+$(this).attr("id"))
		sensor_sel = $(this).attr("id")
	});
	$("div#lista_sensores div.list-group div.list-group-item a").click(function() {
		if($(this).text() == "Quitar sensor ") {
			console.log(sensor_sel);
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
			$("div.panel#caja_lugar").toggle()
		}
	})
		
	$("div#bajaModal > div.modal-dialog > div.modal-content > div.modal-footer > button[data-dismiss='modal']").click(function() { //Escucha el evento de deshabilitar el cliente.
		console.log("botón "+$(this).val()+": "+$(this).prop("checked"));
		if($(this).text() == "Deshabilitar") {
		$(this).parent().siblings("button").toggleClass("disabled");
// 		console.log($(this).parent().siblings("button"))
		$.get("${pageContext.request.contextPath}/cliente/deshabilitar",
				  {// Envía una petición para actualizar los datos. 
		    idCliente: "${sessionScope.persona.dni}"
		  }, function(data, status){
		    console.log("Data: " + data + "\nStatus: " + status);
		  });
		}
		})
}); 
</script>
</head>
<body>
<jsp:include page="/WEB-INF/view/menu_cliente.jsp" flush="true">
<jsp:param value="sensores" name="pagina"/></jsp:include>
	<div class="container">	
		<c:set var="sensores" value="${requestScope.sensores}" />
			<div class="col-sm-4" id="lista_sensores">
	<h3>Sensores <span class="glyphicon glyphicon-equalizer" aria-hidden="true"></span></h3>
				<div class="list-group">
				<c:choose>
					<c:when test="${!empty sensores}">
						<c:forEach var="sensor" items="${sensores}">
							<a href="#" id="${sensor.idSensor}" class="list-group-item">${sensor.ubicacion }</a>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="alert alert-warning">No tiene sensores contratados.</div>
					</c:otherwise>
				</c:choose>
				<div class="list-group-item"><a href="#">Quitar sensor <span class="glyphicon glyphicon-remove-circle"></span></a>
					<a href="#">Contratar sensor <span class="glyphicon glyphicon-plus-sign"></span></a></div>
				</div>
				<div class="panel panel-default" id="caja_lugar">
					<div class="panel-heading">Datos del nuevo sensor</div>
					<div class="panel-body">
					<form action="clienteAltaSensor.do" method="post">
					<label>Lugar de ubicación:</label><input type="text" name="lugar" class="form-control" style="width:30%" placeholder="sala o estancia"/>
					<button type="submit" class="btn btn-default">Añadir</button>
					</form>
					</div>
				</div>
			</div>
<div class="col-sm-8"><div class="panel panel-default" id="lista_alarmas">
	<div class="panel-heading">Alarmas</div>
			<table class="panel-body table table-hover">
			<tr><th>Sensor</th><th>Fecha</th></tr>
					<c:forEach var="alarm" items="${alarmas}">
					<tr><td>${alarm.idSensor }</td><td>${alarm.fecha }</td></tr>
					</c:forEach>
			</table></div></div>
	<br />
	<br />
	</div>
</body>
</html>