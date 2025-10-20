import java.util.Date;

public abstract class Cargo implements Trackable {

	//[Code Smell?]
	private int rfid;
	
	public Cargo(int rfid){
		this.setRfid(rfid);
	}

	public int getRfid() {
		return rfid;
	}

	// is RfID really changeable?
	public void setRfid(int rfid) {
		this.rfid = rfid;
	}
	
	// NOTE: default-implementation
	public Date getLastUpdate(){
		return new Date();
	}
	
	public abstract void load();
	public abstract void unload();
}
