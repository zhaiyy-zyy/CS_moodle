package fleetmanagement.carriers;

import fleetmanagement.carriers.lorry.Lorry;

public class CarrierFactoryImpl implements ICarrierFactory {

	public Carrier createCarrier(String type, int id) {
		if ( "lorry".equalsIgnoreCase(type) ) {
			return new Lorry(id);
		} else if ( "ship".equalsIgnoreCase(type) ) {
			return new Ship(id);
		} else if ( "train".equalsIgnoreCase(type) ) {
			return new Train(id);
		}
		
		return null;
	}
}
