package approaches.activity;

import util.Statistics;

public class Activity {
	private static final double CLIENT_ARRIVAL_MEAN = 5;
	private static final double CALL_ARRIVAL_MEAN = 10;
	private static final double ATM_MEAN = 4;
	private static final double ATM_VARIANCE = 2;
	private static final double CASHIER_MEAN = 7;
	private static final double CASHIER_VARIANCE = 3;
	private static final double MANAGER_MEAN = 10;
	private static final double MANAGER_VARIANCE = 4;
	private static final double CALL_TIME_A = 1;
	private static final double CALL_TIME_B = 10;

	private static AvailableServices services = AvailableServices
			.sharedInstance();
	private static Executive executive = Executive.sharedInstance();
	private static QueueManager queueManager = QueueManager.sharedInstance();
	private static DataCollector dataCollector = DataCollector.sharedInstance();

	private enum Type {
		ARRIVE_CLIENT, ARRIVE_CALL, END_ATM_SERVICE, END_CASHIER_SERVICE, END_MANAGER_SERVICE, END_MANAGER_CALL_SERVICE,
	}

	private Client client;
	private Call call;
	private double time;
	private Type type;

	public Activity(double time, Type type) {
		this.time = time;
		this.type = type;
	}

	private static void chooseClientQueue(Client client) {
		double random = Math.random();
		if (random < 0.7) {
			queueManager.enqueueATMClient(client);
		} else if (random >= 0.7 && random < 0.9) {
			queueManager.enqueueCashierClient(client);
		} else {
			queueManager.enqueueManagerClient(client);
		}
	}

	public void arriveClient() {
		double time1 = 0;
		double simTime = executive.simulationTime();
		if (type == Type.ARRIVE_CLIENT && time == simTime) {
			time1 = simTime + Statistics.exponential(CLIENT_ARRIVAL_MEAN);
			executive.addActivity(new Activity(time1, Type.ARRIVE_CLIENT));

			// Decide here in which queue to put the client, using the
			// probabilities.
			Client client = new Client(time1);
			chooseClientQueue(client);
		}
	}

	public void startATMService() {
		double simTime = executive.simulationTime();
		if (services.isATMFree() && queueManager.peekATMClient() != null
				&& queueManager.peekATMClient().arrive <= simTime) {
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

	public void endATMService() {
		double simTime = executive.simulationTime();
		if (type == Type.END_ATM_SERVICE && time == simTime) {
			services.freeFirstBusyATM();
			client.end = simTime;

			// Decide here if this client is still going to use other services.
			double random = Math.random();
			if (random < 0.15) {
				queueManager.enqueueCashierClient(client);
			} else if (random >= 0.15 && random < 0.3) {
				queueManager.enqueueManagerClient(client);
			} else {
				dataCollector.addClient(client);
			}
		}
	}

	public void startCashierService() {
		double simTime = executive.simulationTime();
		if (services.isCashierFree() && queueManager.peekCashierClient() != null && queueManager.peekCashierClient().arrive <= simTime) {
			client = queueManager.dequeueCashierClient();
			if (client != null) {
				if (client.start != client.end) {
					// Client from ATM. Adjust offsets.
					client.start = simTime - client.end;
					client.end = client.start;
				}
				else {
					client.start = simTime;
				}
				time = simTime + Statistics.normal(CASHIER_MEAN, CASHIER_VARIANCE);
				type = Type.END_CASHIER_SERVICE;
				executive.addActivity(this);
				services.makeOneCashierBusy();
			}
		}
	}

	public void endCashierService() {
		double simTime = executive.simulationTime();
		if (type == Type.END_CASHIER_SERVICE && time == simTime) {
			services.freeFirstBusyCashier();
			client.end = simTime;
			dataCollector.addClient(client);
		}
	}

	public void startManagerService() {
		double simTime = executive.simulationTime();
		if (services.isManagerFree() && queueManager.peekManagerCall() == null && queueManager.peekManagerClient() != null
				&& queueManager.peekManagerClient().arrive <= simTime) {
			client = queueManager.dequeueManagerClient();
			if (client != null) {
				if (client.start != client.end) {
					client.start = simTime - client.end;
					client.end = client.start;
				}
				else {
					client.start = simTime;
				}
				time = simTime + Statistics.normal(MANAGER_MEAN, MANAGER_VARIANCE);
				type = Type.END_MANAGER_SERVICE;
				executive.addActivity(this);
				services.makeOneManagerBusy();
			}
		}
	}

	public void endManagerService() {
		double simTime = executive.simulationTime();
		if (type == Type.END_MANAGER_SERVICE && time == simTime) {
			services.freeFirstBusyManager();
			client.end = simTime;
			dataCollector.addClient(client);
		}
	}

	public void arriveCall() {
		double time1, simTime = executive.simulationTime();
		if (type == Type.ARRIVE_CALL && time == simTime) {
			time1 = simTime + Statistics.exponential(CALL_ARRIVAL_MEAN);
			Call call = new Call(time1);
			executive.addActivity(new Activity(time1, Type.ARRIVE_CALL));
			queueManager.enqueueManagerCall(call);
		}
	}

	public void startCall() {
		double simTime = executive.simulationTime();
		if (services.isManagerFree() && queueManager.peekManagerCall() != null && queueManager.peekManagerCall().arrive <= simTime) {
			call = queueManager.dequeueManagerCall();
			if (call != null) {
				call.start = simTime;
				time = simTime + Statistics.uniform(CALL_TIME_A, CALL_TIME_B);
				type = Type.END_MANAGER_CALL_SERVICE;
				executive.addActivity(this);
				services.makeOneManagerBusy();
			}
		}
	}

	public void endCall() {
		double simTime = executive.simulationTime();
		if (type == Type.END_MANAGER_CALL_SERVICE && time == simTime) {
			services.freeFirstBusyManager();
			call.end = simTime;
			dataCollector.addCall(call);
		}
	}

	public void executeActivity() {
		this.arriveClient();
		this.arriveCall();
		this.endATMService();
		this.endCashierService();
		this.endCall();
		this.endManagerService();
		this.startATMService();
		this.startCashierService();
		this.startCall();
		this.startManagerService();
	}

	public double getTime() {
		return time;
	}

	public static void main(String[] args) {
		double initialSimulationTime = 0;
		Client firstClient = new Client(initialSimulationTime);
		chooseClientQueue(firstClient);
		Activity activity = new Activity(initialSimulationTime, Type.ARRIVE_CLIENT);
		executive.addActivity(activity);
		activity = new Activity(initialSimulationTime + 0.001, Type.ARRIVE_CALL);
		executive.addActivity(activity);
		while (!executive.isTimeFinished()) {
			// System.out.println(executive.simulationTime());
			activity = executive.getActivity();
			activity.executeActivity();
			executive.removeActivity();
		}

		dataCollector.printClientStatistics();
	}
}
