package fleetmanagement.carriers;

import fleetmanagement.cargo.Cargo;

public class Ship extends Carrier {

	public Ship(int id) {
		super(id);
	}

	public String getLocation(){
		return "Ship.getLocation()";
	}

	@Override
	public void add(Cargo c) {
	}
}
