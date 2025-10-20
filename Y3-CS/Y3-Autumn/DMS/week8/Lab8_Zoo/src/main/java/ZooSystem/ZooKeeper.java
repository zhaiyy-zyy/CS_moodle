package ZooSystem;

public class ZooKeeper extends Employee {
    public ZooKeeper(String name) {
        super(name);
    }

    @Override
    public int calculateChristmasBonus() {
        int bonus = (int) ((double) getSalary()*0.05+100.0);
        return bonus;
    }
}
