<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spr" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Formulario de alta | Administrador SegurJava</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src='http://cdnjs.cloudflare.com/ajax/libs/modernizr/2.0.6/modernizr.min.js' type='text/javascript'></script>
<script type="text/javascript">
function ocultarCamposCliente(rol) {
	console.log(rol);
	$('form#cliente').toggleClass('form_admin');
}
$(function(){
	$('select#rol').on('change', function() {
// 	console.log($(this).val());
	if($(this).val() == 'ROLE_USER') {
		$('form#cliente').removeClass('form_admin');
		$('div.campo_cliente').find('input').prop('disabled', false);
// 		$('form#cliente').attr('action', 'adminAltaCliente.do');
	}
	else {
// 		$('form#cliente').addClass('form_admin');
		$('div.campo_cliente').find('input').prop('disabled', true);
// 		$('form#cliente').attr('action', 'adminAltaUsuario.do');
	}
	})
	$('form#cliente').on('reset', function() {
		$('form#cliente').removeClass('form_admin');
		$('div.campo_cliente').find('input').prop('disabled', false);
// 		$('form#cliente').attr('action', 'adminAltaCliente.do');
	});
});
</script>
<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> 
<style>
/* Oculta los campos cliente del formulario cuando cambia el rol seleccionado */
form.form_admin div div.form-group.campo_cliente {
	display: none;
}

/*the container must be positioned relative:*/
.autocomplete {
  position: relative;
/*   display: inline-block; */
}

.autocomplete-items {
  position: absolute;
  border: 1px solid #d4d4d4;
  border-bottom: none;
  border-top: none;
  z-index: 99;
  /*position the autocomplete items to be the same width as the container:*/
  top: 100%;
  left: 0;
  right: 0;
}

.autocomplete-items div {
  padding: 10px;
  cursor: pointer;
  background-color: #fff; 
  border-bottom: 1px solid #d4d4d4; 
}

/*when hovering an item:*/
.autocomplete-items div:hover {
  background-color: #e9e9e9; 
}

/*when navigating through the items using the arrow keys:*/
.autocomplete-active {
  background-color: DodgerBlue !important; 
  color: #ffffff; 
}
</style>

</head>
<body>
	<jsp:include page="/WEB-INF/view/menu_admin.jsp" flush="true">
		<jsp:param value="alta_cliente" name="pagina" /></jsp:include>
<%-- <div class="container-fluid">	
 <nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">segurJava</a>
    </div>
    <ul class="nav navbar-nav">
      <li><a href="toMenu" class="active">Inicio</a></li>
	<li><a href="#" class="active">Dar de alta cliente</a></li>
