package ZooSystem;

public interface Employable {
    public void setEmployeeID(int number);
    public int getEmployeeID();
    public void setEmployeeName(String name);
    public String getEmployeeName();
    public void setSalary(int salary);
    public int getSalary();

    public int calculateChristmasBonus();
}
