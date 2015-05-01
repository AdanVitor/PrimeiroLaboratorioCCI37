package attendantsPackage;

import java.util.ArrayList;

import util.Statistics;
import entities.Call;
import entities.Client;


public abstract class AttendantController {

	public String name;
	public int attendantsNumber;
	public ArrayList<Attendant> attendants = new ArrayList<Attendant>();
	public ArrayList<Client> clientQueue = new ArrayList<Client>();
	public ArrayList<Call> callQueue = new ArrayList<Call>();
	
	Attendant freeAttendant ;
	public boolean isFree(){
		for(Attendant attendant : attendants ){
			if(attendant.isFree){
				freeAttendant = attendant;
				return true;
			}
		}
		return false;
	}
	
	public boolean queueIsEmpty(){
		return clientQueue.isEmpty();
	}
	
	public boolean clientQueueIsEmpty(){
		return  clientQueue.isEmpty();
	}
	
	public boolean callQueueIsEmpty(){
		return callQueue.isEmpty();
	}
	
	public void markAsBusy(){
		freeAttendant.isFree = false;
		freeAttendant = null;
	}
	
	public void addInClientQueue(Client client){
		clientQueue.add(client);
	}
	
	public void addInCallQueue(Call call){
		callQueue.add(call);
	}
	
	public Client removeClientOfQueue(){
		return clientQueue.remove(0);
	}
	
	public Call removeCallOfQueue() {
		return callQueue.remove(0);
	}
	
	
	public abstract double getServiceTime();
	public int getClientQueueSize(){
		return clientQueue.size();
	}
	
	public int getCallQueueSize(){
		return callQueue.size();
	}

	public void markAsFree() {
		for(Attendant attendant : attendants ){
			if(!attendant.isFree){
				attendant.isFree = true;
				break;
			}
		}
		
	}
	boolean isToPrintLostCalls;
	
	public void removeLostCalls(double startTime) {
		double arrival = callQueue.get(0).arrivalTime;
		while(!callQueue.isEmpty() && startTime - arrival > Statistics.getMaxCallWaiting){
			printIfDebug("Tirando ligação perdida: chegada " + arrival + " atendimento: " + startTime);
			Call call = callQueue.remove(0);
			Statistics.addInLostCalls(call);
			if(!callQueueIsEmpty()){
				arrival = callQueue.get(0).arrivalTime;
			}
		}
		
	}
	
	boolean printToDebug = false;
	public void printIfDebug(String message){
		if(printToDebug){
			System.out.println(message);
		}
	}

	

	

	
	
	
	
	

}
