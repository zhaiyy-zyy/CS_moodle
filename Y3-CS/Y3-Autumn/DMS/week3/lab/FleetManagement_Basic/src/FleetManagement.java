import java.io.IOException;
import java.util.List;

import fleetmanagement.carriers.Carrier;
import fleetmanagement.carriers.Lorry;
import fleetmanagement.carriers.Ship;
import fleetmanagement.carriers.LorryState;
import fleetmanagement.carriers.Train;
import fleetmanagement.db.DataManager;
import fleetmanagement.route.Route;
import fleetmanagement.route.RouteNode;
import fleetmanagement.route.RoutePlanner;

public class FleetManagement {
	
	private DataManager databaseManager;
	private RoutePlanner routePlanner;

	public FleetManagement() {
	}
	
	public void run() throws IOException {
		this.databaseManager.loadData();
		
		this.initCarriers();
		this.initRouteplaner();
		
		this.planRoutes();
	}
	
	private void planRoutes() {
		RouteNode from = this.routePlanner.getRouteNodeByLocation("A");
		RouteNode to = this.routePlanner.getRouteNodeByLocation("E");
		
		Route route = this.routePlanner.planRoute(from, to);

		RouteNode additionalNode = this.routePlanner.getRouteNodeByLocation("C");
		
		// NOTE: will notify route-planer that route has changed
		route.addNode(additionalNode);
	}

	private void initCarriers() {
		List<Carrier> carriers = this.databaseManager.getCarriers();
		for (Carrier c : carriers) {
			// NOTE: this is VERY bad style and NOT OO, use visitor instead: double dynamic binding
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
	
	private void initRouteplaner() {
		this.routePlanner.add( new RouteNode("A"));
		this.routePlanner.add( new RouteNode("B"));
		this.routePlanner.add( new RouteNode("C"));
		this.routePlanner.add( new RouteNode("D"));
		this.routePlanner.add( new RouteNode("E"));
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
