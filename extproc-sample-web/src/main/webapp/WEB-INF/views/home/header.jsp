<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h1><a title="첫화면" href="<c:url value="/" />"><img src="<c:url value="/resources/logo-header.png" />" alt="온라인배치" /></a></h1>
<div id="nav">
	<ul>
		<li><a href="<c:url value="/signin" />">새 작업 등록</a></li>
	</ul>
</div>