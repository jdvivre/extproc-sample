package jcf.sample.extproccmd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jcf.cmd.runner.CommandLineRunner;
import jcf.cmd.runner.spring.FasterSpringRunnerRepository;

public class Main {
	
	private static final Logger logger = LoggerFactory
			.getLogger(Main.class);

	private static final String basePackage = "jcf.sample.extproccmd.runners";
	private static final String contextPath = "classpath*:META-INF/spring/context.xml";

	public static void main(String[] args) {
		
		FasterSpringRunnerRepository repository = new FasterSpringRunnerRepository(basePackage, contextPath);

		if (args.length < 1) {
			System.err.println("candidates : ");
			for (String r : repository.getRunners()) {
				System.err.print(r);
				System.err.print("\t");
			}
			System.err.println();
			System.exit(1);
		}
		String runnerName = args[0];
		
		
		try {
			CommandLineRunner runner = repository.getRunner(runnerName);
			
			runner.run(args);
			
		} catch (Exception e) {
			logger.warn("error executing " + runnerName, e);
			
		} finally {
			repository.close();
		}
	}
	
}
