package edu.utdallas.blockingFIFO.implementation;
import edu.utdallas.blockingFIFO.BlockingFIFO;
import edu.utdallas.taskExecutor.Task;

public class BlockingFIFOImplementation implements BlockingFIFO {

	private int FIFOMaxSize;
	//Array of Tasks to be maintained in QUEUE
	private  Task itemsInQueue[];
	private int nextIntoFIFO;
	private int nextOutFIFO;
	private int count;
	
	//BlockingFIFOQueue size is limited to 100
	private int MaxLimit=100; 
	
	//Creation of Monitor objects
	private  Object notEmpty=new Object();
	private  Object notFull=new Object();

	//Constructor initializing MaxSize of FIFOQueue
	public BlockingFIFOImplementation() {
		this.FIFOMaxSize = MaxLimit;
		this.itemsInQueue = new Task[MaxLimit];
	}

	//Adding Task in QUEUE
	public void put(Task x) throws InterruptedException {
		while (true) {
			if (count == FIFOMaxSize){
				synchronized (notFull) {
					notFull.wait();
				}
			}
			synchronized (this) {
				if (count == FIFOMaxSize) {
					continue;
				}
				itemsInQueue[nextIntoFIFO] = x;
				nextIntoFIFO = (nextIntoFIFO + 1) % FIFOMaxSize;
				count++;
				synchronized (notEmpty) {
					notEmpty.notify();
				}
				return;
			}

		}

	}

	//Remove Each task in QUEUE and method returns task 
	public Task take() throws InterruptedException {
		while (true) {
			if (count == 0) {
				synchronized (notEmpty) {
					notEmpty.wait();
				}
			}
			synchronized (this) {
				if (count == 0) {
					continue;
				}
				Task task = itemsInQueue[nextOutFIFO];
				nextOutFIFO = (nextOutFIFO + 1) % FIFOMaxSize;
				count--;
				synchronized (notFull) {
					notFull.notify();
				}
				return task;
			}
		}	
	}
}
