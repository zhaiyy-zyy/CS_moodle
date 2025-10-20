package com.siebers.ZooProject.Main;

import java.util.ArrayList;

public class ZooApp {
	
	public static void main(String[] args) {
		ArrayList<Object> animals=new ArrayList<>();
		Object o=new Reptile("Snake",4);
		Reptile r=new Reptile("Turtle",24);
		animals.add(o);
		animals.add(r);
		animals.add(new Amphibian("Frog"));
		while(animals.size()>0) {
			o=animals.remove(0);
			System.out.println(o.toString());
			((Animal)o).eat();
			((Animal)o).enjoy();
			((Animal)o).maintain();
			System.out.println();
		}
	}
}


