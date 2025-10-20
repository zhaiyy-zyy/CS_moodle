public class Fluid extends Cargo {

	public Fluid(int rfid) {
		super(rfid);
	}

	public String getLocation(){
		return "Fluid.getLocation()";
	}

	@Override
	public void load() {
	}

	@Override
	public void unload() {
	}
}
