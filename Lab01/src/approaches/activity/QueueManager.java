package approaches.activity;

import java.util.TreeMap;

public class QueueManager {
	
	private static QueueManager queueManager = null;
	
	private TreeMap<Double, Client> atmQueue = new TreeMap<>();
	private TreeMap<Double, Client> cashierQueue = new TreeMap<>();
	private TreeMap<Double, Client> managerQueue = new TreeMap<>();
	
	private QueueManager () {}
	
	public static QueueManager sharedInstance() {
		if (queueManager == null) {
			queueManager = new QueueManager();
		}
		return queueManager;
	}
	
	public Client dequeueATMClient() {
		if(atmQueue.size() == 0) {
			return null;
		}
		
		return atmQueue.remove(atmQueue.firstKey());
	}
	
	public void enqueueATMClient(Client c) {
		atmQueue.put(c.arrive, c);
	}

	public Client peekATMClient() {
		return atmQueue.firstEntry().getValue();
	}
	
	public Client dequeueCashierClient() {
		return cashierQueue.firstEntry().getValue();
	}
	
	public void enqueueCashierClient(Client c) {
		cashierQueue.put(c.arrive, c);
	}
	
	public Client dequeueManagerClient() {
		return managerQueue.firstEntry().getValue();
	}
	
	public void enqueueManagerClient(Client c) {
		managerQueue.put(c.arrive, c);
	}

}
