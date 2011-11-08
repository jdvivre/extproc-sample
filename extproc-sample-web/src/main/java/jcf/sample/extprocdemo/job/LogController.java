package jcf.sample.extprocdemo.job;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import jcf.extproc.ExternalProcessOperator;
import jcf.extproc.config.ExtProcConstant;
import jcf.extproc.fileaccess.FileAccess;
import jcf.util.tail.JavaTail;
import jcf.util.tail.StreamWriter;

import org.apache.commons.io.FileUtils;
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

	@RequestMapping(value="/jobs/logAll/{jobName}", method=RequestMethod.GET)
	public String log(Model model, @PathVariable String jobName, @RequestParam String sInstanceId) throws IOException {
		String job = jobName; // escape /
		long instanceId = Long.valueOf(sInstanceId);

		File file = fileAccess.getLogFile(operator.getJobInstanceInfo(job, instanceId));


		if(file!=null){
			model.addAttribute("logContents", FileUtils.readFileToString(file));

		}else{
			model.addAttribute("logContents", "");
		}
		model.addAttribute("jobName", jobName);
		model.addAttribute("jobInstance", sInstanceId);

		return "jobs/log";
	}
	
	@RequestMapping(value="/jobs/log/{jobName}", method=RequestMethod.GET)
	public void tail(@PathVariable String jobName, @RequestParam String sInstanceId, HttpServletResponse response) throws IOException, InterruptedException {
		String job = jobName; // escape /
		long instanceId = Long.valueOf(sInstanceId);

		File file = fileAccess.getLogFile(operator.getJobInstanceInfo(job, instanceId));

		if(file!=null){
			
			response.setContentType("text/html;charset=UTF-8");
			final OutputStream os = response.getOutputStream();
			
			new JavaTail(file).tailf(new StreamWriter() {
				
				PrintWriter pw = new PrintWriter(os, true);
				
				public void println(String line) {
					pw.println(line);
				}
				
				public void open() {
					pw.println("<pre>");
				}
				
				public void heartBeat() throws IOException {
					pw.print("");
				}
				
				public void close() {
					pw.println("</pre>");
				}
			}, ExtProcConstant.END_MARKER);
		}

	}
}