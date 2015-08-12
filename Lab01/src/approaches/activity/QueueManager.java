package approaches.activity;

import java.util.TreeMap;

public class QueueManager {
	
	private static QueueManager queueManager = null;
	
	private TreeMap<Double, Client> playQueue = new TreeMap<>();
	private TreeMap<Double, Client> paymentQueue = new TreeMap<>();
	
	
	private QueueManager () {}
	
	public static QueueManager sharedInstance() {
		if (queueManager == null) {
			queueManager = new QueueManager();
		}
		return queueManager;
	}
	
	public Client dequeuePaymentQueue() {
		if(paymentQueue.size() == 0) {
			return null;
		}
		
		return paymentQueue.remove(paymentQueue.firstKey());
	}
	public void enqueuePaymentClient(Client c) {
		paymentQueue.put(c.arrive, c);
	}
	
	public Client peekPaymentClient() {
		return paymentQueue.firstEntry() == null ? null : paymentQueue.firstEntry().getValue();
	}
	
	public boolean paymentQueueIsEmpty(){
		return paymentQueue.size() == 0;
	}
	
	//------------------------------------------------------------------------------------------------
	
	public Client dequeuePlayQueue() {
		if(playQueue.size() == 0) {
			return null;
		}
		
		return playQueue.remove(playQueue.firstKey());
	}
	
	public void enqueuePlayClient(Client c) {
		playQueue.put(c.arrive, c);
	}

	public Client peekPlayClient() {
		return playQueue.firstEntry() == null ? null : playQueue.firstEntry().getValue();
	}
	
	public boolean playQueueIsEmpty(){
		return playQueue.size() == 0;
	}
}
