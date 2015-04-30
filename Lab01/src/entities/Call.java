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
		// TODO Auto-generated method stub
		
	}

	private void arriveCall(AttendantController attendantController) {
		addNewEventCallArrive();
		if(attendantController.isFree() && attendantController.callQueueIsEmpty()){
			attendantController.markAsBusy();
			scheduleEndService(attendantController);
		}
		else{
			attendantController.addInCallQueue(this);
		}
	}
	
	private void scheduleEndService(AttendantController attendantController){
		double endCallTime = Executive.simulationTime + Statistics.getCallDuration();
		this.startTime = Executive.simulationTime;
		this.endTime = endCallTime;
		Executive.addEvent(endCallTime, EventConstants.CALL_END,
				this, attendantController);
	}
	
	private void addNewEventCallArrive(){
		double nextCallTime = Statistics.getNextCallArrival(Executive.simulationTime);
		Call nextCall = new Call(nextCallTime);
		Executive.addEvent(nextCallTime, EventConstants.ARRIVE_CALL, nextCall, RaffleAttendantController.getManagerController());
	}

		
}

	

