package attendantsPackage;

import util.Statistics;

public class EletronicCashsController extends AttendantController {
	
	public static int eletronicCashAttendants = 2;
	// É SÓ UMA FILA PARA CADA TIPO DE ATENDIMENTO!!!!
	public EletronicCashsController(){
		
		this.name = "EletronicCashsController ";
		for(int i = 0 ; i < eletronicCashAttendants ;i++){
			this.attendants.add(new EletronicCash());
		}
	}

	@Override
	public double getServiceTime() {
		return Statistics.getEletronicCashServiceTime();
	}
	
}
