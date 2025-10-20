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
import fleetmanagement.carriers.Carrier;
import fleetmanagement.carriers.Lorry;
import fleetmanagement.customer.Customer;
import fleetmanagement.employees.Employee;

public class DataManager {
	
	private List<Carrier> carriers;
	private List<Cargo> cargos;
	private List<Employee> employees;
	private List<Customer> customers;
	
	public DataManager(){
		this.carriers=new ArrayList<Carrier>();
		this.cargos=new ArrayList<Cargo>();
		this.employees=new ArrayList<Employee>();
		this.customers=new ArrayList<Customer>();
	}
	
	public void loadData() throws IOException {
		this.loadCarriers();
	}
	
	public void addCarrier(Carrier carrier){
		this.carriers.add(carrier);
	}
	
	public void addCargo(Cargo cargo){
		this.cargos.add(cargo);
	}

	public void addEmployees(Employee employee){
		this.employees.add(employee);
	}

	public void addCustomers(Customer customer){
		this.customers.add(customer);
	}
	
	public List<Carrier> getCarriers() {
		return Collections.unmodifiableList( this.carriers );
	}
	
	private void loadCarriers() throws IOException {
		// You might want to try out adding a MySQL database here
		System.out.println("Read from (database)");
		File inputFile = new File("carriers.txt");
		
		// NOTE: using try-with construct of java: will close the input-streams automatically IN EVERY CASE
		try ( FileInputStream is = new FileInputStream(inputFile);
				BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

			String line;
			
			while ((line = reader.readLine()) != null) {
				String[] token = line.split(" ");
				if ( 2 != token.length )
					continue;
				
				String carrierType = token[0];
				int carrierId = Integer.parseInt(token[1]);
				
				if (carrierType.equals("Lorry")){
					Lorry lorry = new Lorry(carrierId);
					this.carriers.add(lorry);
					
					System.out.println("Loaded Lorry " + lorry.getId() );
				}
			}
		}
	}
}
