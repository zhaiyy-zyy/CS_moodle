package com.siebers.ZooProject.Main;

public abstract class Employee {
	private String name;
	private double salary;
	
	public Employee(String name) {
		setName(name);
		setSalary(2000);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	public abstract void promotion();
}

