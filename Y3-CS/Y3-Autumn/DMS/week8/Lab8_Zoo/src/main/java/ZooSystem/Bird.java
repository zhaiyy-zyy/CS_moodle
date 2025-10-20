package ZooSystem;

public abstract class Bird extends Animal{
    public Bird(String name) {
        super(name);
        // TODO Auto-generated constructor stub
    }

    private double lengthOfBeak;

    public double getLengthOfBeak() {
        return lengthOfBeak;
    }

    public void setLengthOfBeak(double lengthOfBeak) {
        this.lengthOfBeak = lengthOfBeak;
    }
}
