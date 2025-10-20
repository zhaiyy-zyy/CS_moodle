package fleetmanagement.cargo;
import java.util.Date;

import fleetmanagement.Trackable;

public abstract class Cargo implements Trackable {
	
	private RfID rfid;
	
	public Cargo(){
		this.setRfid(new RfID());
	}

	public RfID getRfid() {
		return rfid;
	}

	// is RfID really changeable?
	public void setRfid(RfID rfid) {
		this.rfid = rfid;
	}
	
	// NOTE: default-implementation
	public Date getLastUpdate(){
		return new Date();
	}
	
	public abstract void load();
	public abstract void unload();
}
