package fleetmanagement.route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fleetmanagement.route.observer.RoutePlannerObserver;

public class RoutePlanner {
	
	private List<RouteNode> allNodes;
	private List<Route> routes;
	
	private List<RoutePlannerObserver> observers;
	
	public RoutePlanner() {
		this.routes = new ArrayList<Route>();
		this.allNodes = new ArrayList<RouteNode>();
		
		this.observers = new ArrayList<RoutePlannerObserver>();
	}
	
	public Route planRoute(RouteNode from, RouteNode to) {
		// NOTE: would check if from and to are included in allNodes
		
		// NOTE: for now just trivial route: directly from->to (route-planning is a NP-hard problem: traveling salesman!)
		Route r = new Route( this );
		r.addNode( from );
		r.addNode( to );
		
		this.routes.add( r );
		
		// NOTE PATTERNS: observer-pattern in action, notify observers
		this.notifyObserver();
				
		return r;
	}

	public void addObserver(RoutePlannerObserver o) {
		this.observers.add(o);
	}
	
	public void deleteObserver(RoutePlannerObserver o) {
		this.observers.remove(o);
	}
	
	public void notifyObserver() {
		for (RoutePlannerObserver o : this.observers ) {
			o.update();
		}
	}
	
	void routeChanged(Route r, RouteNode includeNode) {
		// NOTE: check if route is already added to routes - then notify observers
		if ( this.routes.contains(r) ) {
			// NOTE PATTERNS: observer-pattern in action, notify observers
			this.notifyObserver();
		}
	}
	
	public RouteNode getRouteNodeByLocation(String loc) {
		for ( RouteNode n : this.allNodes) {
			if ( n.getLocation().equals(loc)) {
				return n;
			}
		}

		return null;
	}
	
	public RouteNode add(RouteNode r) {
		this.allNodes.add( r );
		
		// NOTE PATTERNS: observer-pattern in action, notify observers
		this.notifyObserver();
		
		return r;
	}
	
	public List<RouteNode> getRouteNodes() {
		return Collections.unmodifiableList( this.allNodes );
	}
	
	public List<Route> getRoutes() {
		return Collections.unmodifiableList( this.routes );
	}
}
