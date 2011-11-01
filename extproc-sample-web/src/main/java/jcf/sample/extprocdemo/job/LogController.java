/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jcf.sample.extprocdemo.job;

import java.util.ArrayList;
import java.util.List;

import jcf.extproc.ExternalProcessOperator;
import jcf.extproc.process.JobInstanceFilter;
import jcf.extproc.process.JobInstanceInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * UI Controller for Event actions.
 * @author Keith Donald
 */
@Controller
public class LogController {
	
	@Autowired
	private ExternalProcessOperator operator;
	
	@RequestMapping(value="log", method=RequestMethod.GET)
	public String list(Model model, @RequestParam String job) {
		List<JobInstanceInfo> list = new ArrayList<JobInstanceInfo>();
		
		JobInstanceFilter jobInstanceFilter = null;
		
		for (long instanceId : operator.getJobInstanceIdList(job, jobInstanceFilter)) {
			list.add(operator.getJobInstanceInfo(job, instanceId));
		}
		
		model.addAttribute("instanceList", list);
		return "instances/list";
	}
	
//	@RequestMapping(value="/secure/events", method=RequestMethod.GET)
//	public String execute(@RequestParam String job) {
//		long instanceId = operator.execute(job, null);
//		return "redirect:/events";
//	}	
}