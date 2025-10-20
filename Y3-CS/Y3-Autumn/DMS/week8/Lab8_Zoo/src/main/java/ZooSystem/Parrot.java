package ZooSystem;

public class Parrot extends Bird{
    public Parrot(String name) {
        super(name);
        // TODO Auto-generated constructor stub
    }

    public void speech(){
        System.out.println("My name is " + getName());
    }
}
