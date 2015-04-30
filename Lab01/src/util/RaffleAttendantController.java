package util;

import attendantsPackage.AttendantController;
import attendantsPackage.CashsController;
import attendantsPackage.EletronicCashsController;
import attendantsPackage.ManagersController;


public class RaffleAttendantController {
	static final String managerController = "ManagerController", cashController = "CashController" , eletronicCashController = "EletronicCashController";
	
	static ManagersController managersController = new ManagersController();
	static CashsController cashsController = new CashsController();
	static EletronicCashsController eletronicCashsController = new EletronicCashsController();
	
	static double[] anyAttendantIntervals = {0.1, 0.3 , 1};
	static String[] anyAttendants = {managerController, cashController, eletronicCashController};
	
	static double[] secondAttendantIntervals = {0.15, 0.3 , 1};
	static String[] secondAttendants = {cashController, managerController, "nobody"};
	
	
	
	public static AttendantController getClientAnyAttendent(double random){
		return getAttendantController(anyAttendantIntervals, anyAttendants , random);
		
	}
	// the second attendant - the case where the client passed in the 
	public static AttendantController getClientSecondAttendent(double random){
		return getAttendantController(secondAttendantIntervals, secondAttendants, random);
		
	}
	
	public static AttendantController getManagerController(){
		return managersController;
	}
	
	static private AttendantController getAttendantController(double[] intervals, String[] attendants, double random){
		String name = managerController;
		for (int i = 0 ; i < intervals.length ; i++){
			if(random <= intervals[i]){
				name = attendants[i];
				break;
			}
		}
		switch(name){
		case managerController : return managersController;
		case cashController : return cashsController;
		case eletronicCashController: return eletronicCashsController;
		default: return null;
		}
	}
	


}
