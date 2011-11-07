package jcf.sample.extprocdemo.job;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jcf.cmd.progress.builder.ProgressReaderBuilder;
import jcf.cmd.progress.reader.ProgressReader;
import jcf.extproc.ExternalProcessOperator;
import jcf.extproc.fileaccess.FileAccess;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProgressController {

	@Autowired
	private ExternalProcessOperator operator;

	@Autowired
	private FileAccess fileAccess;

	@RequestMapping(value="/jobs/{jobName}/progress", method=RequestMethod.GET)
	public @ResponseBody int log(Model model, @PathVariable String jobName) throws IOException {

		String job = jobName;

		List<Long> jobInstanceIdList = operator.getJobInstanceIdList(jobName, null);
		Long lastJobId = jobInstanceIdList.get(jobInstanceIdList.size()-1);

		try{
			File file = fileAccess.get(operator.getJobInstanceInfo(job, lastJobId), "progress");
			byte[] readFileToByteArray = FileUtils.readFileToByteArray(file);
			ProgressReaderBuilder progressReaderBuilder = new ProgressReaderBuilder();
			progressReaderBuilder.addInt("prgoress");
			ProgressReader reader = progressReaderBuilder.build();
			reader.reset(readFileToByteArray);
			int prgoress = reader.getInt("prgoress");

			return prgoress;
		}catch(Exception exception){
			return 0;
		}
	}
}