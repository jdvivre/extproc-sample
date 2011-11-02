<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html>
<html>
<head>
	<title><tiles:insertAttribute name="title" defaultValue="JCF SAMPLE" /></title>
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
    <meta name="description" content="JCF SAMPLE" />
    <meta name="keywords" content="JCF SAMPLE" />

	<link rel="stylesheet" href="http://twitter.github.com/bootstrap/1.3.0/bootstrap.min.css">

    <style type="text/css">
      body {
        padding-top: 60px;
      }
    </style>
	<c:forEach var="style" items="${styles}">
	<link rel="stylesheet" href="<c:url value="/resources/${style}" />" type="text/css" media="all" />
	</c:forEach>

	<tiles:useAttribute id="styles" name="styles" classname="java.util.List" ignore="true" />

	<c:forEach var="meta" items="${metadata}">
	<meta name="${meta.key}" content="${meta.value}"/>
	</c:forEach>

	<script type="text/javascript" src="<c:url value="/resources/jquery/1.6/jquery.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/jquery-cookie/1.0/jquery-cookie.js" />"></script>
</head>
<body>
  	<div class="topbar">
		<tiles:insertAttribute name="header" />
	</div>
	<div class="container-fluid">
	   <div class="sidebar">
		<div class="well">
			<h5>온라인 배치</h5>
			<ul>
				<li><a href="/extproc-sample-web/jobs">온라인 배치 통합 뷰</a></li>
				<li><a href="/extproc-sample-web/jobs/biztype/type1">업무별 배치 (유형 1)</a></li>
				<li><a href="/extproc-sample-web/jobs/biztype/type2">업무별 배치 (유형 2)</a></li>
			</ul>
			<h5>공통 기능</h5>
			<ul>
				<li><a href="/extproc-sample-web/fileUpload">파일 업로드</a></li>
			</ul>
		</div>
		</div>
		<div class="content">
			<tiles:insertAttribute name="content" />
		    <footer>
				<tiles:insertAttribute name="footer" />
		    </footer>
		</div>
	</div>
	<tiles:useAttribute id="scripts" name="scripts" classname="java.util.List" ignore="true" />
	<c:forEach var="script" items="${scripts}">
		<script type="text/javascript" src="<c:url value="/resources/${script}" />"></script>
	</c:forEach>
	<script type="text/javascript">
		$.cookie('Greenhouse.timeZoneOffset', new Date().getTimezoneOffset() * 60000);
	</script>
</body>
</html>