<li><a href="toAdminInformeTemporal">Generar informe de un intervalo temporal</a></li>
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
<div class="alert alert-info">
Introduzca los datos del nuevo cliente<br/></div>

	<f:form class="form-horizontal" autocomplete="off" action="adminAltaCliente.do" method="post" modelAttribute="cliente">
		<spr:hasBindErrors name="cliente">
			<c:if test="${not empty errors.allErrors}"><div class="alert alert-warning" role="alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4>Errores detectados:</h4>
				<ul><c:forEach var="error" items="${errors.allErrors}"><li><b><spr:message message="${error}" /></b></li></c:forEach></ul>
			</div></c:if>				
		</spr:hasBindErrors>
			<%-- <f:errors class="has-error" /> --%>
	<div class="col-sm-4 col-sm-push-8">
		<c:set var="rolError"><f:errors path="rol" /></c:set><div class="form-group ${not empty rolError ? 'has-error' : ''}">
			<label class="col-sm-6 control-label">Rol:</label>
			    <div class="col-sm-6">
			<f:select class="form-control" path="rol" id="rol" >
				<f:option value="ROLE_USER" selected="true" >Cliente</f:option>
				<f:option value="ROLE_ADMIN" >Administrador</f:option>
			</f:select><span class="help-block">${rolError }</span></div>
		</div>
		<c:set var="habilitadoError"><f:errors path="habilitado" /></c:set><div class="form-group ${not empty habilitadoError ? 'has-error' : ''}">
			<f:label path="habilitado" class="col-sm-6 control-label">Habilitado:</f:label>
			    <div class="col-sm-6">
			<%-- <f:select class="form-control" path="habilitado" id="habilitado">
				<f:option value="true" selected="selected" >Sí</f:option>
				<f:option value="false" >No</f:option>
			</f:select> --%>
			<div class="radio-inline"><f:radiobutton path="habilitado" value = "true" label = "Sí" /></div>
			<div class="radio-inline"><f:radiobutton path="habilitado" value = "false" label = "No" /></div>
			<span class="help-block">${habilitadoError }</span></div>
			</div>
		<c:set var="policiaError"><f:errors path="avisoPolicia" /></c:set><div class="form-group campo_cliente ${not empty policiaError ? 'has-error' : ''}">
			<f:label path="avisoPolicia" class="col-sm-6 control-label">Aviso a la policia:</f:label>
			    <div class="col-sm-6">
			<%-- <f:select class="form-control" path="avisoPolicia" id="avisoPolicia">
				<f:option value="true" selected="selected" >Sí</f:option>
				<f:option value="false" >No</f:option>
			</f:select> --%>
			<div class="radio-inline"><f:radiobutton path="avisoPolicia" value = "true" label = "Sí" /></div>
			<div class="radio-inline"><f:radiobutton path="avisoPolicia" value = "false" label = "No" /></div>
			<span class="help-block">${policiaError }</span></div>
			</div>
		</div>
	<div class="col-sm-8 col-sm-pull-4">
		<c:set var="DNIError"><f:errors path="dni" /></c:set><div class="form-group ${not empty DNIError ? 'has-error' : ''}">
			<label class="col-sm-2 control-label">DNI:</label>
				<div class="autocomplete col-sm-10">
					<f:input class="form-control" path="dni" id="dni" list="dnis" maxlength="9" required="required" placeholder="00000000A"/>
					<datalist id="dnis">
						<c:forEach var="dni" items="${requestScope.clientes}">
							<option value=${dni}>
						</c:forEach>
					</datalist>
				<span class="help-block">${DNIError }</span></div>
			</div>
		<c:set var="usuarioError"><f:errors path="usuario" /></c:set><div class="form-group ${not empty usuarioError ? 'has-error' : ''}">
			<label class="col-sm-2 control-label">Usuario:</label>
			    <div class="col-sm-10">
			<f:input class="form-control" path="usuario" id="usuario" maxlength="20" required="required"/><span class="help-block">${usuarioError}</span></div>
		</div>
		<spr:bind path="password">
		<div class="form-group ${status.error ? 'has-error' : ''}">
			<label class="col-sm-2 control-label">Password:</label>    <div class="col-sm-10">
			<f:password class="form-control" path="password" id="password" maxlength="15" required="required"/><f:errors path="password" cssClass="help-block"/></div>
		</div></spr:bind>
		<c:set var="verifyPasswordError"><f:errors path="verifyPassword" /></c:set><div class="form-group ${not empty verifyPasswordError ? 'has-error' : ''}">
		<%-- <div class="alert alert-warning alert-dismissible" role="alert">
		<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		<f:errors path="verifyPassword" /></div> --%>
			<label class="col-sm-2 control-label">Repita la password:</label>    <div class="col-sm-10">
			<f:password class="form-control" path="verifyPassword" id="verifyPassword" maxlength="100" required="required"/><span class="help-block">${verifyPasswordError }</span></div>
		</div>
		<c:set var="nombreError"><f:errors path="nombre" /></c:set><div class="form-group campo_cliente ${not empty nombreError ? 'has-error' : ''}">
			<label class="col-sm-2 control-label">Nombre:</label>    <div class="col-sm-10">
			<f:input class="form-control" path="nombre" id="nombre" maxlength="100" required="required"/><span class="help-block">${nombreError }</span></div>
		</div>
		<div class="form-group campo_cliente">
			<label class="col-sm-2 control-label">Dirección:</label>    <div class="col-sm-10">
			<f:input class="form-control" path="direccion" id="direccion" maxlength="100"/></div>
		</div>
		<c:set var="emailError"><f:errors path="email" /></c:set><div class="form-group campo_cliente ${not empty emailError ? 'has-error' : ''}">
			<label class="col-sm-2 control-label">Email:</label>    <div class="col-sm-10">
			<f:input type="email" class="form-control" path="email" id="email" maxlength="100" /><span class="help-block">${emailError }</span></div>
		</div>
		<c:set var="cuentaError"><f:errors path="cuenta" /></c:set><div class="form-group campo_cliente ${not empty cuentaError ? 'has-error' : ''}">
			<label class="col-sm-2 control-label">Cuenta:</label>    <div class="col-sm-10">
			<f:input class="form-control" path="cuenta" id="cuenta" maxlength="24" placeholder="ES00 0000 0000 00 0000000000"/><span class="help-block">${cuentaError }</span></div>
		</div>
		<div class="pull-right"><button type="reset" class="btn btn-default">Limpiar datos</button>
		<button type="submit" class="btn btn-primary">Dar de alta</button></div>
		</div>
	</f:form>
