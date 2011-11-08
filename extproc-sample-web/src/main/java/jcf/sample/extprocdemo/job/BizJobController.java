package jcf.sample.extprocdemo.job;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jcf.extproc.ExternalProcessOperator;
import jcf.extproc.fileaccess.FileAccess;
import jcf.extproc.process.ExternalProcess;
import jcf.extproc.process.JobInstanceInfo;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BizJobController {

	@Autowired
	private ExternalProcessOperator operator;

	@Autowired
	private FileAccess fileAccess;

	@RequestMapping(value="/jobs/{jobName}/run", method=RequestMethod.POST)
	public @ResponseBody int runBathch(@PathVariable String jobName) {
		Map<String, String> adhocEnvironment = null;

		JobInstanceInfo jobInstanceInfo = new JobInstanceInfo();

		operator.execute(jobName, adhocEnvironment,jobInstanceInfo);

		boolean running = operator.isRunning(jobName);
		return (running) ? 1 : 0;
	}

	@RequestMapping(value="/jobs/{jobName}/stop", method=RequestMethod.POST)
	public @ResponseBody int stopBatch(@PathVariable String jobName) {
		operator.destroy(jobName);

		boolean running = operator.isRunning(jobName);
		return (running) ? 1 : 0;
	}

	@RequestMapping(value="/jobs/type1/{jobName}/status", method=RequestMethod.GET)
	public @ResponseBody int getStatus(Model model, @PathVariable String jobName) {
		boolean running = operator.isRunning(jobName);
		List<Long> jobInstanceIdList = operator.getJobInstanceIdList(jobName, null);
		boolean empty = jobInstanceIdList.isEmpty();
		byte a = (byte) ((running) ? 1 : 0);
		byte b = (byte) ((empty) ? 1 : 0);
		a = (byte)((a<<1)|b);
		return a;
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
///extproc-sample-web/jobs/'+sampleJobName+'/download
	@RequestMapping(value="/jobs/{jobName}/download/*", method=RequestMethod.GET)
	public void downloadFile(Model model, HttpServletResponse response, @PathVariable String jobName) throws Exception {
		JobInstanceInfo jobInstanceInfo = operator.getLastInstanceInfo(jobName);

		response.setContentType("application/octet;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;");

		File file = fileAccess.get(jobInstanceInfo, "sample-file");

		InputStream inputStream = new FileInputStream(file);
		try {
			IOUtils.copyLarge(inputStream, response.getOutputStream());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
		}
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