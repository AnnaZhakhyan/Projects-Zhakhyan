package persistence;

public class PersistenceAPI {
    DatabaseConnection databaseConnection = new DatabaseConnection();
    EmployeeStorage employeeStorage = new EmployeeStorage();

    public DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }
    public EmployeeStorage getEmployeeStorage() {
        return employeeStorage;
    }

    public PersistenceAPI(){};
}
