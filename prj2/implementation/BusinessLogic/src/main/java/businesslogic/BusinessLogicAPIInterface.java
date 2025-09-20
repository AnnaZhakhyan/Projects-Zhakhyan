package businesslogic;

import java.util.ArrayList;

import datarecords.Employee;

public interface BusinessLogicAPIInterface {

    boolean registerEmployee(String firstName, String lastName, String username, String hashedPassword,
            String position);

    String hashPassword(String password);

    ArrayList<Employee> searchEmployees(String query);

    void deleteEmployee(Employee selectedEmployee);


}
