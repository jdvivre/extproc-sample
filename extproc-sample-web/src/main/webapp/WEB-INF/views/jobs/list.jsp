<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags/dates" prefix="d" %>

<h2>등록된 작업</h2>

<c:if test="${not empty jobList}">
<table>
 <thead>
   <tr>
     <th>#</th>
     <th>Job Name</th>
     <th>User Name</th>
     <th>Work Directory</th>
     <th>Description</th>
   </tr>
 </thead>
 <tbody>
 <c:forEach items="${jobList}" var="job">
 	<s:url value="/jobs/{name}" var="eventUrl">
		<s:param name="name" value="${job.name}" />
	</s:url>
   <tr>
     <td></td>
     <td><a href="${eventUrl}"><c:out value="${job.name}" /></a></td>
     <td><c:out value="${job.user}" escapeXml="true" /></td>
     <td><c:out value="${job.workDirectory}" escapeXml="true" /></td>
     <td><c:out value="${job.description}" escapeXml="true" /></td>
   </tr>
 </c:forEach>
 </tbody>
</table>
</c:if>