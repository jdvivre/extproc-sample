package jcf.sample.extproccmd.runners;

import jcf.cmd.progress.builder.ProgressWriterBuilder;
import jcf.cmd.progress.writer.ProgressWriter;
import jcf.cmd.runner.AbstractProgressiveRunner;

public class SampleProgressiveRunner extends AbstractProgressiveRunner {

	@Override
	protected ProgressWriter prepare(ProgressWriterBuilder builder) {
		return builder.addString("retVal", 32).build();
	}

	@Override
	protected void run(ProgressWriter progressWriter) {
		progressWriter.setString("retVal", "start");

		;
		
		progressWriter.setString("retVal", "finish");
	}


}
