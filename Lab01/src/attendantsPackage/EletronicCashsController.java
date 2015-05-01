package attendantsPackage;

import util.Statistics;

public class EletronicCashsController extends AttendantController {
	
	// É SÓ UMA FILA PARA CADA TIPO DE ATENDIMENTO!!!!
	public EletronicCashsController(){
		this.name = "EletronicCashsController ";
		this.attendantsNumber = 1;
		for(int i = 0 ; i < attendantsNumber ;i++){
			this.attendants.add(new EletronicCash());
		}
	}

	@Override
	public double getServiceTime() {
		return Statistics.getEletronicCashServiceTime();
	}
	
}
