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
		progressWriter.setInt("prgoress", 0);

		try {
			Thread.sleep(1000);
			progressWriter.setInt("prgoress", 10);
			Thread.sleep(1000);
			progressWriter.setInt("prgoress", 20);
			Thread.sleep(1000);
			progressWriter.setInt("prgoress", 30);
			Thread.sleep(1000);
			progressWriter.setInt("prgoress", 40);
			Thread.sleep(1000);
			progressWriter.setInt("prgoress", 70);
			Thread.sleep(1000);
			progressWriter.setInt("prgoress", 80);
			Thread.sleep(1000);
			progressWriter.setInt("prgoress", 90);
		} catch (InterruptedException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		progressWriter.setInt("prgoress", 100);
	}


}
