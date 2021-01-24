<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<meta charset="ISO-8859-1">
<head>
<title>View Sensors here</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="./css/modulo_web.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="./js/ajax.js"></script>
<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">
var alerta_alarmas
function suscribir(){
	var url="${pageContext.request.contextPath}/alarmas/cliente/${sessionScope.persona.dni}"; 
	alerta_alarmas=new EventSource(url); //objeto para consumir flujos de datos
	//comenzamos suscripción
	alerta_alarmas.addEventListener("message",function(e){
		console.log("Obj recibido: "+e.data);
// debugger;
		var alarm = e.data; //0
/* 		var array=JSON.parse(e.data);
		for(var i=0;i<array.length;i++){
			alarm = array[i] */
		console.log(alarm);
		if(alarm!=0 && $("div#caja_sensor button.btn[value='"+alarm+"']").length > 0){ //Comprueba si el valor del sensor recibido existe en el panel.
// 			console.log($("div#caja_sensor button.btn[value='"+alarm.id.idSensor+"']").parent().find("label.switch input[type='checkbox']"))
			if($("div#caja_sensor button.btn[value='"+alarm+"']").parent().find("input[type='checkbox']").prop("checked") == true); //Comprueba si el sensor está activo.
			$("div#caja_sensor button.btn").filter("[value='"+alarm+"']").attr("class","btn btn-danger");
			}
// 		}
	});
}

function cancelar(){
	alerta_alarmas.close();
	console.log("Suscriptor desconectado.");
}
// window.onunload = cancelar;
window.onclose = cancelar;
/* $(window).on("beforeunload", function() { 
    alert("Do you really want to close?"); 
})	 */
$(function(){ 
<c:if test="${!empty sensores}">
suscribir();	//Llama a la función para suscribirse al flujo de alarmas.
/*	var menu_sensors = ${sensores};
	if(!$.isEmptyObject(menu_sensors)) { //Para cambiar la selección del menu.
		}
		$("ul.nav.navbar-nav").children().first().removeClass("active")
		$("ul.nav.navbar-nav").children().eq(1).addClass("active")
	} else {
	  	$("ul.nav.navbar-nav").children().first().addClass("active")
		$("ul.nav.navbar-nav").children().eq(1).removeClass("active")
	}*/
	$("div#caja_sensor button.btn").click(function() { //Escucha el evento para verificar una alarma.
		if($(this).attr("class") == "btn btn-danger") {
		$(this).removeClass("btn-danger");
		console.log("botón "+$(this).val());
		$.ajax({// Envía una petición para actualizar los datos. 
			url:"${pageContext.request.contextPath}/sensor/actualizar/modo",
// 			type:"GET",
// 			contentType: "application/x-www-form-urlencoded",
		    data: {"idSensor": $(this).val(), "modo": "NORMAL"}, 
		    success: $(this).addClass("btn-success")/*,
		    error: function( jqXHR, textStatus, errorThrown ) { $(this).addClass("btn-warning") }*/
		  })/*.done(function(){
			  $(this).addClass("btn-success");
		})*/.fail(function(jqXHR, textStatus, errorThrown){
			$(this).addClass("btn-warning");
		});
	$("div#caja_sensor button.btn-success").click(function() {
		$(this).removeClass("btn-success");
		$(this).addClass("btn-info");
// 		$(this).prop("disabled", true)
		})
		}
	})
		
	$("div#caja_sensor label.switch input[type='checkbox']").change(function() { //Escucha el evento de des/activar el sensor.
		console.log("botón "+$(this).val()+": "+$(this).prop("checked"));
		$(this).parent().siblings("button").toggleClass("disabled");
// 		console.log($(this).parent().siblings("button"))
		$.get("${pageContext.request.contextPath}/sensor/actualizar/estado",
				  {// Envía una petición para actualizar los datos. 
		    idSensor: $(this).val(),
		    estado: $(this).prop("checked")
		  }, function(data, status){
		    console.log("Data: " + data + "\nStatus: " + status);
		  });
		})
</c:if>		
		$("div#bajaModal > div.modal-dialog > div.modal-content > div.modal-footer > button[data-dismiss='modal']").click(function() { //Escucha el evento de deshabilitar el cliente.
		var dni_cliente = "${sessionScope.persona.dni}";
		console.log("Cliente con DNI: "+dni_cliente);
		console.log($(this).text());
		if($(this).text() == " Deshabilitar ") {
			window.location.href = "clienteBaja.do?dni="+dni_cliente;
		}
	})
		
}); 
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/view/menu_cliente.jsp" flush="true">
		<jsp:param value="panel" name="pagina" /></jsp:include>
	<%-- <div class="container-fluid">	
 <nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">segurJava</a>
    </div>
    <ul class="nav navbar-nav">
	<c:choose>
	<c:when test="${empty sensores}">
      <li class="active"><a href="#">Inicio</a></li>
      <li><a href="toDashboard">Ver Sensores</a></li>
    </c:when>
    <c:otherwise>
      <li><a href="toMenu">Inicio</a></li>
      <li class="active"><a href="#">Ver Sensores</a></li>
    </c:otherwise>
    </c:choose>
      <li><a href="toGestionarSensores">Gestionar Sensores</a></li>
      <li><a href="#" data-toggle="modal" data-target="#bajaModal">Darse de baja</a></li>
    </ul>
     <ul class="nav navbar-nav navbar-right">
      <li><a href="#"><span class="glyphicon glyphicon-user"></span> ${sessionScope.persona.nombre}</a></li>
      <li><a href="<c:url value="/j_spring_security_logout" />"><span class="glyphicon glyphicon-log-in"></span> Salir</a></li>
    </ul>
  </div>
