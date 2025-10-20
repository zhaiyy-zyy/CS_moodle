package fleetmanagement.employees;

public abstract class Employee {

	private String name;
	private String office;
	
	public Employee(String name, String office) {
		this.name = name;
		this.office = office;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getOffice() {
		return this.office;
	}
}
