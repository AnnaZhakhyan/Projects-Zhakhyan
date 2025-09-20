package businesslogic;

import datarecords.Employee;
import persistence.EmployeeStorage;

import java.io.IOException;
import java.util.ArrayList;

public class EmployeeService {
    private EmployeeStorage employeeStorage;

    public EmployeeService() {
        this.employeeStorage = new EmployeeStorage();
    }

    public ArrayList<Employee> searchEmployees(String query) throws IOException {
        return employeeStorage.findEmployees(query);
    }

    public void deleteEmployee(Employee employee) {
        employeeStorage.deleteEmployee(employee);
    }

    public boolean createEmployee(String firstName, String lastName, String username, String password, String position) {
       
        return employeeStorage.createEmployee(firstName, lastName, username, password, position);
    }

    public void editEmployee(Employee selectedEmployee) {
        employeeStorage.editEmployee(selectedEmployee);
    }
    
}
