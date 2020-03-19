package producer;

public class ClientSimulation {
	private int clientId;
	private int arrivalTime;
	private int serviceTime;
	private int queue;
	private long timeWhenAddedToQueue;
	private long timeWhenLeftQueue;
	private long timeWhenServiced;

	public ClientSimulation(int clientId, int arrivalTime, int serviceTime, int queue) {
		this.clientId = clientId;
		this.arrivalTime = arrivalTime;
		this.serviceTime = serviceTime;
		this.queue = queue;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(int serviceTime) {
		this.serviceTime = serviceTime;
	}

	public int getQueue() {
		return queue;
	}

	public void setQueue(int queue) {
		this.queue = queue;
	}

	public long getTimeWhenAddedToQueue() {
		return timeWhenAddedToQueue;
	}

	public void setTimeWhenAddedToQueue(long timeWhenAddedToQueue) {
		this.timeWhenAddedToQueue = timeWhenAddedToQueue;
	}

	public long getTimeWhenLeftQueue() {
		return timeWhenLeftQueue;
	}

	public void setTimeWhenLeftQueue(long timeWhenLeftQueue) {
		this.timeWhenLeftQueue = timeWhenLeftQueue;
	}

	public long getTimeWhenServiced() {
		return timeWhenServiced;
	}

	public void setTimeWhenServiced(long timeWhenServiced) {
		this.timeWhenServiced = timeWhenServiced;
	}

}