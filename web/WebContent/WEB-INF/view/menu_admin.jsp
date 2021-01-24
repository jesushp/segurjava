<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
		<li class="${param.pagina == 'menu'?'active': ''}"><a href="${param.pagina == 'menu'?'#': 'admin-menu.jsp'}"><fmt:message key="menu.home"/></a></li>
		<li class="${param.pagina == 'alta_cliente'?'active': ''}"><a href="${param.pagina == 'alta_cliente'?'#': 'adminAltaCliente.to'}"><fmt:message key="menu.alta_cliente"/></a></li>
		<li class="${param.pagina == 'informe_temporal'?'active': ''}"><a href="${param.pagina == 'informe_temporal'?'#': 'admin-informeTemporal.jsp'}"><fmt:message key="menu.informe_temporal"/></a></li>
		<li class="${param.pagina == 'informe_cliente'?'active': ''}"><a href="${param.pagina == 'informe_cliente'?'#': 'adminInformeFullCliente.to'}"><fmt:message key="menu.informe_cliente"/></a></li>
    </ul>
     <ul class="nav navbar-nav navbar-right">
      <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span> <%-- sessionScope.persona.usuario --%><sec:authentication property="name" /> <b class="caret"></b></a>
      <ul class="dropdown-menu">
          <li><a href="adminEncript.do"><fmt:message key="menu.encript_bd"/></a></li>
          <li><a href="#" data-toggle="modal" data-target="#pwdModal"><fmt:message key="menu.encript_bd-pwd"/></a></li>
        </ul></li>
      <li><a href="${pageContext.request.contextPath}/j_spring_security_logout"><span class="glyphicon glyphicon-log-in"></span> <fmt:message key="menu.log_out"/></a></li>
    </ul>
    </div>
  </div>
</nav>
</div>
<!-- Modal -->
<div id="pwdModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title"><fmt:message key="menu.encript_bd-pwd"/></h4>
			</div>
			<form action="adminPwd.do"><div class="modal-body">
				<p><fmt:message key="menu.encript_bd-preconfirm"/></p>
				<label><fmt:message key="menu.encript_bd-campo_pwd"/>: <input type="text" name="pwd"></label>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<fmt:message key="menu.opt_out-cancel"/>
				</button>
				<button type="submit" class="btn btn-warning" data-dismiss="modal" >
				<span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span> <fmt:message key="menu.encript_bd-confirm"/></button>
			</div></form>
		</div>
	</div>
</div>
