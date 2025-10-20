package com.ae2dms.designpatterns.strategy;

public abstract class Duck {
    protected FlyBehavior flyBehavior;

    public void swim(){ System.out.println("Swimming, swimming..");}
    public void quack(){ System.out.println("quacking, quacking..");}
    public void performFly() {flyBehavior.fly();}
    public void setFlyBehavior(FlyBehavior fb) {flyBehavior = fb;}

    public abstract void display();
}
