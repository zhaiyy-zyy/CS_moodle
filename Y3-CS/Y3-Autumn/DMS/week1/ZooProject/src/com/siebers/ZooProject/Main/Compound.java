package com.siebers.ZooProject.Main;

import java.util.ArrayList;

public class Compound {
	private ArrayList<Animal> animals;
	
	public Compound() {
		animals=new ArrayList<>();
	}
/*
	public void addAnimal() {
		animals.add(new Animal());
	}
*/	
	public void addAnimal(Animal animal) {
		animals.add(animal);
	}
	
	public void printInfo() {
		System.out.println("The compound has "+animals.size()+" animals.");
	}
}

