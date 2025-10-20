package com.siebers.ZooProject.Main;

public class Reptile extends Animal {
	private int numTeeth;

	public Reptile(String name, int numTeeth) {
		super(name);
		this.setNumTeeth(numTeeth);
	}

	@Override
	public void eat() {
		System.out.println(this.getClass().getSimpleName()+" eats like a reptile.");
	}

	public int getNumTeeth() {
		return numTeeth;
	}

	public void setNumTeeth(int numTeeth) {
		this.numTeeth = numTeeth;
	}

	@Override
	public void maintain() {
		System.out.println(this.getClass().getSimpleName()+" maintains life as reptile.");
	}
}

