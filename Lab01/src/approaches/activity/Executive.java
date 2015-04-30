package approaches.activity;

import java.util.TreeMap;

public class Executive {
	private static final double END_SIMULATION_TIME = 24 * 60 * 60 * 30 * 2;  // Months in seconds.
	                                            //    /    |     |    \    \
	                                            // hours  mins  secs  days  months
	private static Executive executive = null;
	
	private TreeMap<Double, Activity> activities = null;
	private double maxTime = 0;
	
	private Executive() {
		activities = new TreeMap<>();
	}

	public static Executive sharedInstance() {
		if (executive == null) {
			executive = new Executive();
		}
		return executive;
	}
	
	public double simulationTime() {
		return (double) activities.firstKey();
	}
	
	public boolean isTimeFinished() {
		return maxTime > END_SIMULATION_TIME;
	}
	
	public Activity getActivity() {
		return (Activity) activities.firstEntry().getValue();
	}

	public void removeActivity() {
		activities.pollFirstEntry();
	}

	public void addActivity(Activity activity) {
		double activityTime = activity.getTime();
		if (activityTime > maxTime) {
			maxTime = activityTime;
		}
		activities.put(activityTime, activity);
	}
}
