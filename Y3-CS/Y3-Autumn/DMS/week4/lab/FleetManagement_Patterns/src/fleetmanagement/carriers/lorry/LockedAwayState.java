package fleetmanagement.carriers.lorry;

public class LockedAwayState extends LorryState {

	public LockedAwayState(Lorry l) {
		super(l);
	}

	@Override
	public String toString() {
		return "LockedAwayState";
	}
}
