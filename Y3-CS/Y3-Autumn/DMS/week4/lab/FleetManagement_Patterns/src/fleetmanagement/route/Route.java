package fleetmanagement.route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Route {

	private RoutePlanner planer;
	private List<RouteNode> nodes;
	
	public Route(RoutePlanner planer) {
		this.planer = planer;
		this.nodes = new ArrayList<RouteNode>();
	}
	
	public List<RouteNode> getNodes() {
		return Collections.unmodifiableList(this.nodes);
	}
	
	public void addNode(RouteNode e) {
		this.nodes.add(e);
		this.planer.routeChanged(this, e);
	}
	
	@Override
	public String toString() {
		return "Route [nodes=" + nodes + "]";
	}
}
