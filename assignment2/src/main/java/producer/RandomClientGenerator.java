package producer;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class RandomClientGenerator {
	private int minArrivalTime;
	private int maxArrivalTime;
	private int minServiceTime;
	private int maxServiceTime;
	private int numOfQueues;
	private AtomicInteger clientIdGen;
	private Random randomGen;

	public RandomClientGenerator(int minArrivalTime, int maxArrivalTime, int minServiceTime, int maxServiceTime, int numQueus) {
		super();
		this.minArrivalTime = minArrivalTime;
		this.maxArrivalTime = maxArrivalTime;
		this.minServiceTime = minServiceTime;
		this.maxServiceTime = maxServiceTime;
		clientIdGen = new AtomicInteger(1);
		randomGen = new Random();
		this.numOfQueues = numQueus;
	}
	
	public ClientSimulation generateNewClient() {
		int arrivalTime = randomGen.nextInt((maxArrivalTime - minArrivalTime) + 1) + minArrivalTime;
		int serviceTime = randomGen.nextInt((maxServiceTime - minServiceTime) + 1) + minServiceTime;
		return new ClientSimulation(clientIdGen.getAndIncrement(), arrivalTime, serviceTime, randomGen.nextInt(numOfQueues)); 
	}
	
}
