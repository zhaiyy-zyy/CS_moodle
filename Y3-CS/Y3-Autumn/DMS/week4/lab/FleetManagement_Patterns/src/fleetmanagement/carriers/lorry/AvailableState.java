package fleetmanagement.carriers.lorry;

import fleetmanagement.cargo.Cargo;

public class AvailableState extends LorryState {
	
	public AvailableState(Lorry l) {
		super(l);
	}
	
	@Override
	public void fromAvailableToInUse() {
		System.out.println("Lorry from Available -> InUse: " + lorry );
		lorry.setState(new InUseState(lorry));
	}
	
	@Override
	public void fromAvailableToMaintenance() {
		System.out.println("Lorry from Available -> Maintenance: " + lorry );
		lorry.setState(new MaintenanceState(lorry));
	}

	@Override
	public void addCargo(Cargo c) {
		lorry.addCargoToList(c);
		System.out.println("AvailableState addCargo to lorry: " + lorry);
		
		this.fromAvailableToInUse();
	}

	@Override
	public String toString() {
		return "AvailableState";
	}
}
