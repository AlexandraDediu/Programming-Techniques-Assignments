package queue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.swing.JTextArea;

import producer.ClientSimulation;

public class Queue {
	private BlockingQueue<ClientSimulation> queue;
	private long emptyTime;
	private int queueId;
	private long emptyStartTime;
	private List<ClientSimulation> processed;
	private JTextArea clientTextArea;

	public Queue(int id, JTextArea clientTextArea) {
		this.queueId = id;
		queue = new ArrayBlockingQueue<ClientSimulation>(20);
		emptyStartTime = System.currentTimeMillis();
		processed = new ArrayList<ClientSimulation>();
		this.clientTextArea = clientTextArea;
	}

	public void add(ClientSimulation clientSimulation) {
		
		long addedToQueueTime = System.currentTimeMillis();
		if (queue.size() == 0) {
			emptyTime += (addedToQueueTime - emptyStartTime);
		}
		clientSimulation.setTimeWhenAddedToQueue(addedToQueueTime);
		clientTextArea.append("Client: " + clientSimulation.getClientId() + " added to queue "
				+ clientSimulation.getQueue() + " at " + formatDate(clientSimulation.getTimeWhenAddedToQueue()) + "\n");
		queue.add(clientSimulation);
	}

	public ClientSimulation take() throws InterruptedException {
		long removalTime = System.currentTimeMillis();
		ClientSimulation queueHead = queue.take();
		queueHead.setTimeWhenServiced(System.currentTimeMillis());
		clientTextArea.append("Client: " + queueHead.getClientId() + " waited in queue for : "
				+ (queueHead.getTimeWhenServiced() - queueHead.getTimeWhenAddedToQueue() ) / 1000 + " seconds.\n");
		if (queue.size() == 0) {
			emptyStartTime = removalTime;
		}
		return queueHead;
	}

	public int size() {
		return queue.size();
	}

	public void markProcessed(ClientSimulation simulation) {
		clientTextArea.append("Client: " + simulation.getClientId() + ": Service time was " + simulation.getServiceTime() + " seconds and was removed from queue at: "
				+ formatDate(simulation.getTimeWhenLeftQueue()) + "\n");
		processed.add(simulation);
	}

	public int getTotalProcessed() {
		return processed.size();
	}

	public long getAvgWaitingTime() {
		long avg = 0;
		for (ClientSimulation simulation : processed) {
			long waitingTime = (simulation.getTimeWhenLeftQueue() - simulation.getTimeWhenAddedToQueue())
					- simulation.getServiceTime() * 1000;
			avg += waitingTime;
		}
		return processed.size() > 0 ? avg / processed.size() : 0;
	}

	public long getMaxWaitingTime() {
		long max = 0;
		for (ClientSimulation simulation : processed) {
			long waitingTime = (simulation.getTimeWhenLeftQueue() - simulation.getTimeWhenAddedToQueue())
					- simulation.getServiceTime() * 1000;
			if (max < waitingTime) {
				max = waitingTime;
			}
		}
		return max;
	}

	public String getStats() {
		return "Queue " + queueId + "\n" + "was empty for: " + emptyTime / 1000 + "\n" + "current queue size: "
				+ queue.size() + "\n" + "processed: " + getTotalProcessed() + " clients\n" + "avg waiting time: "
				+ getAvgWaitingTime() / 1000 + "\n" + "max waiting time: " + getMaxWaitingTime() / 1000 + "\n\n";
	}

	private String formatDate(long millies) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		String dateString = format.format(new Date(millies));

		return dateString;
	}
}
