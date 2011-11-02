package jcf.sample.extprocdemo.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jcf.extproc.ExternalProcessOperator;
import jcf.extproc.process.ExternalProcess;
import jcf.extproc.process.JobInstanceInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BizJobController {

	@Autowired
	private ExternalProcessOperator operator;

//	@RequestMapping(value="/events", method=RequestMethod.GET, headers="application/json")
//	public @ResponseBody List<ExternalProcess> upcomingEvents(@RequestParam(value="after", required=false) @DateTimeFormat(iso=ISO.DATE_TIME) Long afterMillis) {
//		List<ExternalProcess> list = new ArrayList<ExternalProcess>();
//		for (String jobName : operator.getJobNames()) {
//			list.add(operator.getJob(jobName));
//		}
//		return list;
//	}

	@RequestMapping(value="/jobs/run", method=RequestMethod.POST)
	public @ResponseBody String runBathch(@RequestParam String jobName) {
		Map<String, String> adhocEnvironment = null;

		JobInstanceInfo jobInstanceInfo = new JobInstanceInfo();

		operator.execute(jobName, adhocEnvironment,jobInstanceInfo);

//		List<ExternalProcess> list = new ArrayList<ExternalProcess>();
//		for (String jobName : operator.getJobNames()) {
//			list.add(operator.getJob(jobName));
//		}
//
//		model.addAttribute("jobList", list);
		return "test";
	}

	@RequestMapping(value="/jobs/biztype/type2", method=RequestMethod.GET)
	public String bizBatch2(Model model) {
		List<ExternalProcess> list = new ArrayList<ExternalProcess>();
		for (String jobName : operator.getJobNames()) {
			list.add(operator.getJob(jobName));
		}

		model.addAttribute("jobList", list);
		return "jobs/biztype/type2";
	}

	@RequestMapping(value="/jobs/biztype/type1", method=RequestMethod.GET)
	public String bizBatch1(Model model) {
		List<ExternalProcess> list = new ArrayList<ExternalProcess>();
		for (String jobName : operator.getJobNames()) {
			list.add(operator.getJob(jobName));
		}

		model.addAttribute("jobList", list);
		return "jobs/biztype/type1";
	}
}