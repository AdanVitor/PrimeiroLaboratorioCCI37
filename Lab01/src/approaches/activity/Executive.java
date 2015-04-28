package approaches.activity;

import java.util.TreeMap;

public class Executive {
	
	private static Executive executive = null;
	
	private TreeMap<Double, Activity> activities = null;
	
	private Executive() {
		activities = new TreeMap<Double, Activity>();
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
		return activities.isEmpty();
	}
	
	public Activity getActivity() {
		return (Activity) activities.firstEntry().getValue();
	}

	public void removeActivity() {
		activities.pollFirstEntry();
	}

	public void addActivity(Activity activity) {
		activities.put(activity.getTime(), activity);
	}
}
