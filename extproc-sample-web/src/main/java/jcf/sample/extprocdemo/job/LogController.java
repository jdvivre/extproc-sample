package jcf.sample.extprocdemo.job;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import jcf.extproc.ExternalProcessOperator;
import jcf.extproc.fileaccess.FileAccess;
import jcf.extproc.fileaccess.FileInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LogController {

	@Autowired
	private ExternalProcessOperator operator;

	@Autowired
	private FileAccess fileAccess;

	@RequestMapping(value="/jobs/log/{jobName}", method=RequestMethod.GET)
	public String log(Model model, @PathVariable String jobName, @RequestParam String sInstanceId) {
		String job = jobName; // escape /
		long instanceId = Long.valueOf(sInstanceId);
		Iterable<FileInfo> list = fileAccess.list(operator.getJobInstanceInfo(job, instanceId));

		File file = null;

		for (FileInfo fileInfo : list) {
			System.out.println(fileInfo);
			if(fileInfo.getName().equals("stdout.log")){
				file = fileAccess.get(operator.getJobInstanceInfo(job, instanceId), "stdout.log");
			}
		}

		if(file!=null){
			StringBuilder sb = new StringBuilder();
			try {
				InputStreamReader reader = new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8"));

				BufferedReader bufferedReader = new BufferedReader(reader);

				String line;
				while ((line = bufferedReader.readLine()) != null) {
					sb.append(line);
				}
			} catch (Exception e) {
				throw new UnsupportedOperationException("Error occur while handling logging file",e);
			}

			String ret = sb.toString();

			model.addAttribute("logContents", ret);
		}else{
			model.addAttribute("logContents", "");
		}
		model.addAttribute("jobName", jobName);
		model.addAttribute("jobInstance", sInstanceId);

		return "jobs/log";
	}
}