package attendantsPackage;

import util.Statistics;


public class CashsController extends AttendantController{
	// É SÓ UMA FILA PARA CADA TIPO DE ATENDIMENTO!!!!
	public static int cashAttendants = 2;
	
	public CashsController(){
		this.name = "CashsController ";
		for(int i = 0 ; i < cashAttendants ;i++){
			this.attendants.add(new Cash());
		}
	}

	@Override
	public double getServiceTime() {
		return Statistics.getCashServiceTime();
	}

}
