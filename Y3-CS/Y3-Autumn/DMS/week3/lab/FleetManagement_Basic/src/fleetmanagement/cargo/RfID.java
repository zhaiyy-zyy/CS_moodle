package fleetmanagement.cargo;

public class RfID {

	private static int nextId = 0;
	
	private int id;
	
	public RfID() {
		this.id = RfID.nextId++;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "RfID [id=" + id + "]";
	}
}
