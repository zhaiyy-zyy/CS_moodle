package fleetmanagement.carriers.lorry;

import java.util.Date;

import fleetmanagement.cargo.Cargo;
import fleetmanagement.carriers.Carrier;

public class Lorry extends Carrier {

	private LorryState state;
	
	public Lorry(int id) {
		super(id);
		this.state = new AvailableState(this);
	}
	
	public LorryState getState() {
		return this.state;
	}
	
	public void setState(LorryState state) {
		this.state = state;
	}
	
	public void add(Cargo cargo) {
		this.state.addCargo(cargo);
	}

	void addCargoToList(Cargo cargo) {
		this.cargos.add(cargo);
	}
	
	@Override
	public Date getLastUpdate() {
		return new Date();
	}

	@Override
	public String getLocation() {
		return "GPS location";
	}

	@Override
	public String toString() {
		return "Lorry [id=" + getId() + " cargos=" + cargos + ", state=" + state + "]";
	}
}
