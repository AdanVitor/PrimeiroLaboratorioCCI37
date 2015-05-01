package entities;

import util.RaffleAttendantController;
import util.Statistics;
import attendantsPackage.AttendantController;
import executive.EventConstants;
import executive.Executive;

public class Call extends Entity {

	public Call(double arrivalTime) {
		super(arrivalTime);
		
	}
	
	public void executeEvent(int eventCode, AttendantController attendantController) {
		switch(eventCode){
		case EventConstants.ARRIVE_CALL:
			arriveCall(attendantController); break;
		case EventConstants.CALL_END:
			endCall(attendantController); break;
			
		}
		
	}

	private void endCall(AttendantController attendantController) {
		Statistics.addInCallStatistics(this);
		dealWithManager(attendantController);
	}

	private void arriveCall(AttendantController attendantController) {
		addNewEventCallArrive();
		if(attendantController.isFree() && attendantController.callQueueIsEmpty()){
			attendantController.markAsBusy();
			scheduleEndCall(attendantController, this);
		}
		else{
			attendantController.addInCallQueue(this);
		}
	}
	
	
	private void addNewEventCallArrive(){
		double nextCallTime = Statistics.getNextCallArrival(Executive.simulationTime);
		Call nextCall = new Call(nextCallTime);
		Executive.addEvent(nextCallTime, EventConstants.ARRIVE_CALL, nextCall, RaffleAttendantController.getManagerController());
	}

		
}

	

