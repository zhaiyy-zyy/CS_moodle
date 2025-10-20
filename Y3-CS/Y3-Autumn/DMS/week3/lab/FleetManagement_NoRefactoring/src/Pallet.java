public class Pallet extends Cargo {

	public Pallet(int rfid) {
		super(rfid);
	}

	public String getLocation(){
		return "Pallet.getLocation()";
	}
	
	@Override
	public void load() {
	}

	@Override
	public void unload() {
	}
}
