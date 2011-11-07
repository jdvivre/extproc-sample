<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags/dates" prefix="d" %>

<h2>업무별 배치 (화면유형 1)</h2>

<h3>Sample Batch <select id="jobSelect">
                <option>TESTJOB</option>
                <option>SampleProgressiveJob</option>
                <option>SamplePallerJob</option>
                <option>FileWrittingJob</option>
              </select> 진행 상황</h3>
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
	var j = jQuery,
		timeoutId = null,
		sampleJobName= $("#jobSelect").val();

	init();

	function init(){
		$("#jobSelect").change(function(e) {
			sampleJobName = $("#jobSelect").val();
			clearTimeout(timeoutId);
			getStatus(sampleJobName);
		});

		j.get('/extproc-sample-web/status/'+sampleJobName, function( data ) {
			var status = null;

			if(data===1){
				status = 'run';
				changeStatus(status);
			}else{
				status = 'stop';
				changeStatus(status);
			}

			disableAllBtn();

			switch (status) {
				case 'stop':
					j('button#run').removeClass('disabled');
					bindBtnEvent('button#run');
					break;
				case 'run':
					j('button#stop').removeClass('disabled');
					bindBtnEvent('button#stop');
					break;
				case 'complete':
					j('button#download').removeClass('disabled');
					bindBtnEvent('button#download');
					break;
				default: break;
			}
			bindStatusPollingEvent();
		});

		getStatus(sampleJobName);
	}

	function bindStatusPollingEvent(target){
		j(window).focus(function(event){
			getStatus(sampleJobName);
	    });

		j(window).blur(function(event){
			clearTimeout(timeoutId);
	    });
	}

	function bindBtnEvent(target){

		unbindAllBtnEvent();

		switch (target) {
			case "button#run":
				j("button#run").bind('click',function(){
					changeStatus('run');
					j.post('/extproc-sample-web/jobs/'+sampleJobName+'/run');
					getStatus(sampleJobName);
				});
				break;
			case "button#stop":
				j("button#stop").bind('click',function(){
					changeStatus('stop');
					j.post('/extproc-sample-web/jobs/'+sampleJobName+'/stop');
				});
				break;
			case "button#download":
				j("button#download").bind('click',function(){
					j.post('/');
				});
				break;
			default:
				break;
		}
	}

	function unbindAllBtnEvent(){
		j("button#download").unbind('click');
		j("button#stop").unbind('click');
		j("button#run").unbind('click');
	}

	function changeStatus(toStats){
		var statusLabel = "";

		j('#jobStatus').attr('stauts',toStats);

		switch (toStats) {
			case 'stop': statusLabel = "중지됨";
				break;
			case 'run': statusLabel = "실행중";
				break;
			case 'complete': statusLabel = "완료됨";
				break;
			default:
				break;
		}

		j('#jobStatus').html(statusLabel);
	}

	function disableAllBtn(){
		j('#btnContainer').find('button').each(function(index,el){
			j(this).addClass('disabled');
		});
	}

	function getStatus( jobName ) {
		console.log(jobName);

	  var status =  j('#jobStatus').attr('stauts'),
	      jobName=jobName;

      j.get('/extproc-sample-web/status/'+jobName, function( data ) {
	    if ( status === 'run') {
			if ( typeof data != 'undefined') {
		        if(data === 0){
		        	changeStatus('stop');
		        	bindBtnEvent('button#run');
		        }
		        clearTimeout( timeoutId );
		        timeoutId = setTimeout( function() {getStatus(jobName);}, 100 );
		     }
			}else{
				timeoutId = setTimeout( function() {getStatus(jobName);}, 5000 );
			}
	   });
	}

})(jQuery);
</script>
