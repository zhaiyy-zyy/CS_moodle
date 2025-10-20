package fleetmanagement.route.observer.impl;

import java.util.List;

import fleetmanagement.route.Route;
import fleetmanagement.route.RouteNode;
import fleetmanagement.route.RoutePlanner;
import fleetmanagement.route.observer.RoutePlannerObserver;

public class RoutePlannerMonitor extends RoutePlannerObserver {

	public RoutePlannerMonitor(RoutePlanner p) {
		super(p);
	}

	@Override
	public void update() {
		System.out.println("----------- RoutePlannerMonitor Update -----------");
		System.out.println("	RoutePlanner has changed");
		
		List<RouteNode> nodes = this.routePlanner.getRouteNodes();
		List<Route> routes = this.routePlanner.getRoutes();
		
		System.out.println("	Current Nodes: ");
		for (RouteNode n : nodes) {
			System.out.println(n);
		}
		
		System.out.println("	Current Routes: ");
		for (Route r : routes) {
			System.out.println(r);
		}
	}
}