</nav>
</div> --%>
	<!-- Modal
<div id="bajaModal" class="modal fade" role="dialog">
  <div class="modal-dialog"> -->

	<!-- Modal content
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Solicitar baja del sistema</h4>
      </div>
      <div class="modal-body">
        <p>Se deshabilitara su cuenta de cliente.</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-warning" data-dismiss="modal"><span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span> Deshabilitar </button>
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
      </div>
    </div>

  </div>
</div> Modal Baja -->
	<div class="container">
		<c:set var="sensores" value="${requestScope.sensores}" />
		<c:choose>
			<c:when test="${!empty sensores}">
				<h3>Sensores <span class="glyphicon glyphicon-equalizer" aria-hidden="true"></span></h3>
				<div class="row">
					<c:forEach var="sensor" items="${sensores}">
						<div class="col-xs-6 col-sm-4 col-md-2 text-right" id="caja_sensor">
							<c:choose>
								<c:when test="${sensor.modo == 'DETECCION'}">
									<button type="button" value="${sensor.idSensor}"
										class="btn btn-danger">${sensor.ubicacion }</button>
								</c:when>
								<c:when test="${sensor.estado == 'ACTIVADO'}">
									<button type="button" value="${sensor.idSensor}"
										class="btn btn-info">${sensor.ubicacion }</button>
								</c:when>
								<c:otherwise>
									<button type="button" value="${sensor.idSensor}"
										class="btn btn-info disabled">${sensor.ubicacion }</button>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${sensor.estado == 'ACTIVADO'}">
									<label class="switch"> <input type="checkbox"
										value="${sensor.idSensor}" checked="checked"> <span
										class="slider round"></span></label>
								</c:when>
								<c:otherwise>
									<label class="switch"> <input type="checkbox"
										value="${sensor.idSensor}"> <span class="slider round"></span></label>
								</c:otherwise>
							</c:choose>

						</div>
					</c:forEach>
				</div>
			</c:when>
			<c:otherwise>
				<div class="alert alert-info">Seleccione una opción del menu</div>
			</c:otherwise>
		</c:choose>
		<br /> <img class="img-rounded img-responsive" id="plano_planta" alt="Plano de la planta del domicilio u oficina."
			src="<c:url value='/img/plano_planta.png'/>" ismap="ismap" /> <br />
	</div>
</body>
</html>