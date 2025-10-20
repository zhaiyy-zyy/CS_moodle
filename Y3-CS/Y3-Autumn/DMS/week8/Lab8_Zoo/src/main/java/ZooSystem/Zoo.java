package ZooSystem;

import java.util.ArrayList;

public class Zoo {
    private String location;
    private int numCompounds;
    public static int numZoos;
    private ArrayList<Compound> compounds;

    //constructor #1
    public Zoo(String location, int numCompounds){
        setLocation(location);
        setNumCompounds(numCompounds);
        compounds = new ArrayList<Compound>();
        numZoos++;
        for (int i = 0; i < numCompounds; i++){
            addCompounds(new Compound());
        }
    }

    private void addCompounds(Compound compound) {
        this.compounds.add(compound);
    }

    //constructor #2
    public Zoo(){
        this("Unknown", 30);
    }

    public void buildNewCompound() {
        this.numCompounds++;
    }

    public int getNumCompounds() {
        return numCompounds;
    }

    public void setNumCompounds(int numCompounds) {
        this.numCompounds = numCompounds;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public void printInfo(){
        System.out.println("Zoo location is " + this.location + ", has "
                + this.numCompounds + " compounds");
    }
}
