import java.io.*;
import java.util.ArrayList;

public class TransfersTestDrive {
    public static void main(String[] args) {
        // Запрашиваем у пользователя путь к файлу
        System.out.println("Введите путь файла с сотрудниками.");
        System.out.println("Предполагается, что фамилии, отделы и зарплаты разделены" +
                " точкой с запятой.");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String fileName = null;
        try {
            fileName = reader.readLine();
        } catch (IOException ioex) {
            System.out.println("Введите, пожалуйста, корректный путь файла");
        }

        // Запрашиваем у пользователя выходной файл
        System.out.println("Введите путь к файлу с результатами");
        String outputFileName = null;
        try {
            outputFileName = reader.readLine();
            reader.close();
        } catch (IOException ioex) {
            System.out.println("Введите, пожалуйста, корректный путь файла");
        }

        // Создаем ридер для чтения файла
        BufferedReader fileReader = null;
        try {
            fileReader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException fnfex) {
            System.out.println("Ошибка указания пути файла. Перезапустите программу и " +
                    "укажите верный путь.");
        }

        // Заранее создаем список отделов
        ArrayList<Department> departments = new ArrayList<>();
        // Проходимся по файлу, сплитим по разделителю, заполняем списки отделов и сотрудников
        try {
            while(fileReader.ready()) {
                String nextLine = fileReader.readLine();
                String[] data = nextLine.split(";");
                if (data.length != 3) System.out.println("Неверный формат данных. " +
                        "Данные должны быть указаны в формате Фамилия;Отдел;Зарплата(число).");

                String nextEmployeeName = data[0].trim(); // Фамилия очередного сотрудника
                String nextEmployeeDpt = data[1].trim(); // Департамент очередного сотрудника
                double nextEmployeeSalary = 0.0;
                try {
                    nextEmployeeSalary = Double.parseDouble(data[2].trim()); // ЗП очередного сотрудника
                } catch (NumberFormatException nfex) {
                    System.out.println("Зарплата не является числом." +
                            "Поэтому вы видите ошибку ниже. Пожалуйста, проверьте данные о зарплате.");
                }
                // Теперь, когда все данные есть, создаем очередного сотрудника
                Employee nextEmployee = new Employee(nextEmployeeName, nextEmployeeSalary, nextEmployeeDpt);

                // Сначала предварительно создаем новый отдел.
                Department nextDepartment = new Department(nextEmployeeDpt, nextEmployee);
                boolean contains = false; // Полагаем, что его нет в списке
                for (Department dpt : departments) { // Проходимся по списку, ищем этот отдел
                    if (dpt.getName().equals(nextDepartment.getName())) {
                        contains = true;
                        dpt.addToEmployeeList(nextEmployee); // Отдел найден, просто добавляем туда сотрудника
                    }
                }
                // Если после цикла отдел не найден, то создаем новый отдел (в конструкторе сразу добавляется новый сотрудник)
                if (!contains) {
                    departments.add(new Department(nextEmployeeDpt, nextEmployee));
                }
            }
        } catch (IOException fileReadEx) {
            System.out.println("Ошибка чтения файла.");
        }

        // Формируем выходной файл
        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(outputFileName));
            // Проходимся по списку отделов и выводим среднюю зарплату в каждом
            for (Department dpt : departments) {
                fileWriter.write("Средняя зарплата в департаменте " + dpt.getName() +
                " равна " + dpt.getAverageSalary());
                fileWriter.newLine();
            }
            // Попробуем вычислить переводы сотрудников
            // Для этого необходимы 2 условия:
            // 1. Чтобы его ЗП была меньше средней по его отделу
            // 2. Его ЗП должна быть больше средней по другому отделу
            fileWriter.newLine();
            fileWriter.write("Чтобы увеличить средние ЗП, возможны следующие варианты переводов:");
            fileWriter.newLine();
            for (Department dpt : departments) {
                double averageDptSalary = dpt.getAverageSalary(); // Средняя ЗП отдела
                for (Employee employee : dpt.getListOfEmployees()) { // Пройдемся по сотрудникам текущего отдела
                    if (employee.getSalary() > averageDptSalary) break; // Если его ЗП больше средней, то останов
                    for (Department dptToTransfer : departments) { // Снова перебираем средние ЗП всех отделов
                        if (employee.getSalary() > dptToTransfer.getAverageSalary()) { // Оба условия удовлетворены
                            fileWriter.write("Сотрудник " + employee.getName().toUpperCase() + " из <" + employee.getDepartment() +
                                    "> в ----->  <" + dptToTransfer.getName() + ">");
                            fileWriter.newLine();
                        }
                    }
                }
            }
            fileWriter.close();
            System.out.println("Готово. Проверьте файл с результатом");
        } catch (IOException ioex) {
            System.out.println("Ошибка записи в файл. Пожалуйста, повторите попытку.");
        }
    }
}
