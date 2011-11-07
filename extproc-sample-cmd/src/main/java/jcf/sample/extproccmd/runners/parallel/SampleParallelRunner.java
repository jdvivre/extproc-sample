package jcf.sample.extproccmd.runners.parallel;

import jcf.cmd.progress.parallel.LogicContainer;
import jcf.cmd.progress.parallel.ParallelProgressSetter;
import jcf.cmd.progress.parallel.ProgressiveRunnable;
import jcf.cmd.runner.AbstractParallelJobRunner;

public class SampleParallelRunner extends AbstractParallelJobRunner {

	@Override
	protected void setUpContainer(LogicContainer logicContainer) {

		logicContainer.add(new ProgressiveRunnable() {

			public void run(ParallelProgressSetter progress) {

				long startTime = System.currentTimeMillis();

				progress.setMessage("시작");

				progress.setTotal(10);

				for (int i = 0; i < 10; i++) {
					try {
						Thread.sleep(1000);

					} catch (InterruptedException e) {
						break;
					}

					if (System.currentTimeMillis() - startTime > 5000) {
						progress.setMessage("커피나 한잔?");
					}
					progress.setCurrent(i);
				}

				progress.setMessage("끝");
			}
		});

		logicContainer.add(new ProgressiveRunnable() {

			public void run(ParallelProgressSetter progress) {

				long startTime = System.currentTimeMillis();

				progress.setMessage("시작");

				progress.setTotal(10);

				for (int i = 0; i < 10; i++) {
					try {
						Thread.sleep(1000);

					} catch (InterruptedException e) {
						break;
					}

					if (System.currentTimeMillis() - startTime > 5000) {
						progress.setMessage("커피나 한잔?");
					}
					progress.setCurrent(i);
				}

				progress.setMessage("끝");
			}
		});

	}

}
