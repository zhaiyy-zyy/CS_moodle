import java.io.IOException;
import java.util.List;

public class FleetManagement {
	
	private DataManager databaseManager;
	private RoutePlanner routePlanner;

	public FleetManagement() {
	}
	
	public void run() throws IOException {
		this.databaseManager.loadData();
		
		this.initCarriers();

		//initialize route planner [Code Smell?]
		this.routePlanner.addRouteNode( new RouteNode("A"));
		this.routePlanner.addRouteNode( new RouteNode("B"));
		this.routePlanner.addRouteNode( new RouteNode("C"));
		this.routePlanner.addRouteNode( new RouteNode("D"));
		this.routePlanner.addRouteNode( new RouteNode("E"));

		//plan routes [Code Smell?]
		RouteNode from = null;
		for (RouteNode n : this.routePlanner.getRouteNodes()){
			if (n.getLocation().equals("A")){
				from = n;
				break;
			}
		}

		RouteNode to = null;
		for (RouteNode n : this.routePlanner.getRouteNodes()){
			if (n.getLocation().equals("E")){
				to = n;
				break;
			}
		}

		Route route = this.routePlanner.planRoute(from, to);
		RouteNode additionalNode = null;
		for (RouteNode n : this.routePlanner.getRouteNodes()){
			if (n.getLocation().equals("E")){
				additionalNode = n;
				break;
			}
		}
		// NOTE: will notify route-planer that route has changed
		route.addNode(additionalNode);

	}

	private void initCarriers() {
		List<Carrier> carriers = this.databaseManager.getCarriers();
		for (Carrier c : carriers) {
			// NOTE: this is VERY bad style and NOT OO, use visitor pattern instead: double dynamic binding
			if ( c instanceof Lorry ) {
				Lorry lorry = (Lorry) c;
				lorry.setState( LorryState.MAINTENANCE );
				
			} else if ( c instanceof Ship ) {
				// TODO: do Ship-specific stuff
				
			} else if ( c instanceof Train ) {
				// TODO: do Train-specific stuff
			}	
		}
	}

	public static void main(String[] args) throws IOException {
		FleetManagement fm = constructFleetManagement();
		fm.run();
	}
	
	private static FleetManagement constructFleetManagement() {
		FleetManagement fm = new FleetManagement();
		
		DataManager dbm = new DataManager();
		RoutePlanner rp = new RoutePlanner();
		
		fm.databaseManager = dbm;
		fm.routePlanner = rp;
		
		return fm;
	}
}
