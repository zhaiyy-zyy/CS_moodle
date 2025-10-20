import java.util.Date;

public class Lorry extends Carrier {
	
	private LorryState state;
	
	public Lorry(int id) {
		super(id);
		this.state = LorryState.AVAILABLE;
	}
	
	public boolean setState(LorryState state) {
		// TODO PATTERNS: state-pattern goes here
		
		boolean success = false;
		System.out.print("setting state ... ");
		if (this.state == state) {
		} else {
			switch (this.state) {
			case AVAILABLE:
				if (state == LorryState.LOCKEDAWAY) {
					System.out.println("failed");
					return success;
				}
				break;
			case INUSE:
				if (state == LorryState.LOCKEDAWAY || state == LorryState.MAINTENANCE) {
					System.out.println("failed");
					return success;
				}
				break;
			case MAINTENANCE:
				if (state == LorryState.INUSE) {
					System.out.println("failed");
					return success;
				}
				break;
			case LOCKEDAWAY:
				if (state == LorryState.INUSE || state == LorryState.AVAILABLE) {
					System.out.println("failed");
					return success;
				}
			}
			this.state = state;
			success = true;
		}
		System.out.println(state.toString());
		return success;
	}
	
	public void add(Cargo cargo) {
		if (this.state == LorryState.AVAILABLE) {
			this.cargos.add(cargo);
			System.out.print("Lorry " + this.getId() + ": add Cargo " + cargo.getRfid() + " (" + cargo.getClass() + ") ");
			this.setState(LorryState.INUSE);
		}
	}

	@Override
	public Date getLastUpdate() {
		return new Date();
	}

	@Override
	public String getLocation() {
		return "GPS location";
	}
}
