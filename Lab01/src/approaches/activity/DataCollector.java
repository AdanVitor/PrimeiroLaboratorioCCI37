package approaches.activity;

import java.util.ArrayList;

public class DataCollector {

	private static DataCollector collector = null;
	private ArrayList<Client> clientList = new ArrayList<>();

	private DataCollector() {
	}

	public static DataCollector sharedInstance() {
		if (collector == null) {
			collector = new DataCollector();
		}
		return collector;
	}

	public void addClient(Client client) {
		clientList.add(client);
	}

	public void printClientStatistics() {
		int clientListSize = clientList.size();
		if (clientListSize != 0) {
			double waitingTimeSum = 0;
			double serviceTimeSum = 0;
			double totalTimeSum = 0;
			for (Client c : clientList) {
				waitingTimeSum += (c.start - c.arrive);
				serviceTimeSum += (c.end - c.start);
				totalTimeSum += (c.end - c.arrive);
			}
			System.out.println("Average waiting time: " + waitingTimeSum / clientListSize);
			System.out.println("Average service time: " + serviceTimeSum / clientListSize);
			System.out.println("Average total time: " + totalTimeSum / clientListSize);
			System.out.println("Number of clients: " + clientListSize);
		}
	}
}
