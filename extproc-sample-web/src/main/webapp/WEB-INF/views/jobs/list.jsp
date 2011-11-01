<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib tagdir="/WEB-INF/tags/dates" prefix="d" %>

<h2>등록된 작업</h2>

<c:if test="${not empty jobList}">
<dl class="listings">
<c:forEach items="${jobList}" var="job">
	<s:url value="/jobs/{name}" var="eventUrl">
		<s:param name="name" value="${job.name}" />
	</s:url>
	<dt >
		<a href="${eventUrl}"><c:out value="${job.name}" /></a><br/>
	</dt>
	<dd>
		<c:out value="${job.user}" escapeXml="true" />		
	</dd>	
	<dd>
		<c:out value="${job.workDirectory}" escapeXml="true" />		
	</dd>	
	<dd>
		<c:out value="${job.description}" escapeXml="true" />		
	</dd>	
</c:forEach>
</dl>
</c:if>