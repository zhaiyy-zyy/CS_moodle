package fleetmanagement.route;

public class RouteNode {
	
	private String location;
	
	public RouteNode(String location) {
		this.location = location;
	}
	
	public String getLocation() {
		return this.location;
	}

	@Override
	public String toString() {
		return "RouteNode [location=" + location + "]";
	}
}
