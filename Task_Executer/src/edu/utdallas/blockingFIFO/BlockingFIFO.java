package edu.utdallas.blockingFIFO;

import edu.utdallas.taskExecutor.Task;

// BlockingFIFO Interface contains following methods to implement.
public interface BlockingFIFO {
	void put(Task item) throws Exception;
    Task take() throws Exception;

}
