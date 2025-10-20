package ZooSystem;

public class Admin extends Employee {
    public Admin(String name) {
        super(name);
    }

    @Override
    public int calculateChristmasBonus() {
        int bonus = (int) ((double) getSalary()*0.08 + 90);
        return bonus;
    }
}
