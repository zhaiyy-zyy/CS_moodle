package fleetmanagement.db;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fleetmanagement.cargo.Cargo;
import fleetmanagement.cargo.Fluid;
import fleetmanagement.cargo.Pallet;
import fleetmanagement.carriers.Carrier;
import fleetmanagement.carriers.ICarrierFactory;
import fleetmanagement.customer.Customer;
import fleetmanagement.employees.Clerk;
import fleetmanagement.employees.Driver;
import fleetmanagement.employees.Employee;
import fleetmanagement.employees.Manager;

public class DataManager {
	
	// NOTE: this is the singleton-instance: STATIC
	private static DataManager instance;
	
	private ICarrierFactory carrierFactory;
	
	private List<Carrier> carriers;
	private List<Cargo> cargos;
	private List<Employee> employees;
	private List<Customer> customers;
	
	public static void init(ICarrierFactory carrierFactory) {
		if ( null == DataManager.instance ) {
			new DataManager(carrierFactory);
		}
	}
	
	// NOTE: this is the singleton-access method: STATIC
	public static DataManager getInstance() {
		return DataManager.instance;
	}
	
	// NOTE: a constructor of a singleton MUST BE PRIVATE, otherwise it can be instantiated outside the control of the singleton-access method
	private DataManager(ICarrierFactory carrierFactory){
		this.carriers=new ArrayList<Carrier>();
		this.cargos=new ArrayList<Cargo>();
		this.employees=new ArrayList<Employee>();
		this.customers=new ArrayList<Customer>();
	
		this.carrierFactory = carrierFactory;
		
		// assign freshly created instance to singleton-instance
		DataManager.instance = this;
	}
	
	public void loadData() throws IOException {
		this.loadCarriers();
		this.loadCargo();
		this.loadEmployees();
		this.loadCustomers();
	}
	
	public List<Carrier> getCarriers() {
		return Collections.unmodifiableList( this.carriers );
	}
	
	public List<Cargo> getCargos() {
		return Collections.unmodifiableList( this.cargos );
	}
	
	private void loadCargo()  {
		this.cargos.add( new Fluid() );
		this.cargos.add( new Pallet() );
	}
	
	private void loadCustomers() {
		this.customers.add( new Customer() );
	}

	private void loadEmployees() {
		this.employees.add( new Driver( "Max", "C50" ) );
		this.employees.add( new Manager( "Moriz", "A10" ) );
		this.employees.add( new Clerk( "Hans", "B35" ) );
	}

	private void loadCarriers() throws IOException {
		// You might want to try out adding a MySQL database here
		System.out.println("Read from (database)");
		File inputFile = new File("carriers.txt");
		
		// NOTE: using try-with concstruct of java: will close the input-streams automatically IN EVERY CASE
		try ( FileInputStream is = new FileInputStream(inputFile);
				BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

			String line;
			
			while ((line = reader.readLine()) != null) {
				String[] token = line.split(" ");
				if ( 2 != token.length )
					continue;
				
				String carrierType = token[0];
				int carrierId = Integer.parseInt(token[1]);
				
				// NOTE PATTERN: the carrier-factory in action: discriminating between carrier-types is done in the factory
				Carrier c = carrierFactory.createCarrier( carrierType, carrierId );
				
				this.carriers.add(c);
				System.out.println("Loaded new Carrier: " + c );
			}
		}
	}
}
