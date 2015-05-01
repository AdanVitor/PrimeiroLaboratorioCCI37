package entities;



import util.Statistics;
import executive.EventConstants;
import executive.Executive;
import attendantsPackage.AttendantController;

public class Entity {
	
	public double arrivalTime = 0 ;
	public double startTime = 0;
	public double endTime = 0 ;
	public Entity (double arrivalTime){
		this.arrivalTime = arrivalTime;
	}

	public void executeEvent(int eventCode, AttendantController atendantController) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean debugPrint = false;
	
	
	public void scheduleEndCall(AttendantController attendantController,
			Call call) {
		double endCallTime = Executive.simulationTime + Statistics.getCallDuration();
		call.startTime = Executive.simulationTime;
		call.endTime = endCallTime;
		Executive.addEvent(endCallTime, EventConstants.CALL_END, call, attendantController);
	}
	
	public void dealWithManager(AttendantController attendantController){
		printIfDebugPrint("Lidando com gerente");
		if(attendantController.callQueueIsEmpty()){
			printIfDebugPrint("Fila de ligações vazia");
			dealWithNotCalls(attendantController);
		}
		else{
			dealWithCalls(attendantController);
		}
	}
	
	
	public void dealWithCalls(AttendantController attendantController) {
		printIfDebugPrint("Fila de ligações não vazia - tamanho atual: " + attendantController.getCallQueueSize());
		attendantController.removeLostCalls(Executive.simulationTime);
		printIfDebugPrint("Fila de ligações depois de retirar as perdidas - tamanho: " + attendantController.getCallQueueSize());
		if(attendantController.callQueueIsEmpty()){
			printIfDebugPrint("Indo para a fila de clientes");
			dealWithNotCalls(attendantController);
		}
		else{
		    printIfDebugPrint("Marcando fim de ligação: tamanho anterior da fila de ligação: " + attendantController.getCallQueueSize());
		    Call call = attendantController.removeCallOfQueue();
		    scheduleEndCall(attendantController, call);
		    printIfDebugPrint("Novo tamanho da fila de ligação: " + attendantController.getCallQueueSize());
		    printIfDebugPrint("Gerente tem que continuar ocupado: free? " + attendantController.isFree());
		}
	}
	
	public void dealWithNotCalls(AttendantController attendantController) {
		printIfDebugPrint("**** Deal WithNotCalls ***** ");
		if(!attendantController.clientQueueIsEmpty()){
			printIfDebugPrint("***** Liberando da fila de cliente - fila anterior " + attendantController.getClientQueueSize() +  " ********");
			Client client = attendantController.removeClientOfQueue();
			scheduleEndService(attendantController , client);
		}
		else{
			printIfDebugPrint("Tamanho da fila: " + attendantController.getClientQueueSize());
			printIfDebugPrint("****** Marcando attendente como livre ******");
			attendantController.markAsFree();
		}
	}
	
	public void scheduleEndService(AttendantController attendantController, Client client){
		double endServiceTime = Executive.simulationTime + attendantController.getServiceTime();
		client.startTime = Executive.simulationTime;
		client.endTime = endServiceTime;
		Executive.addEvent(endServiceTime, EventConstants.END_CLIENT_SERVICE,
				client, attendantController);
	}
	
	public void printIfDebugPrint(String message){
		if(debugPrint){
			System.out.println(message);
		}
	}
	

}
