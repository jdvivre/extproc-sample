package jcf.sample.extprocdemo.config;

import jcf.extproc.ExternalProcessOperator;
import jcf.extproc.spring.ExternalProcessOperatorFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@ImportResource("classpath:jcf/sample/extprocdemo/config/component.xml")
public class Component {

	@Bean
	public ExternalProcessOperator getOperator() throws Exception {
		ExternalProcessOperatorFactory factory = new ExternalProcessOperatorFactory();
		factory.setBaseDirectory("D:/tmp/jobs");
//		factory.setBaseDirectory("c:/tmp/batch_repository");
		factory.setCharset("MS949");
		factory.setTaskExecutor(new SimpleAsyncTaskExecutor());
		
		return factory.getObject();
	}
}
