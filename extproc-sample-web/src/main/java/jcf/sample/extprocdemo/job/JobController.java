package jcf.sample.extprocdemo.job;

import java.util.ArrayList;
import java.util.List;

import jcf.extproc.ExternalProcessOperator;
import jcf.extproc.process.ExternalProcess;
import jcf.extproc.process.JobInstanceFilter;
import jcf.extproc.process.JobInstanceInfo;

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

	@RequestMapping(value="/jobs", method=RequestMethod.GET)
	public String list(Model model) {
		List<ExternalProcess> list = new ArrayList<ExternalProcess>();
		for (String jobName : operator.getJobNames()) {
			list.add(operator.getJob(jobName));
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