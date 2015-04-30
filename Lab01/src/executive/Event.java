package executive;

import attendantsPackage.AttendantController;
import entities.Entity;

public class Event {
	public double time;  // 
	public int eventCode;
	public Entity entity; 
	public AttendantController atendantController;
	
	public Event(double time, int eventCode, Entity entity, AttendantController attendantControler){
		this.time = time;
		this.eventCode = eventCode;
		this.entity = entity;
		this.atendantController = attendantControler;
	}
}
