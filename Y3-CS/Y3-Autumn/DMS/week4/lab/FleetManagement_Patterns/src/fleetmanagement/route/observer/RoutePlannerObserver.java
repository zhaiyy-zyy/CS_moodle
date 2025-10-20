package fleetmanagement.route.observer;

import fleetmanagement.route.RoutePlanner;

public abstract class RoutePlannerObserver {

	protected RoutePlanner routePlanner;
	
	public RoutePlannerObserver(RoutePlanner p) {
		this.routePlanner = p;
	}
	
	public abstract void update();
}
