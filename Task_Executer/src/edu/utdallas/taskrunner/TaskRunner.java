package edu.utdallas.taskrunner;

import edu.utdallas.blockingFIFO.BlockingFIFO;
import edu.utdallas.taskExecutor.Task;

public class TaskRunner extends Thread {

	BlockingFIFO blockingFIFOImpl;

	//Set the BlockingFIFO in constructor
	public TaskRunner(BlockingFIFO blockingFIFOQueue) {
		blockingFIFOImpl = blockingFIFOQueue;
	}

	//Run method overrides thread run method and starts running when called
	@Override
	public void run() {
		while (true) {
			try {
				Task newTask = blockingFIFOImpl.take(); //Get task from FIFOQueue
				newTask.execute();						//Execute task from test
			}

			catch (Throwable th) {
				th.printStackTrace();
			}
		}
	}
}
