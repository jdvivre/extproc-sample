package jcf.sample.extprocdemo.job;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import jcf.extproc.ExternalProcessOperator;
import jcf.extproc.process.ExternalProcess;
import jcf.extproc.process.JobInstanceFilter;
import jcf.extproc.process.JobInstanceInfo;
import jcf.extproc.process.JobStatus;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class JobController {

	@Autowired
	private ExternalProcessOperator operator;

	
	public static class JobStatusInfo {
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getUser() {
			return user;
		}
		public void setUser(String user) {
			this.user = user;
		}
		public String getWorkDirectory() {
			return workDirectory;
		}
		public void setWorkDirectory(String workDirectory) {
			this.workDirectory = workDirectory;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public boolean isRunning() {
			return isRunning;
		}
		public void setRunning(boolean isRunning) {
			this.isRunning = isRunning;
		}
		public JobStatus getResult() {
			return result;
		}
		public void setResult(JobStatus result) {
			this.result = result;
		}
		String name;
		String user;
		String workDirectory;
		String description;
		boolean isRunning;
		JobStatus result;
		
		
	}
	
	@RequestMapping(value="/jobs", method=RequestMethod.GET)
	public String list(Model model) throws IllegalAccessException, InvocationTargetException {
		List<JobStatusInfo> list = new ArrayList<JobStatusInfo>();
		
		for (String jobName : operator.getJobNames()) {
			JobStatusInfo jobStatusInfo = new JobStatusInfo();
			
			BeanUtils.copyProperties(jobStatusInfo, operator.getJob(jobName));
			
			JobInstanceInfo lastInstanceInfo = operator.getLastInstanceInfo(jobName);
			if (lastInstanceInfo != null) {
				jobStatusInfo.setResult(lastInstanceInfo.getResult());
				
			} else {
				jobStatusInfo.setResult(JobStatus.STOPPED);
			}
			jobStatusInfo.setRunning(operator.isRunning(jobName));
			
			list.add(jobStatusInfo);
		}
		

		model.addAttribute("jobList", list);
		return "jobs/list";
	}

	@RequestMapping(value="/jobs/{jobName}", method=RequestMethod.GET)
	public String list(Model model, @PathVariable String jobName) {

		String job = jobName; // escape /

		List<JobInstanceInfo> list = new ArrayList<JobInstanceInfo>();

		JobInstanceFilter jobInstanceFilter = null;

		int count = 0;
		List<Long> jobInstanceIdList = operator.getJobInstanceIdList(job, jobInstanceFilter);
		for (long instanceId : jobInstanceIdList) {
			if (count++ > 100) break;
			list.add(operator.getJobInstanceInfo(job, instanceId));
		}

		model.addAttribute("instanceList", list);
		return "jobs/instances/list";
	}

}