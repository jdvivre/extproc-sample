package jcf.sample.extprocdemo.job;

import java.util.ArrayList;
import java.util.List;

import jcf.extproc.ExternalProcessOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JobStatusController {

	@Autowired
	private ExternalProcessOperator operator;

	@RequestMapping(value="/status", method=RequestMethod.GET)
	public @ResponseBody List<Integer> getStatusList(Model model) {
		List<Integer> list = new ArrayList<Integer>();
		for (String jobName : operator.getJobNames()) {
			list.add((operator.isRunning(jobName)) ? 1 : 0);
		}
		return list;
	}

	@RequestMapping(value="/status/{jobName}", method=RequestMethod.GET)
	public @ResponseBody int getStatus(Model model, @PathVariable String jobName) {
		boolean running = operator.isRunning(jobName);
		return (running) ? 1 : 0;
	}

}