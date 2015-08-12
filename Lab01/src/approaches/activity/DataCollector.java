package approaches.activity;

import java.util.ArrayList;

public class DataCollector {

	private static DataCollector collector = null;
	private ArrayList<Client> clientList = new ArrayList<>();
	private ArrayList<Call> callList = new ArrayList<>();

	private DataCollector() {
	}

	public static DataCollector sharedInstance() {
		if (collector == null) {
			collector = new DataCollector();
		}
		return collector;
	}
	
	private <T extends Entity> void printStatisticsForList(ArrayList<T> list) {
		int listSize = list.size();
		if (listSize != 0) {
			double waitingTimeSum = 0;
			double serviceTimeSum = 0;
			double totalTimeSum = 0;
			for (T c : list) {
				waitingTimeSum += (c.start - c.arrive);
				serviceTimeSum += (c.end - c.start);
				totalTimeSum += (c.end - c.arrive);
			}
			System.out.println("Average waiting time: " + waitingTimeSum / listSize);
			System.out.println("Average service time: " + serviceTimeSum / listSize);
			System.out.println("Average total time: " + totalTimeSum / listSize);
			System.out.println("Number of clients: " + listSize);
		}


	}

	public void addClient(Client client) {
		clientList.add(client);
	}

	public void printClientStatistics() {
		printStatisticsForList(clientList);
	}
}
