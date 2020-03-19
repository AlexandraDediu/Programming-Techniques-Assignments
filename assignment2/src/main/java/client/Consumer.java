package client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import producer.ClientSimulation;
import queue.Queue;

public class Consumer implements Runnable {
	private Queue queue;

	public Consumer(Queue queue) {
        this.queue = queue;
    }
 
    public void run() {
        try
        {
            while (true)
            {
            	ClientSimulation client = queue.take();
            	
                serveClient(client);
            }
        }
        catch (InterruptedException ex)
        {
            System.out.println("Consumer Interrupted");
        }
    }
 
    void serveClient(ClientSimulation client)
    {
        try
        {
            Thread.sleep(client.getServiceTime() * 1000); // simulate time passing
            client.setTimeWhenLeftQueue(System.currentTimeMillis());
            queue.markProcessed(client);
        }
        catch (InterruptedException ex)
        {
            System.out.println("Exception serving consumer");
        }
    }
}