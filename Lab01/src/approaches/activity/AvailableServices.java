package approaches.activity;

import java.util.Arrays;

public class AvailableServices {
	private static AvailableServices services = null;
	private final int AVAILABLE_UNITS = 2;

	private Boolean[] atmFree = new Boolean[AVAILABLE_UNITS];
	private Boolean[] cashierFree = new Boolean[AVAILABLE_UNITS];
	private Boolean[] managerFree = new Boolean[AVAILABLE_UNITS];

	private AvailableServices() {
		Arrays.fill(atmFree, true);
		Arrays.fill(cashierFree, true);
		Arrays.fill(managerFree, true);
	}
	
	public static AvailableServices sharedInstance() {
		if (services == null) {
			services = new AvailableServices();
		}
		return services;
	}
	
	public boolean isATMFree() {
		boolean freeSpot = false;
		for (int i = 0; i < atmFree.length && !freeSpot; i++) {
			if (atmFree[i]) {
				freeSpot = true;
			}
		}
		return freeSpot;
	}
	
	public void makeOneATMBusy() {
		for (int i = 0; i < atmFree.length; i++) {
			if (atmFree[i]) {
				atmFree[i] = false;
				return;
			}
		}
	}

	public void freeFirstBusyATM() {
		for (int i = 0; i < atmFree.length; i++) {
			if(!atmFree[i]) {
				atmFree[i] = true;
				return;
			}
		}
	}
	
	public boolean isCashierFree() {
		boolean freeSpot = false;
		for (int i = 0; i < cashierFree.length && !freeSpot; i++) {
			if (cashierFree[i]) {
				freeSpot = true;
			}
		}
		return freeSpot;
	}
	
	public boolean isManagerFree() {
		boolean freeSpot = false;
		for (int i = 0; i < managerFree.length && !freeSpot; i++) {
			if (managerFree[i]) {
				freeSpot = true;
			}
		}
		return freeSpot;
	}
}
