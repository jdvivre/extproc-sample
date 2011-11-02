<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags/dates" prefix="d" %>

<h2>업무별 배치 (화면유형 2)</h2>

<h3>업무 2 진행 상황</h3>
<div class="clearfix">
	<label>현재 상태</label>
	<div class="well">
		<div class="span4" style="padding: 14px 19px;">
			<div class="btn info disabled">중지됨</div>
		</div>
		<div class="span12">
			<a id="run" class="btn success">실행</a>
		</div>
	</div>
</div>