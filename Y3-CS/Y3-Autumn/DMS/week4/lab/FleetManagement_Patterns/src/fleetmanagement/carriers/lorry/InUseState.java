package fleetmanagement.carriers.lorry;

public class InUseState extends LorryState {

	public InUseState(Lorry l) {
		super(l);
	}

	@Override
	public void fromInUseToAvailable() {
		System.out.println(lorry.toString()+" moving fromInUseToAvailable");
		lorry.setState(new AvailableState(lorry));
	}

	@Override
	public String toString() {
		return "InUseState";
	}
}
