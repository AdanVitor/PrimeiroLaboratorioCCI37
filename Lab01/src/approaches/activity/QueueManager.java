package approaches.activity;

import java.util.Deque;
import java.util.LinkedList;

public class QueueManager {
	
	private Deque<Client> atmQueue = new LinkedList<>();
	private Deque<Client> cashierQueue = new LinkedList<>();
	private Deque<Client> managerQueue = new LinkedList<>();
	
	public Client dequeueATMClient() {
		return atmQueue.poll();
	}
	
	public void enqueueATMClient(Client c) {
		atmQueue.offer(c);
	}
	
	public Client dequeueCashierClient() {
		return cashierQueue.poll();
	}
	
	public void enqueueCashierClient(Client c) {
		cashierQueue.offer(c);
	}
	
	public Client dequeueManagerClient() {
		return managerQueue.poll();
	}
	
	public void enqueueManagerClient(Client c) {
		managerQueue.offer(c);
	}
}
