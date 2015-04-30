package main;

import util.RaffleAttendantController;
import util.Statistics;
import attendantsPackage.AttendantController;
import entities.*;
import executive.*;

public class Main {

	public static void main(String[] args) {
		
		double nextTimeClient = Statistics.getNextClientArrival(0);
		Client firstClient = new Client(nextTimeClient);
		// ruffle the attendant of the Client
		AttendantController clientAttendant = RaffleAttendantController
				.getClientAnyAttendent(Statistics.random());
		double nextTimeCall = Statistics.getNextCallArrival(0);
		Call firstCall = new Call(nextTimeCall);
		// returning the managerController
		AttendantController callAttendant = RaffleAttendantController
				.getManagerController();

		Executive.simulationEnd= 500;
		// simulations parameters are in Statistics.java
		
		
		Executive.addEvent(nextTimeClient, EventConstants.ARRIVE_ClIENT, firstClient, clientAttendant);
		Executive.addEvent(nextTimeCall, EventConstants.ARRIVE_CALL, firstCall, callAttendant);
		
		while(Executive.simulationTime < Executive.simulationEnd){
			Executive.timeScan();
			Executive.executeEvents();
		}
	}

}
