public class Employee {
    private String name;
    private double salary;
    private String departmentName;

    public Employee(String name, double salary, String department) {
        this.name = name;
        this.salary = salary;
        this.departmentName = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public String getDepartment() {
        return departmentName;
    }

    public void setDepartment(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
