package com.siebers.ZooProject.Main;

public class Amphibian extends Animal {

	public Amphibian(String name) {
		super(name);
	}

	@Override
	public void eat() {
		System.out.println(this.getClass().getSimpleName()+" eats like an amphibian.");
	}
	
	@Override
	public void enjoy() {
		System.out.println(this.getClass().getSimpleName()+" enjoys life as amphibian.");
	}

	@Override
	public void maintain() {
		System.out.println(this.getClass().getSimpleName()+" maintains life as amphibian.");	
	}
}

