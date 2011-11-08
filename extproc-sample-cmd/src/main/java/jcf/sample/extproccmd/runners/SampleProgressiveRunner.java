package jcf.sample.extproccmd.runners;

import jcf.cmd.progress.builder.ProgressWriterBuilder;
import jcf.cmd.progress.writer.ProgressWriter;
import jcf.cmd.runner.AbstractProgressiveRunner;

public class SampleProgressiveRunner extends AbstractProgressiveRunner {

	@Override
	protected ProgressWriter prepare(ProgressWriterBuilder builder) {
		return builder.addInt("prgoress").build();
	}

	@Override
	protected void run(ProgressWriter progressWriter) {
		try {
			for (int i = 0; i <= 100; i+= 10) {
				Thread.sleep(1000);
				progressWriter.setInt("prgoress", i);
				System.out.println("progress " + i);
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
