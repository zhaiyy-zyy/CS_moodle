package ZooSystemTest;

import ZooSystem.Animal;
import ZooSystem.Compound;
import ZooSystem.Parrot;
import org.junit.Test;

import static org.junit.Assert.*;

public class CompoundTest {
    @Test
    public void getAnimals() {
        Compound compound = new Compound();
        //compound.addAnimal(new Animal());
        assertTrue("exist animals in the list", compound.getAnimals().isEmpty());
    }

    @Test
    public void getAnimalWithNumbers() {
        Compound compound = new Compound();
        compound.addAnimal(new Parrot("Pat1"));
        compound.addAnimal(new Parrot("Pat2"));
        assertTrue("Not having 2 animals in the list", compound.getAnimals().size()==2);
    }

    @Test(timeout=1000)
    //@Test
    public void get10MillionAnimals() {
        Compound compound = new Compound();
        for(int i = 0; i < 10000000; i++) {
            compound.addAnimal(new Parrot("Pat"+i));
        }
        assertTrue("Not having 2 animals in the list", compound.getAnimals().size()==10000000);
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void testIOBException() {
        Compound compound = new Compound();
        for(int i = 0; i < 10; i++) {
            compound.addAnimal(new Parrot("Pat"+i));
        }
        Parrot p = (Parrot) compound.getAnimals().get(19);
    }

}