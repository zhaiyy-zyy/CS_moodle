package fleetmanagement.carriers;

public interface ICarrierFactory {

	public Carrier createCarrier(String type, int id);
}
