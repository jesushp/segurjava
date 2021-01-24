	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
<!--
console.log("URI: ${pageContext.request.contextPath}/${param.pagina }-${n_paginas }"); /${pageContext.request.requestURI}
//-->
</script>
<c:set var="pagActual" value="${param.pagina}"/>
Página nº  <c:if test="${pagActual-3 >= 2}"><a href="${pageContext.request.contextPath}/adminInformeTemporal/1?fecIni=${param.fecIni}&fecFin=${param.fecFin}"> 1 </a> ... </c:if>
		<c:choose><c:when test="${pagActual < 3 && n_paginas >= 5 }"><c:forEach begin="1" end="5" varStatus="pos" var="pag"><c:if test="${pagActual != pag}"><a href="${pageContext.request.contextPath}/adminInformeTemporal/${pag }?fecIni=${param.fecIni}&fecFin=${param.fecFin}&n_filas=${param.n_filas}"></c:if>
					${pag }<c:if test="${pagActual != pag}"></a></c:if>
					${not pos.last ? '|' : ''} </c:forEach></c:when>
					<c:when test="${pagActual > n_paginas - 2 && n_paginas >= 5 }"><c:forEach begin="${n_paginas-4 }" end="${n_paginas }" varStatus="pos" var="pag"><c:if test="${pagActual != pag}"><a href="${pageContext.request.contextPath}/adminInformeTemporal/${pag }?fecIni=${param.fecIni}&fecFin=${param.fecFin}&n_filas=${param.n_filas}"></c:if>
					${pag }<c:if test="${pagActual != pag}"></a></c:if>
					${not pos.last ? '|' : ''} </c:forEach></c:when>
		<c:otherwise><c:forEach begin="${pagActual < 5 ? 1 : pagActual-2 }" end="${n_paginas > pagActual+2 ? pagActual+2 : n_paginas }" varStatus="pos" var="pag">
					<c:if test="${pagActual != pag}"><a href="${pageContext.request.contextPath}/adminInformeTemporal/${pag }?fecIni=${param.fecIni}&fecFin=${param.fecFin}&n_filas=${param.n_filas}"></c:if>
					${pag }<c:if test="${pagActual != pag}"></a></c:if>
					${not pos.last ? '|' : ''} </c:forEach></c:otherwise></c:choose>
			<c:if test="${pagActual+2 < n_paginas}"> ${pagActual+2 == n_paginas-1? '|': '...'} <a href="${pageContext.request.contextPath}/adminInformeTemporal/${n_paginas}?fecIni=${param.fecIni}&fecFin=${param.fecFin}&n_filas=${param.n_filas}"> ${n_paginas } </a></c:if>