package com.ae2dms.zoolab2;

public abstract class Employee implements Employable {
    private int ID;
    private String name;
    private int salary;

    public Employee(String name) {
        setEmployeeName(name);
    }

    @Override
    public void setEmployeeID(int number) {
        ID = number;
    }

    @Override
    public int getEmployeeID() {
        return ID;
    }

    @Override
    public void setEmployeeName(String name) {
        this.name = name;
    }

    @Override
    public String getEmployeeName() {
        return name;
    }

    @Override
    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public int getSalary() {
        return salary;
    }
}
