package fleetmanagement.carriers;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fleetmanagement.Trackable;
import fleetmanagement.cargo.Cargo;

public abstract class Carrier implements Trackable {

	private int id;
	
	protected List<Cargo> cargos;
	
	public Carrier(int id) {
		this.id = id;
		this.cargos = new ArrayList<Cargo>();
	}
	
	public int getId() {
		return this.id;
	}
	
	// NOTE: default-implementation
	public Date getLastUpdate(){
		return new Date();
	}
	
	public abstract void add(Cargo c);
}
