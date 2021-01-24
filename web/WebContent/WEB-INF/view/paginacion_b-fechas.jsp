<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
<!--
console.log("URI: ${pageContext.request.contextPath}/${param.pagina }-${n_paginas }"); /${pageContext.request.requestURI}
//-->
</script>
<c:set var="pagActual" value="${param.pagina}"/>
<ul class="pagination"> 
<%-- Enlaza el primer item cuando no se encuentra en el rango mostrado. --%>
<c:if test="${pagActual >= 5}"><li><a href="<c:url value="/adminInformeTemporal/1">
					<c:param name="fecIni" value="${param.fecIni}" />
					<c:param name="fecFin" value="${param.fecFin}" />
					<c:param name="n_filas" value="${param.n_filas}" /></c:url>">"> 1 </a></li><li><span> ... </span></li></c:if>
		<c:choose><%-- Muestra siempre 5 items en el rango inicial. --%><c:when test="${pagActual < 3 && n_paginas >= 5 }"><c:forEach begin="1" end="5" varStatus="pos" var="pag">
				<li class="${pagActual != pag? '': 'active'}"><a href="<c:url value="/adminInformeTemporal/${pag}">
					<c:param name="fecIni" value="${param.fecIni}" />
					<c:param name="fecFin" value="${param.fecFin}" />
					<c:param name="n_filas" value="${param.n_filas}" /></c:url>"> ${pag } </a></li>
					<%-- <li>${not pos.last ? '|' : ''} </li> --%></c:forEach></c:when>
		<%-- Muestra siempre 5 items en el rango final. --%><c:when test="${pagActual > n_paginas - 2 && n_paginas >= 5 }"><c:forEach begin="${n_paginas-4 }" end="${n_paginas }" varStatus="pos" var="pag">
				<li class="${pagActual != pag? '': 'active'}"><a href="<c:url value="/adminInformeTemporal/${pag}">
					<c:param name="fecIni" value="${param.fecIni}" />
					<c:param name="fecFin" value="${param.fecFin}" />
					<c:param name="n_filas" value="${param.n_filas}" /></c:url>"> ${pag } </a></li>
					<%-- <li>${not pos.last ? '|' : ''} </li> --%></c:forEach></c:when>
		<%-- Enlaza el rango contextual mostrado. --%><c:otherwise><c:forEach begin="${pagActual < 5 ? 1 : pagActual-2 }" end="${n_paginas > pagActual+2 ? pagActual+2 : n_paginas }" varStatus="pos" var="pag">
			<%-- Items enlazados en el contexto. --%><li class="${pagActual != pag? '': 'active'}"><a href="<c:url value="/adminInformeTemporal/${pag}">
					<c:param name="fecIni" value="${param.fecIni}" />
					<c:param name="fecFin" value="${param.fecFin}" />
					<c:param name="n_filas" value="${param.n_filas}" /></c:url>"> ${pag } </a><%-- Item activo. --%></li>
					<%-- Muestra un separador excepto en el tope. --%><%-- <li>${not pos.last ? '|' : ''} </li> --%></c:forEach></c:otherwise></c:choose>
		<%-- Muestra un gap. --%><c:if test="${pagActual+2 < n_paginas && pagActual+2 != n_paginas-1}"><li><span class="pagingDots"> ... </span></li></c:if>
		<%-- Enlaza el último item cuando no se encuentra en el rango mostrado. --%>
		<c:if test="${pagActual+2 < n_paginas}"><li> <a href="<c:url value="/adminInformeTemporal/${n_paginas}">
			<c:param name="fecIni" value="${param.fecIni}" />
			<c:param name="fecFin" value="${param.fecFin}" />
			<c:param name="n_filas" value="${param.n_filas}" /></c:url>"> ${n_paginas } </a></li></c:if>
	</ul>