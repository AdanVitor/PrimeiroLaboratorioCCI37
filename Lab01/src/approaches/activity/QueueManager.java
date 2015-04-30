package approaches.activity;

import java.util.TreeMap;

public class QueueManager {
	
	private static QueueManager queueManager = null;
	
	private TreeMap<Double, Client> atmQueue = new TreeMap<>();
	private TreeMap<Double, Client> cashierQueue = new TreeMap<>();
	private TreeMap<Double, Client> managerQueue = new TreeMap<>();
	private TreeMap<Double, Call> managerCallQueue = new TreeMap<>();
	
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
		return atmQueue.firstEntry() == null ? null : atmQueue.firstEntry().getValue();
	}
	
	public Client dequeueCashierClient() {
		if(cashierQueue.size() == 0) {
			return null;
		}
		
		return cashierQueue.remove(cashierQueue.firstKey());
	}
	
	public void enqueueCashierClient(Client c) {
		cashierQueue.put(c.arrive, c);
	}
	
	public Client peekCashierClient() {
		return cashierQueue.firstEntry() == null ? null : cashierQueue.firstEntry().getValue();
	}

	public Client dequeueManagerClient() {
		if(managerQueue.size() == 0) {
			return null;
		}
		
		return managerQueue.remove(managerQueue.firstKey());
	}
	
	public void enqueueManagerClient(Client c) {
		managerQueue.put(c.arrive, c);
	}
	
	public Client peekManagerClient() {
		return managerQueue.firstEntry() == null ? null : managerQueue.firstEntry().getValue();
	}

	public Call dequeueManagerCall() {
		if(managerCallQueue.size() == 0) {
			return null;
		}
		
		return managerCallQueue.remove(managerCallQueue.firstKey());
	}
	
	public void enqueueManagerCall(Call c) {
		managerCallQueue.put(c.arrive, c);
	}
	
	public Call peekManagerCall() {
		return managerCallQueue.firstEntry() == null ? null : managerCallQueue.firstEntry().getValue();
	}

}
