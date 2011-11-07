<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags/dates" prefix="d" %>
<style>
	.ui-progressbar-value { background-image: url(/extproc-sample-web/resources/images/progressbar/green_progress_bar.png); }
</style>

<h2>업무별 배치 (화면유형 2)</h2>

<h3>병렬처리 진행 상황 예제</h3>
<div class="row">
	<div class="span4">
		<h4>현재 상태</h4>
		<p>작업의 현재 상태를 알 나타낸다. 상태는 중지됨, 실행중, 2가지이다.</p>
		<div id="jobStatus" class="btn large disabled" stauts="stop" style="margin-left:80px;">중지됨</div>
	</div>
	<div class="span12">
		<div id="btnContainer" class="well" style="padding: 14px 19px; margin-top:30px;">
			<button id="run" class="btn success disabled">실행</button>
			<button id="stop" class="btn danger disabled">중지</button>
		</div>
	</div>
</div>
<div class="row"  style="margin-left: 5px">
	<h4>진행 상태</h4>
	<p>현재 진행중인 Job의 Progress를 나타낸다. Sample의 Job은 %이지만 어떠한 진쳑율도 표현이 가능하다.</p>
	<div id="progressbar" ><p style="float: left; padding-left: 45%; padding-top: 2px;">0%</p></div>
</div>
<script type="text/javascript">
(function(jQuery){

	var j = jQuery,
		timeoutId = null,
		progressTimeOut = null,
		sampleJobName= "SampleProgressiveJob",
		progressBarWrapper = j("#progressbar");
		progressBarInnerText = j("#progressbar>p");

	init();

	function init(){
		setProgressValue(0);

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

	function setProgressValue(value){
		progressBarWrapper.progressbar({
			value: value
		});
		progressBarInnerText.html(value+"%");
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
					getProgress(sampleJobName);
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
				bindBtnEvent('button#run');
				j('button#run').removeClass('disabled');
				j('button#stop').addClass('disabled');
				setProgressBarImg("red");
				break;
			case 'run': statusLabel = "실행중";
				bindBtnEvent('button#stop');
				j('button#run').addClass('disabled');
				j('button#stop').removeClass('disabled');
				setProgressBarImg("green");
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

	function getProgress( jobName ) {
      j.get('/extproc-sample-web/jobs/'+jobName+'/progress', function( data ) {
   		  setProgressValue(data);
    	  if(data === 100){
    		  clearTimeout( progressTimeOut );
    	  }else{
    		  progressTimeOut = setTimeout( function() {getProgress(jobName);}, 1000 );
    	  }
	   });
	}

	function setProgressBarImg(color){
		progressBarWrapper.find(".ui-progressbar-value").css("background-image","url(/extproc-sample-web/resources/images/progressbar/"+color+"_progress_bar.png)");
	}

})(jQuery);
</script>