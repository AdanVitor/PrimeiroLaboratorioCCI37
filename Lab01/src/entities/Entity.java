package entities;



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
	
	

}
