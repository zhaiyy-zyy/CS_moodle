package fleetmanagement.carriers.lorry;

import fleetmanagement.cargo.Cargo;

public abstract class LorryState {
	
	protected Lorry lorry;
	
	public LorryState(Lorry l) {
		this.lorry = l;
	}
	
	public void addCargo(Cargo c) {};
	
	public void fromAvailableToInUse() throws StateChangeNotSupportedException { throw new StateChangeNotSupportedException(); }
	public void fromInUseToAvailable() throws StateChangeNotSupportedException { throw new StateChangeNotSupportedException(); }
	public void fromMaintenanceToAvailable() throws StateChangeNotSupportedException { throw new StateChangeNotSupportedException(); }
	public void fromAvailableToMaintenance() throws StateChangeNotSupportedException { throw new StateChangeNotSupportedException(); }
	public void fromMaintenanceToLockedAway() throws StateChangeNotSupportedException { throw new StateChangeNotSupportedException(); }
}
