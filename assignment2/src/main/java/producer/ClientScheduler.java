package producer;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import client.Consumer;
import queue.Queue;

public class ClientScheduler extends SwingWorker<String, String> {
	private int simulationTime;
	private List<Queue> queues;
	private RandomClientGenerator clientGenerator;
	private long simulationStartTime;
	private boolean simulationRunning;
	private JTextArea queueTextArea;
	private JTextArea clientTextArea;

	public ClientScheduler(int simulationTime, RandomClientGenerator clientGenerator, int numQueues,
			JTextArea queueTextArea, JTextArea clientTextArea) {
		this.simulationTime = simulationTime;
		this.clientGenerator = clientGenerator;

		queues = new ArrayList<Queue>(numQueues);
		for (int i = 0; i < numQueues; i++) {
			// maximum queue capacity is 20
			Queue queue = new Queue(i, clientTextArea);
			queues.add(queue);
			Consumer c = new Consumer(queue);
			Thread t = new Thread(c);
			t.start();

		}
		this.queueTextArea = queueTextArea;
		this.clientTextArea = clientTextArea;
		simulationStartTime = System.currentTimeMillis();
		simulationRunning = true;
	}

	public boolean isSimulationRunning() {
		return simulationRunning;
	}

	@Override
	protected String doInBackground() throws Exception {
		do {
			ClientSimulation cl = clientGenerator.generateNewClient();
			queues.get(cl.getQueue()).add(cl);
			try {
				Thread.sleep(cl.getArrivalTime() * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//get statistics about processing, they will be sent to the UI by the call to publish method
			StringBuilder sb = new StringBuilder();
			for (Queue q : queues) {
				sb.append(q.getStats());
			}
	
			// calculate simulation progress (percents)
			long runningFor = System.currentTimeMillis() - simulationStartTime;
			simulationRunning = System.currentTimeMillis() - simulationStartTime <= simulationTime * 1000;
			if (runningFor >= simulationTime * 1000) {
				this.setProgress(100);
			} else {
				this.setProgress((int) (100 * runningFor / (simulationTime * 1000)));
			}
			sb.append("Ran " + getProgress() + "% of simulation \n");
			// this will send the intermediate results to process function that will update the textArea on the UI
			publish(sb.toString());
		} while (simulationRunning);
		
		StringBuilder sb = new StringBuilder();
		for (Queue q : queues) {
			sb.append(q.getStats());
		}
		return sb.toString();
	}

	@Override
	protected void process(final List<String> chunks) {
		// Updates the messages text area
		for (final String string : chunks) {
			queueTextArea.append(string);
			queueTextArea.append("\n");
		}
	}

}
