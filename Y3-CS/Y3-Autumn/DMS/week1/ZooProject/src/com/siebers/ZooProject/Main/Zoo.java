package com.siebers.ZooProject.Main;

import java.util.ArrayList;

public class Zoo {
	private String location;
	private ArrayList<Compound> compounds;
	
	public Zoo(String location, int numCompounds) {
		this.setLocation(location);
		this.compounds=new ArrayList<Compound>();
		createCompound(numCompounds);
	}
	
	public Zoo() {
		this("Unknown",1);
	}
	
	public void createCompound(int numCompounds) {
		if(numCompounds<1)numCompounds=1;
		for(int i=0;i<numCompounds;i++) {
			this.compounds.add(new Compound());
		}
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public void printInfo() {
		System.out.println("The zoo in "+location+" has "+compounds.size()+" compounds.");
	}
}

