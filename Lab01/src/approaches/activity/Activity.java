package approaches.activity;

import java.util.Arrays;

import util.Statistics;

public class Activity {
	private final double CLIENT_ARRIVAL_MEAN = 5;
	private final double ATM_MEAN = 4;
	private final double ATM_VARIANCE = 2;
	
	private enum Type {
		ARRIVE_CLIENT,
		END_ATM_SERVICE,
	}
	private Client client;  // acho que isso não é necessário!!
	private double time;
	private Type type;
	private AvailableServices services = AvailableServices.sharedInstance();
	private Executive executive = Executive.sharedInstance();
	private QueueManager queueManager = new QueueManager();

	public Activity(Client client, double time, Type type) {
		this.client = client;
		this.time = time;
		this.type = type;
	}

	public void arriveClient() {
		double time1 = 0;
		double simTime = executive.simulationTime();
		if (type == Type.ARRIVE_CLIENT && time == simTime) {
			Client client = new Client();
			time1 = simTime + Statistics.exponential(CLIENT_ARRIVAL_MEAN);
			executive.addActivity(new Activity(client, time1, Type.ARRIVE_CLIENT));
			queueManager.enqueueATMClient(client);
		}
	}

	public void startATMService() {
		double time1 = 0;
		double simTime = executive.simulationTime();
		if (services.isATMFree()) {
			Client client = queueManager.dequeueATMClient();
			if (client != null) {
				time1 = simTime + Statistics.normal(ATM_MEAN, ATM_VARIANCE);
				executive.addActivity(new Activity(client, time1, Type.END_ATM_SERVICE));
			}
		}
	}

	public void endATMService () {
		double simTime = executive.simulationTime();
		if (type == Type.END_ATM_SERVICE && time == simTime) {
			services.freeFirstBusyATM();
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
		this.arriveCall();
		this.endATMService();
		this.endCashierService();
		this.endManagerService();
		this.endCall();
		this.startATMService();
		this.startCashierService();
		this.startManagerService();
		this.startCall();
	}
	
	public double getTime() {
		return time;
	}

	public static void main(String[] args) {
		Activity activity = null;
		Executive executive = Executive.sharedInstance();
		while (!executive.isTimeFinished()) {
			activity = executive.getActivity();
			activity.executeActivity();
			executive.removeActivity();
		}
	}
}
