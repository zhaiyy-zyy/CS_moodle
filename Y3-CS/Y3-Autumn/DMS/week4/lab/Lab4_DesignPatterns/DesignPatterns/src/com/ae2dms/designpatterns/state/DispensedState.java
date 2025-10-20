package com.ae2dms.designpatterns.state;

public class DispensedState implements CandyVendingMachineState{
    CandyVendingMachine machine;
    public DispensedState(CandyVendingMachine machine){
        this.machine=machine;
    }
    @Override
    public void insertCoin() {
        System.out.println("Error. System is currently dispensing");
    }
    @Override
    public void pressButton() {
        System.out.println("Error. System is currently dispensing");
    }
    @Override
    public void roll_out_candy() {
        if(machine.getCount()>0) {
            machine.setState(machine.getNoCoinState());
            machine.setCount(machine.getCount()-1);
        }
        else{
            System.out.println("No candies available");
            machine.setState(machine.getNoCandyState());
        }
    }
    @Override
    public String toString(){
        return "DispensedState";
    }
}