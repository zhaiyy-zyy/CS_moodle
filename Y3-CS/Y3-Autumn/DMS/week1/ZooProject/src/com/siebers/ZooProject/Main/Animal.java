package com.siebers.ZooProject.Main;

public abstract class Animal implements Maintainable {
	private String name;

	public Animal(String name) {
		this.setName(name);
	}
	
	public abstract void eat();
	
	public void enjoy() {
		System.out.println(this.getClass().getSimpleName()+" enjoys life as animal.");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

