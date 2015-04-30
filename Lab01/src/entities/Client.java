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
	
	private void dealWithNotCalls(AttendantController attendantController) {
		System.out.println("**** Deal WithNotCalls ***** ");
		if(!attendantController.clientQueueIsEmpty()){
			System.out.println("***** Liberando da fila - fila anterior " + attendantController.getClientQueueSize() +  " ********");
			Client client = attendantController.removeClientOfQueue();
			scheduleEndService(attendantController , client);
		}
		else{
			System.out.println("Tamanho da fila: " + attendantController.getClientQueueSize());
			System.out.println("****** Marcando attendente como livre ******");
			attendantController.markAsFree();
		}
		
	}
	
	private void dealWithManager(AttendantController attendantController){
		
	}







	boolean debug = true;
	
	
	
	
	private void sortOtherDestinyToClient(AttendantController attendantController){
		if(attendantController instanceof EletronicCashsController){
			AttendantController nextAttendantController = RaffleAttendantController.getClientSecondAttendent(Statistics.random());
			if(nextAttendantController != null){ // in the case off null the client is away.
				this.arrivalTime = Executive.simulationTime;
				if(nextAttendantController.isFree() && nextAttendantController.queueIsEmpty()){
					if(debug){
						System.out.println("******* SORT THE OTHER DESTINY **************");
					}
					nextAttendantController.markAsBusy();
					scheduleEndService(nextAttendantController , this);
				}
				else{
					nextAttendantController.addInClientQueue(this);
				}
			}
			else{
				if(debug){
					System.out.println("******* SORT OTHER DESTINY  NULL **************");
				}
				
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
	
	private void scheduleEndService(AttendantController attendantController, Client client){
		double endServiceTime = Executive.simulationTime + attendantController.getServiceTime();
		this.startTime = Executive.simulationTime;
		this.endTime = endServiceTime;
		Executive.addEvent(endServiceTime, EventConstants.END_CLIENT_SERVICE,
				client, attendantController);
	}
	
	private void addNewEventClientArrive(){
		double nextClientTime = Statistics.getNextClientArrival(Executive.simulationTime);
		Client nextClient = new Client(nextClientTime);
		AttendantController nextClientAttendantController = RaffleAttendantController.getClientAnyAttendent(Statistics.random());
		Executive.addEvent(nextClientTime, EventConstants.ARRIVE_ClIENT, nextClient, nextClientAttendantController);
	}

	

}
