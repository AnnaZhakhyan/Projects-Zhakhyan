package gui;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;

import businesslogic.DiscountValidator;
import datarecords.Discount;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterGeneralDiscountController {

    @FXML private TextField dName;
    @FXML private TextField dPercentage;
    @FXML private DatePicker eDate;
    @FXML private DatePicker sDate;
    @FXML private Button registerButton;
    @FXML private Label errorLabel;

    private DiscountController discountController;

    public void errorMessage(List<String> text){
        for (String string : text) {
            errorLabel.setText(string);
        }
    }

    public void setDiscountController(DiscountController discountController) {
        this.discountController = discountController;
    }

    @FXML
    private void handleRegisterButtonAction() {
     try {
            String name = dName.getText();
            int percentage = Integer.parseInt(dPercentage.getText());
            LocalDate startDate = sDate.getValue();
            LocalDate endDate = eDate.getValue();

            // Validate the input
            List<String> errors = DiscountValidator.validateDiscount(name, percentage, startDate, endDate);
            errorMessage(errors);
            if (!errors.isEmpty()) {
                // Display validation errors to the user
                errors.forEach(System.out::println);
                return; // Do not proceed if there are validation errors
            }

            // Save the new discount using the DiscountController
            discountController.addDiscountInDatabase(new Discount(name, percentage, startDate.toString(), endDate.toString()));


            // Close the current stage
            Stage stage = (Stage) dName.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in handleRegisterButtonAction: " + e.getMessage());
        }
    }
}

