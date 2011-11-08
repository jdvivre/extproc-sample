<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags/dates" prefix="d" %>

<h2><c:out value="${jobName}" escapeXml="true" /> :: <c:out value="${jobInstance}" escapeXml="true" /> - 로그</h2>
<div class="hero-unit">
<pre>
<c:out value="${logContents}" escapeXml="true" />
</pre>
</div>