package main;

import util.RaffleAttendantController;
import util.Statistics;
import attendantsPackage.AttendantController;
import attendantsPackage.CashsController;
import attendantsPackage.EletronicCashsController;
import attendantsPackage.ManagersController;
import entities.*;
import executive.*;

public class Main {

	public static void main(String[] args) {
		int attendantsNumber = 1;
		ManagersController.managersAttendant = attendantsNumber ;
		CashsController.cashAttendants = attendantsNumber ;
		EletronicCashsController.eletronicCashAttendants = attendantsNumber ;
		
		
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

		int monthsNumber = 2;
		Executive.simulationEnd= monthsNumber*30*24*60;
		// simulations parameters are in Statistics.java
		
		
		Executive.addEvent(nextTimeClient, EventConstants.ARRIVE_ClIENT, firstClient, clientAttendant);
		Executive.addEvent(nextTimeCall, EventConstants.ARRIVE_CALL, firstCall, callAttendant);
		
		while(Executive.simulationTime < Executive.simulationEnd){
			Executive.timeScan();
			Executive.executeEvents();
		}
		Statistics.printStatisticsResult();
	}

}
