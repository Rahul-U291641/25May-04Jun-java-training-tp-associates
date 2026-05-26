package assignment_1;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Employee Stream Processing
 * */
public class EmployeeStreamProcessing {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee(1, "Alice", "HR", 50000, "Active"),
                new Employee(2, "Bob", "IT", 60000, "Inactive"),
                new Employee(3, "Charlie", "Finance", 75000, "Active"),
                new Employee(4, "David", "IT", 70000, "Active"),
                new Employee(5, "Eve", "HR", 45000, "Inactive")
        );
        fetchHighSalaryEmployees(employees);
        convertEmployeeNamesToUpperCase(employees);
        sortEmployeesBySalaryDescending(employees);
        sortEmployeesBySalaryDescendingUsingReversed(employees);
        countActiveEmployees(employees);
        groupEmployeesByDepartment(employees);
        findHighestSalaryEmployee(employees);
        findSecondHighestSalaryEmployee(employees);
        findSecondHighestSalaryEmployeeAlternative(employees);
    }

    /** Tasks of processing the employee stream **/
    // 1. Fetch employees with salary > 60000
    static void fetchHighSalaryEmployees(List<Employee> employees) {
        // Using Java Streams to filter and print employees with salary > 60000
        System.out.println("-------------------------------------------");
        System.out.println("Employees with salary greater than 60000: ");
        System.out.println("--------------------------------------------");
        employees.stream()
                .filter(emp -> emp.getSalary() > 60000)
                .forEach(System.out::println);
    }

    // 2. Convert employee names to uppercase
    static void convertEmployeeNamesToUpperCase(List<Employee> employees) {
        System.out.println("-------------------------------------------");
        System.out.println("Employee names in uppercase: ");
        System.out.println("--------------------------------------------");
        employees.stream()
                .map(emp -> emp.getName().toUpperCase())
                .forEach(System.out::println);
    }

    // 3. Sort employees by salary in descending order (using .compare method)
    static void sortEmployeesBySalaryDescending(List<Employee> employees) {
        System.out.println("-------------------------------------------");
        System.out.println("Employees sorted by salary in descending order (using .compare method): ");
        System.out.println("--------------------------------------------");
        employees.stream()
                .sorted((emp1, emp2) -> Double.compare(emp2.getSalary(), emp1.getSalary()))
                .forEach(System.out::println);
    }

    //3. Sort employees by salary in descending order (using .reversed method)
    static void sortEmployeesBySalaryDescendingUsingReversed(List<Employee> employees) {
        System.out.println("-------------------------------------------");
        System.out.println("Employees sorted by salary in descending order (using reversed): ");
        System.out.println("--------------------------------------------");
        employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .forEach(System.out::println);
    }

    //4. Find count of active employees
    static void countActiveEmployees(List<Employee> employees) {
        System.out.println("-------------------------------------------");
        System.out.println("Count of active employees: ");
        System.out.println("--------------------------------------------");
        long count = employees.stream()
                .filter(emp -> "Active".equalsIgnoreCase(emp.getActiveStatus()))
                .count();
        System.out.println("Count of active employees: " + count);
    }

    //5. Group employees department-wise
        static void groupEmployeesByDepartment(List<Employee> employees) {
            System.out.println("-------------------------------------------");
            System.out.println("Employees grouped by department: ");
            System.out.println("--------------------------------------------");

            Map<String, List<Employee>> groupedByDepartment = employees.stream()
                    .collect(Collectors.groupingBy(Employee::getDepartment));

            groupedByDepartment.forEach((department, empList) -> {
                System.out.println("Department: " + department);
                empList.forEach(System.out::println);
                System.out.println();
            });
        }

        //6. Find the highest salary employee
        static void findHighestSalaryEmployee(List<Employee> employees) {
            System.out.println("-------------------------------------------");
            System.out.println("Employee with the highest salary: ");
            System.out.println("--------------------------------------------");
            employees.stream()
                    .max(Comparator.comparingDouble(Employee::getSalary))
                    .ifPresent(System.out::println);
        }

        //7. Find the second-highest salary employee
        static void findSecondHighestSalaryEmployee(List<Employee> employees) {
            System.out.println("-------------------------------------------");
            System.out.println("Employee with the second highest salary: ");
            System.out.println("--------------------------------------------");
            employees.stream()
                    .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                    .skip(1)
                    .findFirst()
                    .ifPresent(System.out::println);
        }

        //7. Find the second-highest salary employee (alternative approach)
        static void findSecondHighestSalaryEmployeeAlternative(List<Employee> employees) {
            System.out.println("-------------------------------------------");
            System.out.println("Employee with the second highest salary (alternative approach): ");
            System.out.println("--------------------------------------------");
            employees.stream()
                    .map(Employee::getSalary)
                    .distinct()
                    .sorted(Comparator.reverseOrder())
                    .skip(1)
                    .findFirst()
                    .ifPresent(secondHighestSalary -> {
                        employees.stream()
                                .filter(emp -> emp.getSalary() == secondHighestSalary)
                                .forEach(System.out::println);
                    });
        }
}
