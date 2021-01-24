<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources.application" /> 
<script type="text/javascript">
<!--
console.log("URI: ${pageContext.request.contextPath}/${param.pagina }"); /${pageContext.request.requestURI}
//-->
</script>
<div class="container-fluid">
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
			    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#sjNavbar">
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span> 
			      </button>
				<a class="navbar-brand" href="#"><fmt:message key="menu.app"/></a>
			</div>
		    <div class="collapse navbar-collapse" id="sjNavbar">
			<ul class="nav navbar-nav">
				<li class="${empty sensores && param.pagina == 'panel' ?'active': ''}"><a href="${empty sensores && param.pagina == 'panel' ?'#': 'cliente-menu.jsp'}"><fmt:message key="menu.home"/></a></li>
				<li class="${!empty sensores && param.pagina == 'panel'?'active': ''}"><a href="${!empty sensores && param.pagina == 'panel' ?'#': 'clienteDashboard.to'}"><fmt:message key="menu.panel"/></a></li>
				<li class="${param.pagina == 'sensores'?'active': ''}"><a href="${param.pagina == 'sensores'?'#': 'clienteGestionarSensores.to'}"><fmt:message key="menu.sensores"/></a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span> ${sessionScope.persona.nombre} <b class="caret"></b></a>
				<ul class="dropdown-menu">
					<li><a href="#" data-toggle="modal" data-target="#bajaModal"><fmt:message key="menu.opt_out"/></a></li>
				</ul></li>
				<li><a href="${pageContext.request.contextPath}/j_spring_security_logout"><span class="glyphicon glyphicon-log-in"></span> <fmt:message key="menu.log_out"/></a></li>
			</ul>
			</div>
		</div>
	</nav>
</div>
<!-- Modal -->
<div id="bajaModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title"><fmt:message key="menu.opt_out-start"/></h4>
			</div>
			<div class="modal-body">
				<p><fmt:message key="menu.opt_out-preconfirm"/></p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<fmt:message key="menu.opt_out-cancel"/>
				</button>
				<button type="button" class="btn btn-warning" data-dismiss="modal" onclick="window.location.href = 'clienteBaja.do?dni=${sessionScope.persona.dni}';">
					<span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span> <fmt:message key="menu.opt_out-confirm" />
				</button>
			</div>
		</div>
	</div>
</div>
