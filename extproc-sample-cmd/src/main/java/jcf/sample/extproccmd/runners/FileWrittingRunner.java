package jcf.sample.extproccmd.runners;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import jcf.cmd.runner.CommandLineRunner;

public class FileWrittingRunner implements CommandLineRunner {

	public void run(String[] args){
		File file = new File("sample-file");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file);
			Thread.sleep(4000);
			fileWriter.write("TEST TEST TEST");
		} catch (Exception e) {
			throw new UnsupportedOperationException("ERROR OCCUR WHILE WRITTING A FILE");
		} finally{
			try {
				fileWriter.close();
			} catch (IOException e) {
				throw new UnsupportedOperationException("ERROR OCCUR WHILE WRITTING A FILE");
			}
		}
	}

}
