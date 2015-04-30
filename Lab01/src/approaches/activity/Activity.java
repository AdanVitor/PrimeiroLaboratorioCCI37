package approaches.activity;

import util.Statistics;

public class Activity {
	private static AvailableServices services = AvailableServices.sharedInstance();
	private static Executive executive = Executive.sharedInstance();
	private static QueueManager queueManager = QueueManager.sharedInstance();
	private static DataCollector dataCollector = DataCollector.sharedInstance();

	private final double CLIENT_ARRIVAL_MEAN = 5;
	private final double ATM_MEAN = 4;
	private final double ATM_VARIANCE = 2;
	
	private enum Type {
		ARRIVE_CLIENT,
		END_ATM_SERVICE,
	}
	private Client client;
	private double time;
	private Type type;

	public Activity(double time, Type type) {
		this.time = time;
		this.type = type;
	}

	public void arriveClient() {
		double time1 = 0;
		double simTime = executive.simulationTime();
		if (type == Type.ARRIVE_CLIENT && time == simTime) {
			time1 = simTime + Statistics.exponential(CLIENT_ARRIVAL_MEAN);
			Client client = new Client(time1);
			executive.addActivity(new Activity(time1, Type.ARRIVE_CLIENT));
			queueManager.enqueueATMClient(client);
		}
	}

	public void startATMService() {
		double simTime = executive.simulationTime();
		if (services.isATMFree() && queueManager.peekATMClient() != null && queueManager.peekATMClient().arrive <= simTime) {
			client = queueManager.dequeueATMClient();
			if (client != null) {
				client.start = simTime;
				time = simTime + Statistics.normal(ATM_MEAN, ATM_VARIANCE);
				type = Type.END_ATM_SERVICE;
				executive.addActivity(this);
				services.makeOneATMBusy();
			}
		}
	}

	public void endATMService () {
		double simTime = executive.simulationTime();
		if (type == Type.END_ATM_SERVICE && time == simTime) {
			client.end = simTime;
			services.freeFirstBusyATM();
			dataCollector.addClient(client);
		}
	}

	public void startCashierService() {

	}

	public void endCashierService () {

	}

	public void startManagerService() {

	}

	public void endManagerService () {

	}

	public void arriveCall () {

	}

	public void startCall () {

	}

	public void endCall () {

	}

	public void executeActivity () {
		this.arriveClient();
//		this.arriveCall();
		this.endATMService();
//		this.endCashierService();
//		this.endManagerService();
//		this.endCall();
		this.startATMService();
//		this.startCashierService();
//		this.startManagerService();
//		this.startCall();
	}
	
	public double getTime() {
		return time;
	}

	public static void main(String[] args) {
		double initialSimulationTime = 0;
		Client firstClient = new Client(initialSimulationTime);
		Activity activity = new Activity(initialSimulationTime, Type.ARRIVE_CLIENT);
		queueManager.enqueueATMClient(firstClient);
		executive.addActivity(activity);
		while (!executive.isTimeFinished()) {
//			System.out.println(executive.simulationTime());
			activity = executive.getActivity();
			activity.executeActivity();
			executive.removeActivity();
		}
		
		dataCollector.printClientStatistics();
	}
}
