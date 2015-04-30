package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import util.RaffleAttendantController;
import util.Statistics;
import attendantsPackage.AttendantController;
import entities.Call;
import entities.Client;
import executive.*;

public class ExecutiveTest {
	
	
	@Before
	public void initialSetup(){
		Executive.simulationEnd = 500;
		assertTrue(Executive.simulationEnd == 500 && Executive.simulationTime == 0);
	}
	

	@Test
	public void test() {
		
		Client firstClient = new Client(0);
		// ruffle the attendant of the Client
		AttendantController clientAttendant = RaffleAttendantController
				.getClientAnyAttendent(Statistics.random());

		Call firstCall = new Call(0);
		// returning the managerController
		AttendantController callAttendant = RaffleAttendantController
				.getManagerController();

		Executive.addEvent(0,EventConstants.ARRIVE_ClIENT, firstClient, clientAttendant);
		Executive.addEvent(4,EventConstants.ARRIVE_CALL, firstCall, callAttendant);
		Executive.addEvent(0,EventConstants.ARRIVE_ClIENT, firstClient, clientAttendant);
		Executive.addEvent(5,EventConstants.ARRIVE_CALL, firstCall, callAttendant);
		Executive.addEvent(5, EventConstants.ARRIVE_ClIENT, firstClient, clientAttendant);
		Executive.addEvent(1, EventConstants.ARRIVE_ClIENT, firstClient, clientAttendant);
		ArrayList<Event> events = Executive.events;
		assertTrue(events.size() == 6);
		for(int i = 0 ; i < events.size() - 2 ;i++){
			assertTrue(events.get(i).time <= events.get(i+1).time);
		}
		System.out.println("-----------------");
		Executive.timeScan();
		Executive.executeEvents();
		System.out.println("-----------------");
		Executive.timeScan();
		Executive.executeEvents();
		System.out.println("-----------------");
		Executive.timeScan();
		Executive.executeEvents();
		System.out.println("-----------------");
		Executive.timeScan();
		Executive.executeEvents();
		System.out.println("-----------------");
		Executive.timeScan();
		Executive.executeEvents();
		System.out.println("-----------------");
		
	}

}
