package entities;

import util.RaffleAttendantController;
import util.Statistics;
import executive.EventConstants;
import executive.Executive;
import attendantsPackage.AttendantController;

public class Client extends Entity {
	
	public Client(double arrivalTime) {
		super(arrivalTime);
		// TODO Auto-generated constructor stub
	}

	public void executeEvent(int eventCode, AttendantController attendantController) {
		switch(eventCode){
		case EventConstants.ARRIVE_ClIENT:
			arriveClient(attendantController);
		case EventConstants.END_CLIENT_SERVICE:
			endClientService(attendantController);
			
		}
		
	}

	private void endClientService(AttendantController attendantController) {
		// TODO Auto-generated method stub
		
	}

	private void arriveClient(AttendantController attendantController) {
		addNewEventClientArrive();
		if(attendantController.isFree() && attendantController.queueIsEmpty()){
			attendantController.markAsBusy();
			scheduleEndService(attendantController);
		}
		else{
			attendantController.addInClientQueue(this);
		}
	}
	
	private void scheduleEndService(AttendantController attendantController){
		double endServiceTime = Executive.simulationTime + attendantController.getServiceTime();
		this.startTime = Executive.simulationTime;
		this.endTime = endServiceTime;
		Executive.addEvent(endServiceTime, EventConstants.END_CLIENT_SERVICE,
				this, attendantController);
	}
	
	private void addNewEventClientArrive(){
		double nextClientTime = Statistics.getNextClientArrival(Executive.simulationTime);
		Client nextClient = new Client(nextClientTime);
		AttendantController nextClientAttendantController = RaffleAttendantController.getClientAnyAttendent(Statistics.random());
		Executive.addEvent(nextClientTime, EventConstants.ARRIVE_ClIENT, nextClient, nextClientAttendantController);
	}

	

}
