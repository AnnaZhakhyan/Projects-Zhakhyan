package businesslogic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DiscountValidator {
    
    public static String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Discount name cannot be empty.";
        }
        return null;
    }

    public static String validatePercentage(int percentage) {
        if (percentage < 0 || percentage > 100) {
            return "Discount percentage must be between 0 and 100.";
        }
        return null;
    }

    public static String validateStartDate(LocalDate startDate) {
        if (startDate == null) {
            return "Start date cannot be null.";
        }
        if (startDate.isBefore(LocalDate.now())) {
            return "Start date cannot be in the past.";
        }
        return null;
    }

    public static String validateEndDate(LocalDate startDate, LocalDate endDate) {
        if (endDate == null) {
            return "End date cannot be null.";
        }
        if (startDate != null && !endDate.isAfter(startDate)) {
            return "End date must be at least one day after the start date.";
        }
        return null;
    }

    public static List<String> validateDiscount(String name, int percentage, LocalDate startDate, LocalDate endDate) {
        List<String> errors = new ArrayList<>();

        String nameError = validateName(name);
        if (nameError != null) {
            errors.add(nameError);
        }

        String percentageError = validatePercentage(percentage);
        if (percentageError != null) {
            errors.add(percentageError);
        }

        String startDateError = validateStartDate(startDate);
        if (startDateError != null) {
            errors.add(startDateError);
        }

        String endDateError = validateEndDate(startDate, endDate);
        if (endDateError != null) {
            errors.add(endDateError);
        }

        return errors;
    }
}
