import java.util.ArrayList;

public class Department {
    private String name;
    private ArrayList<Employee> listOfEmployees = new ArrayList<>();

    public Department(String name, Employee employee) {
        this.name = name;
        this.listOfEmployees.add(employee); // Если есть отдел, то там минимум 1 сотрудник
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Employee> getListOfEmployees() {

        return listOfEmployees;
    }

    public void addToEmployeeList(Employee employee) {
        this.listOfEmployees.add(employee);
    }

    public void showAllEmployees() {
        System.out.println("Список сотрудников отдела " + this.getName() + " :");
        for (Employee employee : listOfEmployees) {
            System.out.print("Фамилия: " + employee.getName() + " - ");
            System.out.println("Зарплата: " + employee.getSalary());
        }
    }

    public double getAverageSalary() {
        double sum = 0.0;
        for (Employee employee : listOfEmployees) {
            sum += employee.getSalary();
        }
        return sum / listOfEmployees.size();
    }

}
