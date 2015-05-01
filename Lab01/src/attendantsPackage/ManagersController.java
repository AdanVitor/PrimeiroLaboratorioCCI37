package attendantsPackage;

import java.util.ArrayList;

import util.Statistics;
import entities.Call;



public class ManagersController extends AttendantController{
	
	public ArrayList<Call> callQueue = new ArrayList<Call>();
	
	public ManagersController(){
		this.name = "Managers Controller";
		this.attendantsNumber = 1;
		for(int i = 0 ; i < attendantsNumber ;i++){
			this.attendants.add(new Manager());
		}
	}
	
	@Override
	public boolean queueIsEmpty(){
		return clientQueue.isEmpty() && callQueue.isEmpty();
	}
	

	@Override
	public double getServiceTime() {
		return Statistics.getManageServiceTime();
	}
	
	public int getQueueSize(){
		return clientQueue.size() + callQueue.size();
	}
	
	
	
	// É SÓ UMA FILA PARA CADA TIPO DE ATENDIMENTO!!!!
	
}
