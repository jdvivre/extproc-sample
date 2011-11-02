<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags/dates" prefix="d" %>

<h2>작업 이력 : <c:out value="${instanceList[0].jobName}" escapeXml="true" /></h2>

<table id="jobInstances" class="zebra-striped">
 <thead>
   <tr>
     <th>jobInstanceName</th>
     <th>jobInstance</th>
     <th>result</th>
     <th>triggerDate</th>
     <th>user</th>
     <th>workDirectory</th>
     <th>duration</th>
     <th>exitValue</th>
     <th>description</th>
   </tr>
 </thead>
 <tbody>
	<c:if test="${not empty instanceList}">
	 <c:forEach items="${instanceList}" var="instance">
	   <tr>
			<td class="jobInstanceName"><c:out value="${instance.jobInstanceName}" escapeXml="true" /></td>
			<td class="jobInstance"><c:out value="${instance.jobInstance}" escapeXml="true" /></td>
			<td class="result"><c:out value="${instance.result}" escapeXml="true" /></td>
			<td class="triggerDate"><c:out value="${instance.triggerDate}" escapeXml="true" /></td>
			<td class="user"><c:out value="${instance.user}" escapeXml="true" /></td>
			<td class="workDirectory"><c:out value="${instance.workDirectory}" escapeXml="true" /></td>
			<td class="duration"><c:out value="${instance.duration}" escapeXml="true" /></td>
			<td class="exitValue"><c:out value="${instance.exitValue}" escapeXml="true" /></td>
			<td class="description"><c:out value="${instance.description}" escapeXml="true" /></td>
	   </tr>
	 </c:forEach>
	</c:if>
 </tbody>
</table>
<script type="text/javascript">
(function(jQuery){
	var j = jQuery,
		tableEl = j("#jobInstances");

	tableEl.find("tr").bind("click",function(e){
		var td = j(this).children(),
			valueObj = getValueObj(td),
			contextRoot = "<%=request.getContextPath()%>",
			jabName = "<c:out value="${instanceList[0].jobName}" escapeXml="true" />";

		if(valueObj.jobInstance != ""){
			var redirectURL = contextRoot+"/jobs/log/"+jabName+"?sInstanceId="+valueObj.jobInstance;
			window.location= redirectURL;
		}
		console.log(valueObj);
	});

	function getValueObj(tdEls){
		var returnObj = {};
		j.each(tdEls,function(index,value){
			returnObj[j(value).attr("class")] = j(value).html();
		});
		return returnObj;
	}
})(jQuery);
</script>