</div>
<script>
function autocomplete(inp, arr) {
  /*the autocomplete function takes two arguments,
  the text field element and an array of possible autocompleted values:*/
  var currentFocus;
  /*execute a function when someone writes in the text field:*/
  inp.addEventListener("input", function(e) {
      var a, b, i, val = this.value;
      /*close any already open lists of autocompleted values*/
      closeAllLists();
      if (!val) { return false;}
      currentFocus = -1;
      /*create a DIV element that will contain the items (values):*/
      a = document.createElement("DIV");
      a.setAttribute("id", this.id + "autocomplete-list");
      a.setAttribute("class", "autocomplete-items");
      /*append the DIV element as a child of the autocomplete container:*/
      this.parentNode.appendChild(a);
      /*for each item in the array...*/
      for (i = 0; i < arr.length; i++) {
        /*check if the item starts with the same letters as the text field value:*/
        if (arr[i].substr(0, val.length).toUpperCase() == val.toUpperCase()) {
          /*create a DIV element for each matching element:*/
          b = document.createElement("DIV");
          /*make the matching letters bold:*/
          b.innerHTML = "<strong>" + arr[i].substr(0, val.length) + "</strong>";
          b.innerHTML += arr[i].substr(val.length);
          /*insert a input field that will hold the current array item's value:*/
          b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
          /*execute a function when someone clicks on the item value (DIV element):*/
          b.addEventListener("click", function(e) {
              /*insert the value for the autocomplete text field:*/
              inp.value = this.getElementsByTagName("input")[0].value;
              rellenarCampos(inp.value);
              /*close the list of autocompleted values,
              (or any other open lists of autocompleted values:*/
              closeAllLists();
          });
          a.appendChild(b);
        }
      }
  });
  /*execute a function presses a key on the keyboard:*/
  inp.addEventListener("keydown", function(e) {
      var x = document.getElementById(this.id + "autocomplete-list");
      if (x) x = x.getElementsByTagName("div");
      if (e.keyCode == 40) {
        /*If the arrow DOWN key is pressed,
        increase the currentFocus variable:*/
        currentFocus++;
        /*and and make the current item more visible:*/
        addActive(x);
      } else if (e.keyCode == 38) { //up
        /*If the arrow UP key is pressed,
        decrease the currentFocus variable:*/
        currentFocus--;
        /*and and make the current item more visible:*/
        addActive(x);
      } else if (e.keyCode == 13) {
        /*If the ENTER key is pressed, prevent the form from being submitted,*/
        e.preventDefault();
        if (currentFocus > -1) {
          /*and simulate a click on the "active" item:*/
          if (x) x[currentFocus].click();
        }
      }
  });
  function addActive(x) {
    /*a function to classify an item as "active":*/
    if (!x) return false;
    /*start by removing the "active" class on all items:*/
    removeActive(x);
    if (currentFocus >= x.length) currentFocus = 0;
    if (currentFocus < 0) currentFocus = (x.length - 1);
    /*add class "autocomplete-active":*/
    x[currentFocus].classList.add("autocomplete-active");
  }
  function removeActive(x) {
    /*a function to remove the "active" class from all autocomplete items:*/
    for (var i = 0; i < x.length; i++) {
      x[i].classList.remove("autocomplete-active");
    }
  }
  function closeAllLists(elmnt) {
    /*close all autocomplete lists in the document,
    except the one passed as an argument:*/
    var x = document.getElementsByClassName("autocomplete-items");
    for (var i = 0; i < x.length; i++) {
      if (elmnt != x[i] && elmnt != inp) {
        x[i].parentNode.removeChild(x[i]);
      }
    }
  }
  /*execute a function when someone clicks in the document:*/
  document.addEventListener("click", function (e) {
      closeAllLists(e.target);
  });
}

