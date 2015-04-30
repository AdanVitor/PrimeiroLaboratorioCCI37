package tests;

import static org.junit.Assert.*;


import org.junit.Test;

import attendantsPackage.*;
import attendantsPackage.AttendantController;
import attendantsPackage.ManagersController;
import util.RaffleAttendantController;
import util.Statistics;

public class RaffleAttendantControllerTest {
	
	
	
	@Test
	public void test() {
		int managesCount = 0 , cashCount = 0 , eletronicsCount = 0;
		for (int i = 0 ; i < 4000 ; i++){
			double random = Statistics.random();
			AttendantController attendant = RaffleAttendantController.getClientAnyAttendent(random);
			if(attendant instanceof ManagersController){
				assertTrue(random <= 0.1);
				managesCount++;
			}
			if(attendant instanceof CashsController){
				assertTrue( random <= 0.3 && random > 0.1);
				cashCount++;
			}
			if(attendant instanceof EletronicCashsController){
				assertTrue( random <= 1 && random > 0.3);
				eletronicsCount++;
			}
		}
		int total = managesCount + cashCount + eletronicsCount;
		System.out.println("managesCount: " + 1.0*managesCount/total + " cashCount: " + 1.0*cashCount/total + " eletronicsCount: " + 1.0*eletronicsCount/total);
		

	}
	
	
	@Test
	public void test2() {
		int managesCount = 0 , cashCount = 0 , nullCount = 0;
		for (int i = 0 ; i < 4000 ; i++){
			double random = Statistics.random();
			AttendantController attendant = RaffleAttendantController.getClientSecondAttendent(random);
			if(attendant instanceof ManagersController){
				assertTrue(random <= 0.3 && random > 0.15);
				managesCount++;
			}
			if(attendant instanceof CashsController){
				assertTrue( random <= 0.15);
				cashCount++;
			}
			if(attendant == null){
				assertTrue( random > 0.3 && random <= 1);
				nullCount++;
			}
		}
		int total = managesCount + cashCount + nullCount;
		System.out.println("managesCountSecond: " + 1.0*managesCount/total + " cashCountSecond: " + 1.0*cashCount/total + " nullCountSecond: "
		+ 1.0*nullCount/total);
		

	}

}
