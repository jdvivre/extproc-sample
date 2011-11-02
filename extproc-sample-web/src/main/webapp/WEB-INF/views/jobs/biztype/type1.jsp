<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags/dates" prefix="d" %>

<h2>업무별 배치 (화면유형 1)</h2>

<h3>업무 2 진행 상황</h3>
<div class="row">
	<div class="span4">
		<h4>현재 상태</h4>
		<p>작업의 현재 상태를 알 나타낸다. 상태는 중지됨, 실행중, 완료로 3가지로 나타난다.</p>
		<div id="jobStatus" class="btn info large disabled" stauts="stop" style="margin-left:80px;">중지됨</div>
	</div>
	<div class="span12">
		<div id="btnContainer" class="well" style="padding: 14px 19px; margin-top:30px;">
			<button id="run" class="btn success disabled">실행</button>
			<button id="stop" class="btn danger disabled">중지</button>
			<button id="download" class="btn prime disabled">파일 다운로드</button>
		</div>
	</div>
</div>
<script type="text/javascript">
(function(jQuery){
	var j = jQuery;

	init();
	bind();

	function init(){
		var status = j('#jobStatus').attr('stauts');

		disableAllBtn();

		switch (status) {
		case 'stop':
			j('button#run').removeClass('disabled');
			break;
		case 'run':
			j('button#stop').removeClass('disabled');
			break;
		case 'complete':
			j('button#download').removeClass('disabled');
			break;
		default:
			break;
		}
	}

	function changeStatus(toStats){
		j('#jobStatus').attr('stauts',toStats);
	}

	function disableAllBtn(){
		j('#btnContainer').find('button').each(function(index,el){
			j(this).addClass('disabled');
		});
	}

	function bind(){
		j("button#run").bind('click',function(){
			changeStatus('run');
			j.post('/extproc-sample-web/jobs/run?jobName=TESTJOB');

		});

		j("button#stop").bind('click',function(){
			changeStatus('stop');
			j.post('/');
		});

		j("button#download").bind('click',function(){
			j.post('/');
		});
	}
})(jQuery);
</script>

