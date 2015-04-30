package attendantsPackage;

import util.Statistics;


public class CashsController extends AttendantController{
	// � S� UMA FILA PARA CADA TIPO DE ATENDIMENTO!!!!
	public CashsController(){
		this.name = "CashsController ";
		this.attendantsNumber = 2;
		for(int i = 0 ; i < attendantsNumber ;i++){
			this.attendants.add(new Cash());
		}
	}

	@Override
	public double getServiceTime() {
		return Statistics.getCashServiceTime();
	}

}
