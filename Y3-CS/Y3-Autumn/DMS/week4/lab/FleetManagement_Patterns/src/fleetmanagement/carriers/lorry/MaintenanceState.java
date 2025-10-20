package fleetmanagement.carriers.lorry;

public class MaintenanceState extends LorryState {

	public MaintenanceState(Lorry l) {
		super(l);
	}
	
	@Override
	public void fromMaintenanceToAvailable() {
		System.out.println(lorry.toString()+" moving fromMaintenanceToAvailable");
		lorry.setState(new AvailableState(lorry));
	}
	
	@Override
	public void fromMaintenanceToLockedAway() {
		System.out.println(lorry.toString()+" moving fromMaintenanceToLockedAway");
		lorry.setState(new LockedAwayState(lorry));
	}

	@Override
	public String toString() {
		return "MaintenanceState";
	}
}
