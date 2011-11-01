<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib tagdir="/WEB-INF/tags/dates" prefix="d" %>

<h2>등록된 작업</h2>

<c:if test="${not empty eventList}">
<dl class="listings">
<c:forEach items="${eventList}" var="event">
	<s:url value="/events/history?job={name}" var="eventUrl">
		<s:param name="name" value="${event.name}" />
	</s:url>
	<dt>
		<a href="${eventUrl}"><c:out value="${event.name}" /></a><br/>
	</dt>
	<dd>
		<c:out value="${event.user}" escapeXml="true" />		
	</dd>	
	<dd>
		<c:out value="${event.workDirectory}" escapeXml="true" />		
	</dd>	
	<dd>
		<c:out value="${event.description}" escapeXml="true" />		
	</dd>	
</c:forEach>
</dl>
</c:if>