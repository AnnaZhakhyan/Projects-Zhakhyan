package businesslogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DiscountValidatorTest {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidDiscount() {
        String name = "Summer Sale";
        int percentage = 20;
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(10);

        List<String> errors = DiscountValidator.validateDiscount(name, percentage, startDate, endDate);

        assertTrue(errors.isEmpty(), "Expected no validation errors");
    }

    @Test
    void testInvalidName() {
        String name = "";
        int percentage = 20;
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(10);

        List<String> errors = DiscountValidator.validateDiscount(name, percentage, startDate, endDate);

        assertFalse(errors.isEmpty(), "Expected validation errors for empty name");
        assertTrue(errors.contains("Discount name cannot be empty."), "Expected error message: Discount name cannot be empty.");
    }

    @Test
    void testInvalidPercentage() {
        String name = "Summer Sale";
        int percentage = -10;
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(10);

        List<String> errors = DiscountValidator.validateDiscount(name, percentage, startDate, endDate);

        assertFalse(errors.isEmpty(), "Expected validation errors for negative percentage");
        assertTrue(errors.contains("Discount percentage must be between 0 and 100."), "Expected error message: Discount percentage must be between 0 and 100.");
    }

    @Test
    void testStartDateInPast() {
        String name = "Summer Sale";
        int percentage = 20;
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(10);

        List<String> errors = DiscountValidator.validateDiscount(name, percentage, startDate, endDate);

        assertFalse(errors.isEmpty(), "Expected validation errors for start date in the past");
        assertTrue(errors.contains("Start date cannot be in the past."), "Expected error message: Start date cannot be in the past.");
    }

    @Test
    void testEndDateBeforeStartDate() {
        String name = "Summer Sale";
        int percentage = 20;
        LocalDate startDate = LocalDate.now().plusDays(10);
        LocalDate endDate = LocalDate.now().plusDays(1);

        List<String> errors = DiscountValidator.validateDiscount(name, percentage, startDate, endDate);

        assertFalse(errors.isEmpty(), "Expected validation errors for end date before start date");
        assertTrue(errors.contains("End date must be at least one day after the start date."), "Expected error message: End date must be at least one day after the start date.");
    }
}

