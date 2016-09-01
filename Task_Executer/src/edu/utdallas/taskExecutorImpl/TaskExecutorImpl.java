package edu.utdallas.taskExecutorImpl;

import java.util.ArrayList;
import java.util.List;
import edu.utdallas.blockingFIFO.BlockingFIFO;
import edu.utdallas.blockingFIFO.implementation.BlockingFIFOImplementation;
import edu.utdallas.taskExecutor.Task;
import edu.utdallas.taskExecutor.TaskExecutor;
import edu.utdallas.taskrunner.TaskRunner;

public class TaskExecutorImpl implements TaskExecutor {

	// Create BlockingFIFOQueue to add tasks
	BlockingFIFO blockingFIFOImplementation;

	// Creating ArrayList of Taskrunners
	List<TaskRunner> taskRunnersList;

	// Constructor for initializing FIFOQueue and add TaskRunners into ArrayList
	public TaskExecutorImpl(int noOfThreads) {
		taskRunnersList = new ArrayList<>();
		blockingFIFOImplementation = new BlockingFIFOImplementation();
		runThreads(noOfThreads);
	}

	@Override
	public void addTask(Task task) {
		try {
			blockingFIFOImplementation.put(task);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Start running threads using TaskRunner class which contains run method
	public void runThreads(int totalThreads) {
		for (int i = 0; i < totalThreads; i++) {
			taskRunnersList.add(new TaskRunner(blockingFIFOImplementation));
		}

		for (int i=0;i < taskRunnersList.size();i++) {
			taskRunnersList.get(i).start();
		}
	}
}
