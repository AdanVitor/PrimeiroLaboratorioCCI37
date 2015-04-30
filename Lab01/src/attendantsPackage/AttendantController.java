package attendantsPackage;

import java.util.ArrayList;

import entities.Client;


public abstract class AttendantController {

	public String name;
	public int attendantsNumber;
	public ArrayList<Attendant> attendants = new ArrayList<Attendant>();
	public ArrayList<Client> clientQueue = new ArrayList<Client>();
	
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
	
	public void markAsBusy(){
		freeAttendant.isFree = false;
		freeAttendant = null;
	}
	
	public void addInClientQueue(Client client){
		clientQueue.add(client);
	}
	
	
	public abstract double getServiceTime();
	public int getQueueSize(){
		return clientQueue.size();
	}
	
	
	
	

}
