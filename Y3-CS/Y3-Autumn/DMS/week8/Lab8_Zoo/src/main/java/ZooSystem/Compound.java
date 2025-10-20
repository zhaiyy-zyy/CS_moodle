package ZooSystem;

import java.util.ArrayList;

public class Compound {
    private ArrayList<Animal> animals;

    public Compound() {
        animals = new ArrayList<Animal>();
    }

    public void addAnimal(Animal animal){
        animals.add(animal);
    }
    public ArrayList<Animal> getAnimals(){
        return animals;
    }
}
