package jcf.sample.extprocdemo.fileUpload;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FileUploadController {

	@RequestMapping(value="/fileUpload", method=RequestMethod.GET)
	public String list(Model model) {
		return "fileUpload/fileUpload";
	}
}