package com.ae2dms.zooproject.zoo;

import com.ae2dms.zooproject.employee.Employee;

public class Zookeeper extends Employee {

    public Zookeeper(String name) {
        super(name);
    }

    @Override
    public double getSalary() {
        double baseSalary = super.getSalary();
        return baseSalary + 1000.00;
    }

    @Override
    public void promotion() {
        super.setSalary(super.getSalary() * 1.1);
    }
}

