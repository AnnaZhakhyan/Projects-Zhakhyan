package businesslogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import datarecords.Employee;
import persistence.EmployeeStorage;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class EmployeeTest {

    @Mock
    private EmployeeStorage mockEmployeeStorage;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSearchEmployees() throws IOException {
        ArrayList<Employee> expectedEmployees = new ArrayList<>();
        Employee emp1 = new Employee("1", "Patrick", "Star", "Manager", "patrick123", "pass");
        Employee emp2 = new Employee("2", "Sponge", "Bob", "Developer", "sponge77", "hashh");
        expectedEmployees.add(emp1);
        expectedEmployees.add(emp2);

        when(mockEmployeeStorage.findEmployees(anyString())).thenReturn(expectedEmployees);
        ArrayList<Employee> actualEmployees = employeeService.searchEmployees("Doe");
        assertEquals(expectedEmployees.size(), actualEmployees.size());
        assertEquals(expectedEmployees.get(0).getFirstname(), actualEmployees.get(0).getFirstname());
        assertEquals(expectedEmployees.get(1).getFirstname(), actualEmployees.get(1).getFirstname());

        verify(mockEmployeeStorage, times(1)).findEmployees(anyString());
    }

    @Test
    public void testDeleteEmployee() {

        Employee empToDelete = new Employee("1", "Patrick", "Star", "Manager", "patrick123", "pass");

        employeeService.deleteEmployee(empToDelete);

        verify(mockEmployeeStorage, times(1)).deleteEmployee(empToDelete);
    }

    @Test
    public void testCreateEmployee() {
        String firstName = "Patrick";
        String lastName = "Star";
        String username = "patrick123";
        String password = "pass";
        String position = "Manager";

        when(mockEmployeeStorage.createEmployee(firstName, lastName, username, password, position)).thenReturn(true);

        boolean result = employeeService.createEmployee(firstName, lastName, username, password, position);

        assertTrue(result);

        verify(mockEmployeeStorage, times(1)).createEmployee(firstName, lastName, username, password, position);
    }

    @Test
    public void testHashPassword() {
        String password = "balloon";
        Hasher hasher = new Hasher();
        String hashedPassword = hasher.hashPassword(password);

        assertNotNull(hashedPassword);
        assertNotEquals(password, hashedPassword);
    }

}
