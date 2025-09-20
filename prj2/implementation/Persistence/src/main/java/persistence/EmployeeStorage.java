package persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;

import datarecords.Employee;

public class EmployeeStorage {
    private DataSource dataSource;

    public EmployeeStorage() {
        dataSource = DBProvider.getDataSource("aisdb");
    }

    public boolean createEmployee(String firstName, String lastName, String username, String password, String position) {
        String sql = "INSERT INTO employee (firstname, lastname, username, password, pos) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
    
            if (containsNumbers(firstName) || containsNumbers(lastName)) {
                throw new IllegalArgumentException("First name and last name cannot contain numbers.");
            }
    
            statement.setString(1, firstName.trim());
            statement.setString(2, lastName.trim());
            statement.setString(3, username.trim());
            statement.setString(4, password.trim());
            statement.setString(5, position);
            statement.executeUpdate();
    
            System.out.println("Employee created successfully.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean containsNumbers(String str) {
        return str != null && !str.isEmpty() && str.matches(".*\\d.*");
    }

    public ArrayList<Employee> findEmployees(String query) throws IOException {
        ArrayList<Employee> employees = new ArrayList<>();
        String sql = "SELECT ID, firstname, lastname, pos, username, password FROM employee WHERE firstname LIKE ? OR lastname LIKE ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + query + "%");
            statement.setString(2, "%" + query + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String ID = resultSet.getString("ID");
                    String firstname = resultSet.getString("firstname").trim();
                    String lastname = resultSet.getString("lastname").trim();
                    String position = resultSet.getString("pos").trim();
                    String username = resultSet.getString("username").trim();
                    String password = resultSet.getString("password").trim();

                    Employee employee = new Employee(ID, firstname, lastname, position, username, password);
                    employees.add(employee);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding employees: " + e.getMessage());
        }
        return employees;
    }

    public void editEmployee(Employee selectedEmployee) {
        String sql = "UPDATE employee SET firstname = ?, lastname = ?, username = ?, password = ?, pos = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, selectedEmployee.getFirstname().trim());
            statement.setString(2, selectedEmployee.getLastname().trim());
            statement.setString(3, selectedEmployee.getUsername().trim());
            statement.setString(4, selectedEmployee.getPassword().trim());
            statement.setString(5, selectedEmployee.getPosition().trim());
            statement.setInt(6, Integer.parseInt(selectedEmployee.getID()));
            statement.executeUpdate();

            System.out.println("Employee updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating employee: " + e.getMessage());
        }
    }

    
    public void deleteEmployee(Employee selectedEmployee) {
        String sql = "DELETE FROM employee WHERE ID = ?";
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(5)) {
                System.out.println("Connection to the database established successfully.");
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, Integer.parseInt(selectedEmployee.getID()));
                    statement.executeUpdate();
                    System.out.println("Employee deleted successfully.");
                }
            } else {
                System.out.println("Failed to establish connection to the database.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting employee: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
