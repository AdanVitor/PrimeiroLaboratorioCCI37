package executive;

public class EventConstants {
	public static final int ARRIVE_ClIENT = 0;
	public static final int END_CLIENT_SERVICE = 1;
	public static final int ARRIVE_CALL = 2;
	public static final int CALL_END = 3;
	public static String printEvent(int eventCode) {
		String print = "";
		switch(eventCode){
		case ARRIVE_ClIENT: print = "Arrive client"; break;
		case END_CLIENT_SERVICE: print = "End client service"; break;
		case ARRIVE_CALL: print = "Arrive call"; break;
		case CALL_END: print = "Call End"; break;
		}
		return print;
	}
	
	

}
