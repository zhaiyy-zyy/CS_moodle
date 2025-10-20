import java.io.IOException;
import java.util.List;

import fleetmanagement.cargo.Cargo;
import fleetmanagement.carriers.Carrier;
import fleetmanagement.carriers.CarrierFactoryImpl;
import fleetmanagement.carriers.Ship;
import fleetmanagement.carriers.Train;
import fleetmanagement.carriers.lorry.Lorry;
import fleetmanagement.db.DataManager;
import fleetmanagement.route.Route;
import fleetmanagement.route.RouteNode;
import fleetmanagement.route.RoutePlanner;
import fleetmanagement.route.observer.impl.RoutePlannerMonitor;

public class FleetManagement {
	
	// NOTE: removed due to Singleton-Pattern
	// private DatabaseManager databaseManager;
	private RoutePlanner routePlanner;

	public FleetManagement() {
	}
	
	public void run() throws IOException {
		DataManager.init( new CarrierFactoryImpl() );
		DataManager.getInstance().loadData();
		
		this.initCarriers();
		this.initRouteplaner();
		
		this.planRoutes();
	}
	
	private void planRoutes() {
		RouteNode from = this.routePlanner.getRouteNodeByLocation("A");
		RouteNode to = this.routePlanner.getRouteNodeByLocation("E");
		
		Route route = this.routePlanner.planRoute(from, to);
		RouteNode additionalNode = this.routePlanner.add( new RouteNode("X"));
		route.addNode(additionalNode);
	}

	private void initCarriers() {
		List<Cargo> cargos = DataManager.getInstance().getCargos();
		
		List<Carrier> carriers = DataManager.getInstance().getCarriers();
		for (Carrier c : carriers) {
			// NOTE: this is VERY bad style and NOT OO, may use VISITOR-PATTERN instead: double dynamic binding
			if ( c instanceof Lorry ) {
				Lorry lorry = (Lorry) c;
				lorry.add( cargos.get( 0 ) );  // NOTE: this will result in a state-change to IN-USE
				
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
		
		this.routePlanner.addObserver( new RoutePlannerMonitor(this.routePlanner) );
	}
	
	public static void main(String[] args) throws IOException {
		FleetManagement fm = constructFleetManagement();
		fm.run();
	}
	
	private static FleetManagement constructFleetManagement() {
		FleetManagement fm = new FleetManagement();
		
		// NOTE: removed due to Singleton-Pattern
		// DatabaseManager dbm = new DatabaseManager();
		RoutePlanner rp = new RoutePlanner();
		
		// NOTE: removed due to Singleton-Pattern
		// fm.databaseManager = dbm;
		fm.routePlanner = rp;
		
		return fm;
	}
}
