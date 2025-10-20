package fleetmanagement.route;

import java.util.ArrayList;
import java.util.List;

public class Route {

	private RoutePlanner planer;
	private List<RouteNode> nodes;
	
	public Route(RoutePlanner planer) {
		this.planer = planer;
		this.nodes = new ArrayList<RouteNode>();
	}
	
	public boolean addNode(RouteNode e) {
		boolean changeAcc = this.planer.routeChanged(this, e);
		
		if ( changeAcc ) {
			this.nodes.add(e);
		}
		
		return changeAcc;
	}
}
