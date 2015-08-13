package approaches.activity;
import java.sql.ClientInfoStatus;

import util.Statistics;

public class Activity {
	private static final double CLIENT_ARRIVAL_MEAN = 5;

	private static final double PAYMENT_MEAN = 7;
	private static final double PAYMENT_VARIANCE = 3;
	
	private static final double PLAY_MEAN = 3;
	private static final double PLAY_VARIANCE = 2;
	
	private static final double PAYMENT_AND_PLAY_MEAN = 8;
	private static final double PAYMENT_AND_PLAY_VARIANCE = 2;


	private static final double paymentPorcentage = 0.3;
	private static final double playAndPaymentPorcentage = 0.4;

	private static AvailableServices services = AvailableServices
			.sharedInstance();
	
	private static Executive executive = Executive.sharedInstance();
	private static QueueManager queueManager = QueueManager.sharedInstance();
	private static DataCollector dataCollector = DataCollector.sharedInstance();
	
	private static boolean isUniqueQueue = true;

	private enum Type {
		ARRIVE_CLIENT, END_PAYMENT_CASH_SERVICE, END_PLAY_CASH_SERVICE
	}

	private Client client;
	private double time;
	private Type type;

	public Activity(double time, Type type) {
		this.time = time;
		this.type = type;
	}

	private static void chooseClientQueue(Client client) {
		double random = Math.random();
		if (random < paymentPorcentage) {
			if(!isUniqueQueue){
				if (!queueManager.paymentQueueIsEmpty()
						&& queueManager.playQueueIsEmpty()) {
					queueManager.enqueuePlayClient(client);
				} else {
					queueManager.enqueuePaymentClient(client);
				}
			}
			else{
				queueManager.enqueuePaymentClient(client);
			}
			
			// sort another random to choose the client category
			random = Math.random();
			if (random < playAndPaymentPorcentage) {
				client.type = Client.Type.PAYMENT_AND_PLAY;
			} else {
				client.type = Client.Type.PAYMENT;
			}
		} else {
			if(!isUniqueQueue){
				if (!queueManager.playQueueIsEmpty()
						&& queueManager.paymentQueueIsEmpty()) {
					queueManager.enqueuePaymentClient(client);
				}else{
					queueManager.enqueuePlayClient(client);
				}
			}
			else{
				queueManager.enqueuePaymentClient(client);
			}
			
			client.type = Client.Type.PLAY;
			
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

	public void startPaymentCashService() {
		double simTime = executive.simulationTime();
		if (services.isPaymentCashFree()) {
			if(isClientWaiting(queueManager.peekPaymentClient(), simTime)){
				configureEndPaymentCashService(simTime, queueManager.dequeuePaymentQueue(), Type.END_PAYMENT_CASH_SERVICE);
			} 
		}
	}
	
	public void startPlayCashService() {
		double simTime = executive.simulationTime();
		if (services.isPlayCashFree()) {
			if(isClientWaiting(queueManager.peekPlayClient(), simTime)){
				configureEndPlayCashService(simTime, queueManager.dequeuePlayQueue(), Type.END_PLAY_CASH_SERVICE);
			} 
		}
	}
	
	public void endPaymentService() {
		double simTime = executive.simulationTime();
		if (type == Type.END_PAYMENT_CASH_SERVICE && time == simTime ) {
			freeBusyCash(client, simTime);
		}
	}
	
	public void endPlayService(){
		double simTime = executive.simulationTime();
		if (type == Type.END_PLAY_CASH_SERVICE && time == simTime ) {
			freeBusyCash(client, simTime);
		}
	}
	

	
	
	private void freeBusyCash(Client client, double simTime){
		if(client == null){
			return;
		}
		if(!isUniqueQueue){
			if(client.isInCashPayment){
				services.freeFirstBusyPaymentCash();
			}
			else{
				services.freeFirstBusyPlayCash();
			}
		}
		else{
			services.freeFirstBusyPaymentCash();
		}
		
		client.end = simTime;
		dataCollector.addClient(client);
	}
	
	private boolean isClientWaiting (Client client , double simTime){
		return client != null && client.arrive <= simTime;
	}
	  
	private void configureEndPaymentCashService(double simTime, Client client ,Type activityType){
		configureEndCashServiceParameters(simTime, client, activityType);
		client.isInCashPayment = true;
		services.makeOnePaymentCashBusy();
	}
	
	private void configureEndPlayCashService(double simTime, Client client ,Type activityType){
		
		configureEndCashServiceParameters(simTime, client, activityType);
		client.isInCashPayment = false;
		services.makeOnePlayCashBusy();
	}
	
	private void configureEndCashServiceParameters(double simTime, Client client ,Type activityType){
		this.client = client;
		client.start = simTime;
		time = simTime + getClientServiceTime(client);
		type = activityType;
		executive.addActivity(this);
	}

	private double getClientServiceTime(Client client) {
		double serviceTime = 0;
		switch (client.type) {
		case PAYMENT:
			serviceTime = Statistics.normalLimited(PAYMENT_MEAN, PAYMENT_VARIANCE);
			break;
		case PLAY:
			serviceTime = Statistics.normalLimited(PLAY_MEAN,PLAY_VARIANCE);
			break;
		default:
			serviceTime = Statistics.normalLimited(PAYMENT_AND_PLAY_MEAN, PAYMENT_AND_PLAY_VARIANCE);
		}
		return serviceTime;

	}


	public void executeActivity() {
		if(!isUniqueQueue){
			this.arriveClient();
			this.endPaymentService();
			this.endPlayService();
			this.startPaymentCashService();
			this.startPlayCashService();
		}
		else{
			this.arriveClient();
			this.endPaymentService();
			this.startPaymentCashService();
		}
		
	}

	public double getTime() {
		return time;
	}

	public static void main(String[] args) {
		double initialSimulationTime = 0;
		Client firstClient = new Client(initialSimulationTime);
		chooseClientQueue(firstClient);

		Activity activity = new Activity(initialSimulationTime,
				Type.ARRIVE_CLIENT);
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