var dnis = [<c:forEach var="c" items="${requestScope.clientes}" varStatus="pos">'${c}'${not pos.last ? ',' : ''}</c:forEach>];
if (!Modernizr.input.list) {
	  console.log('This browser does not support HTML5 datalist element, so we will load the polyfills');
		autocomplete(document.getElementById("dni"), dnis);
	} else {
		console.log(Modernizr.input.list, document.getElementById("dni").value);
		document.getElementById('dni').addEventListener("input", function(e) {	
			var opciones = document.getElementById('dnis').options;	    
		    if(!Object.prototype.toString.call(e).indexOf('InputEvent') > -1) //rellena los campos si el evento no es una edición directa.
		    	for (var i=0; i<opciones.length; i++)
		    	       if (opciones[i].value === e.target.value) {
		    	          rellenarCampos(e.target.value); //document.getElementById("dni").value);
		    	          break;
		    	       }
		}, false);
	}
function marcarCasilla(inputName, selValue) {
 	console.log(document.querySelector('input[name="'+inputName+'"]').value);
 	console.log(inputName+": "+selValue+" - "+typeof selValue);
	document.querySelector("input[name='"+inputName+"']").value=selValue;
	if(selValue == true) {
		document.getElementById(inputName+"1").checked = true;
		document.getElementById(inputName+"2").checked = false;
	} else if(selValue == false) {
		document.getElementById(inputName+"1").checked = false;
		document.getElementById(inputName+"2").checked = true;
	}
}
function rellenarCampos(valorSeleccionado){
console.log(document.getElementById("dni").value);
if(document.getElementById("dni").value != "") // var sel = JSONObject[valorSeleccionado];
  $.get("${pageContext.request.contextPath}/datos/cliente/"+valorSeleccionado, function(sel, status){
	  console.log(status);
	document.getElementById("nombre").value=sel.nombre;
// 	document.getElementById("password").value=sel.password;
	document.getElementById("usuario").value=sel.usuario;
	document.getElementById("cuenta").value=sel.cuenta;
	document.getElementById("direccion").value=sel.direccion;
	document.getElementById("email").value=sel.email;
	document.getElementById("rol").value=sel.rol;
/* 	document.querySelector("input[name='avisoPolicia']").value=sel.avisoPolicia;
	if(sel.avisoPolicia == true) {
		document.getElementById("avisoPolicia1").checked = true;
		document.getElementById("avisoPolicia2").checked = false;
	} else if(sel.avisoPolicia == false) {
		document.getElementById("avisoPolicia1").checked = false;
		document.getElementById("avisoPolicia2").checked = true;
	} */
	marcarCasilla("avisoPolicia", sel.avisoPolicia);
	marcarCasilla("habilitado", sel.habilitado);
  });
}
</script>
</body>
</html>