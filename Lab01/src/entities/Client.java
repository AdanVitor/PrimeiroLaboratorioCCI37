package entities;

import util.RaffleAttendantController;
import util.Statistics;
import executive.EventConstants;
import executive.Executive;
import attendantsPackage.AttendantController;
import attendantsPackage.CashsController;
import attendantsPackage.EletronicCashsController;

public class Client extends Entity {
	
	public Client(double arrivalTime) {
		super(arrivalTime);
		// TODO Auto-generated constructor stub
	}

	public void executeEvent(int eventCode, AttendantController attendantController) {
		switch(eventCode){
		case EventConstants.ARRIVE_ClIENT:
			arriveClient(attendantController); break;
		case EventConstants.END_CLIENT_SERVICE:
			endClientService(attendantController); break;
			
		}
		
	}

	private void endClientService(AttendantController attendantController) {
		// for statistics
		Statistics.addInClientStatistics(this);
		sortOtherDestinyToClient(attendantController);
		if(attendantController instanceof CashsController || attendantController instanceof EletronicCashsController){
			dealWithNotCalls(attendantController);
		}
		else{
			dealWithManager(attendantController);
		}
	}
	
	
	

	
	
	private void sortOtherDestinyToClient(AttendantController attendantController){
		if(attendantController instanceof EletronicCashsController){
			AttendantController nextAttendantController = RaffleAttendantController.getClientSecondAttendent(Statistics.random());
			if(nextAttendantController != null){ // in the case off null the client is away.
				this.arrivalTime = Executive.simulationTime;
				if(nextAttendantController.isFree() && nextAttendantController.queueIsEmpty()){
					
					printIfDebug("******* SORT THE OTHER DESTINY **************");
				
					nextAttendantController.markAsBusy();
					scheduleEndService(nextAttendantController , this);
				}
				else{
					nextAttendantController.addInClientQueue(this);
				}
			}
			else{
				printIfDebug("******* SORT OTHER DESTINY  NULL **************");
			
				
			}
		}
		
	}
	
	private void arriveClient(AttendantController attendantController) {
		addNewEventClientArrive();
		if(attendantController.isFree() && attendantController.queueIsEmpty()){
			attendantController.markAsBusy();
			scheduleEndService(attendantController , this);
		}
		else{
			attendantController.addInClientQueue(this);
		}
	}
	
	private void addNewEventClientArrive(){
		double nextClientTime = Statistics.getNextClientArrival(Executive.simulationTime);
		Client nextClient = new Client(nextClientTime);
		AttendantController nextClientAttendantController = RaffleAttendantController.getClientAnyAttendent(Statistics.random());
		Executive.addEvent(nextClientTime, EventConstants.ARRIVE_ClIENT, nextClient, nextClientAttendantController);
	}
	
	boolean printToDebug = false;
	public void printIfDebug(String message){
		if(printToDebug){
			System.out.println(message);
		}
	}
	
	

	

}
