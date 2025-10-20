import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoutePlanner {
	
	private List<RouteNode> allNodes;
	private List<Route> routes;
	
	public RoutePlanner() {
		this.routes = new ArrayList<Route>();
		this.allNodes = new ArrayList<RouteNode>();
	}
	
	public Route planRoute(RouteNode from, RouteNode to) {
		// NOTE: would check if from and to are included in allNodes
		
		// NOTE: for now just trivial route: directly from->to (route-planning is a NP-hard problem: traveling salesman!)
		Route r = new Route( this );
		r.addNode( from );
		r.addNode( to );
		
		this.routes.add( r );
		
		return r;
	}
	
	boolean routeChanged(Route r, RouteNode includeNode) {
		// NOTE: would check if r is included in routes
		// NOTE: would check if from and to are included in allNodes
		
		// TODO PATTERNS: observer-pattern goes here, notify observers
		
		return true;
	}

	//[Code Smell?]
	public void addRouteNode(RouteNode r) {
		this.allNodes.add( r );
	}
	
	public List<RouteNode> getRouteNodes() {
		return Collections.unmodifiableList( this.allNodes );
	}

	//[Code Smell?]
	public List<Route> getsomething() {
		return Collections.unmodifiableList( this.routes );
	}
}
