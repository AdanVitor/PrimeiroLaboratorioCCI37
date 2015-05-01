package executive;

import java.util.ArrayList;





import entities.Entity;
import attendantsPackage.AttendantController;

public class Executive {
	public static ArrayList<Event> events = new ArrayList<Event>();
	public static double simulationTime = 0;
	public static double simulationEnd;
	
	
	
	public static void addEvent(double time, int eventCode, Entity entity, AttendantController attendantController){
		Event event = new Event(time,eventCode,entity,attendantController);
		addInListOrdering(event);
	}

	private static void addInListOrdering(Event event) {
		for (int i = 0 ; i < events.size() ;i++ ){
			if(events.get(i).time >= event.time){
				events.add(i, event);
				return;
			}
		}
		events.add(event);
	}
	
	public static void timeScan() {
		if(!events.isEmpty()){
			Event event = events.get(0);
			simulationTime = event.time;
		}
		
	}
	
	static boolean debug = true;
	static double Epsilon = Math.pow(10, -7);
	
	private static boolean debugPrint = false;
	
	public static void executeEvents() {
		if(debug){
			printIfDebugPrint("-------------Execute Event -------------");
		}
		while(!events.isEmpty() &&  Math.abs(events.get(0).time - simulationTime) < Epsilon){
			Event event = events.remove(0);
			boolean temp = true;
			if(temp){
				printIfDebugPrint("Atendant livre? " + event.atendantController.isFree() );
				printIfDebugPrint("*Evento : " + EventConstants.printEvent(event.eventCode) + 
						"  *Attendant:  " + event.atendantController.name + "  *Tempo Evento: " + event.time);
			}
			event.entity.executeEvent(event.eventCode, event.atendantController);
			
			if(temp){
				printIfDebugPrint("Tamanho da fila client: " + event.atendantController.getClientQueueSize());
				printIfDebugPrint("Tamanho fila de ligações: " + event.atendantController.getCallQueueSize());
				printIfDebugPrint("Cliente: chegada " + event.entity.arrivalTime +
						" inicio de serviço: " + event.entity.startTime 
						+ " fim de serviço: " + event.entity.endTime);
			}
		}
		
	}
	
	public static void printIfDebugPrint(String message){
		if(debugPrint){
			System.out.println(message);
		}
	}
	
	
}
