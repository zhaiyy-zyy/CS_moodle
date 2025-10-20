package fleetmanagement.carriers;

import fleetmanagement.cargo.Cargo;

public class Train extends Carrier {

	public Train(int id) {
		super(id);
	}
	
	public String getLocation(){
		return "Train.getLocation()";
	}

	@Override
	public void add(Cargo c) {
	}
}
