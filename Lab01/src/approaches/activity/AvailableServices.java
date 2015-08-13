package approaches.activity;

import java.util.Arrays;

public class AvailableServices {
	
	private static boolean isUnique = true;
	
	private static AvailableServices services = null;
	private static int AVAILABLE_UNITS = 1;

	private Boolean[] paymentCashFree = new Boolean[AVAILABLE_UNITS];
	private Boolean[] playCashFree = new Boolean[AVAILABLE_UNITS];
	

	private AvailableServices() {
		if(isUnique){
			AvailableServices.AVAILABLE_UNITS = 2;
			paymentCashFree = new Boolean[AVAILABLE_UNITS];
			playCashFree = new Boolean[AVAILABLE_UNITS];
		}
		Arrays.fill(paymentCashFree, true);
		Arrays.fill(playCashFree, true);
		
	}
	
	public static AvailableServices sharedInstance() {
		if (services == null) {
			services = new AvailableServices();
		}
		
		return services;
	}
	
	
	
	public boolean isPaymentCashFree() {
		boolean freeSpot = false;
		for (int i = 0; i < paymentCashFree.length && !freeSpot; i++) {
			if (paymentCashFree[i]) {
				freeSpot = true;
			}
		}
		return freeSpot;
	}
	
	public void makeOnePaymentCashBusy() {
		for (int i = 0; i < paymentCashFree.length; i++) {
			if (paymentCashFree[i]) {
				paymentCashFree[i] = false;
				return;
			}
		}
	}

	public void freeFirstBusyPaymentCash() {
		for (int i = 0; i < paymentCashFree.length; i++) {
			if(!paymentCashFree[i]) {
				paymentCashFree[i] = true;
				return;
			}
		}
	}
	
	//------------------------------------------------------------------------------------
	
	public boolean isPlayCashFree() {
		boolean freeSpot = false;
		for (int i = 0; i < playCashFree.length && !freeSpot; i++) {
			if (playCashFree[i]) {
				freeSpot = true;
			}
		}
		return freeSpot;
	}
	
	public void makeOnePlayCashBusy() {
		for (int i = 0; i < playCashFree.length; i++) {
			if (playCashFree[i]) {
				playCashFree[i] = false;
				return;
			}
		}
	}

	public void freeFirstBusyPlayCash() {
		for (int i = 0; i < playCashFree.length; i++) {
			if(!playCashFree[i]) {
				playCashFree[i] = true;
				return;
			}
		}
	}

}
