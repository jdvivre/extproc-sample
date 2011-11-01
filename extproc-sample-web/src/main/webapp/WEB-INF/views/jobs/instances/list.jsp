<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib tagdir="/WEB-INF/tags/dates" prefix="d" %>

<h2>작업 이력 : <c:out value="${instance.jobName}" escapeXml="true" /></h2>

<c:if test="${not empty instanceList}">
<dl class="listings">
<c:forEach items="${instanceList}" var="instance">
	<dd>
		<c:out value="${instance.jobInstanceName}" escapeXml="true" />		
		<c:out value="${instance.jobInstance}" escapeXml="true" />		
		<c:out value="${instance.result}" escapeXml="true" />		
		<c:out value="${instance.triggerDate}" escapeXml="true" />		
		<c:out value="${instance.user}" escapeXml="true" />		
		<c:out value="${instance.workDirectory}" escapeXml="true" />		
		<c:out value="${instance.duration}" escapeXml="true" />		
		<c:out value="${instance.exitValue}" escapeXml="true" />		
		<c:out value="${instance.description}" escapeXml="true" />		
	</dd>	
</c:forEach>
</dl>
</c:if>
