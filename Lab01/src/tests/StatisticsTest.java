package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import util.Statistics;

public class StatisticsTest {

	@Test
	public void test() {
		for(int i = 0 ; i < 10 ;i++){
			System.out.println("cliente arrival: " + Statistics.getNextClientArrival(0));
			System.out.println("call Arrival: " + Statistics.getNextCallArrival(0));
		}
		
	}

}
