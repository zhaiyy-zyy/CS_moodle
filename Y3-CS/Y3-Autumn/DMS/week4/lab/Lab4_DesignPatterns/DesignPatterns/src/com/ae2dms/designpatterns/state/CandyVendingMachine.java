package com.ae2dms.designpatterns.state;

public class CandyVendingMachine {
    CandyVendingMachineState noCoinState;
    CandyVendingMachineState noCandyState;
    CandyVendingMachineState dispensedState;
    CandyVendingMachineState containsCoinState;
    CandyVendingMachineState state;
    int count;

    public CandyVendingMachine(int numberOfCandies){
        count=numberOfCandies;
        noCoinState=new NoCoinState(this);
        noCandyState=new NoCandyState(this);
        dispensedState=new DispensedState(this);
        containsCoinState=new ContainsCoinState(this);
        state = noCoinState;
    }

    public void insertCoin(){
        System.out.println("You inserted a coin.");
        state.insertCoin();
    }
    public void pressButton(){
        System.out.println("You have pressed the button.");
        state.pressButton();
        state.roll_out_candy();
    }

    /* Getters and Setters of the local variables/states. */

    public CandyVendingMachineState getNoCandyState() {
        return noCandyState;
    }
    public void setNoCandyState(CandyVendingMachineState noCandyState) {
        this.noCandyState = noCandyState;
    }
    public CandyVendingMachineState getNoCoinState() {
        return noCoinState;
    }
    public void setNoCoinState(CandyVendingMachineState noCoinState) {
        this.noCoinState = noCoinState;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public CandyVendingMachineState getState() {
        return state;
    }
    public void setState(CandyVendingMachineState state) {
        this.state = state;
    }
    public CandyVendingMachineState getContainsCoinState() {
        return containsCoinState;
    }
    public void setContainsCoinState(CandyVendingMachineState containsCoinState) {
        this.containsCoinState = containsCoinState;
    }
    public CandyVendingMachineState getDispensedState() {
        return dispensedState;
    }
    public void setDispensedState(CandyVendingMachineState dispensedState) {
        this.dispensedState = dispensedState;
    }
    @Override
    public String toString(){
        String machineDef="Current state of machine "+state +". Candies available "+count;
        return machineDef;
    }
}