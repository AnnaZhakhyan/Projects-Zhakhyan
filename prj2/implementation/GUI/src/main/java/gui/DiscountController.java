package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Supplier;

import datarecords.Discount;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import persistence.DatabaseConnection;
import businesslogic.DiscountValidator;

public class DiscountController implements Initializable {

    DatabaseConnection dbconnect = new DatabaseConnection();

    private final Supplier<SceneManager> sceneManagerSupplier;
    
    @FXML private TableColumn<?, ?> dName;
    @FXML private TableColumn<?, ?> dProc;
    @FXML private TableColumn<?, ?> dStart;
    @FXML private TableColumn<?, ?> dEnd;
    @FXML private TableView<GUIDiscount> dTable;
    
    @FXML private Button editButton;
    @FXML private Button registerButton;
    @FXML private Button removeButton;

    private GUIDiscount selectedDiscount; // Variable to hold the selected discount

    public DiscountController(Supplier<SceneManager> sceneManagerSupplier) {
        this.sceneManagerSupplier = sceneManagerSupplier;
    }

    private void update_dTable() {
        ArrayList<Discount> discounts = dbconnect.findDiscounts();
        if (discounts != null) {
            dTable.getItems().clear();
            for (Discount discount : discounts) {
                ((TableView<GUIDiscount>) dTable).getItems().add(new GUIDiscount(discount));
            }
        }
    }

    @FXML
    void back(){
        sceneManagerSupplier.get().changeScene("SearchFlight");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dName.setCellValueFactory(new PropertyValueFactory<>("name"));
        dProc.setCellValueFactory(new PropertyValueFactory<>("percentage"));
        dStart.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        dEnd.setCellValueFactory(new PropertyValueFactory<>("endDate"));
       
        update_dTable();

        // Add listener to detect row selection
        dTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedDiscount = newSelection;
            }
        });

        // Add event handler for remove button
        removeButton.setOnAction(event -> {
            removeDiscount();
        });
    }

    public void registerDiscount() throws IOException{
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterGeneralDiscount.fxml"));
        Parent layout = loader.load();
        Scene scene = new Scene(layout, 400, 285);

        RegisterGeneralDiscountController controller = loader.getController();
        controller.setDiscountController(this);

        stage.setScene(scene);
        stage.setTitle("Register Discount");
        stage.show();
    }

    @FXML private void handleEditButtonAction() {
        if (selectedDiscount != null) {
            try {
                // Load the EditGeneralDiscount window
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EditGeneralDiscount.fxml"));
                Parent layout = loader.load();
                Scene scene = new Scene(layout, 400, 285);
                
                //Set the existing discount details in the EditGeneralDiscountController
                EditGeneralDiscountController controller = loader.getController();
                controller.setDiscountController(this); // Pass the DiscountController instance
                controller.setDiscount(selectedDiscount);

                stage.setScene(scene);
                stage.setTitle("Edit Discount");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error loading EditGeneralDiscount window: " + e.getMessage());
            }
        } else {
            // Inform the user to select a discount before editing
            System.out.println("Please select a discount to edit.");
        }
    }

    public void updateDiscountInDatabase(Discount discount) {
        dbconnect.updateDiscount(discount);
        update_dTable();
    }

    public void addDiscountInDatabase(Discount discount) {
        dbconnect.addDiscount(discount);
        update_dTable();
    }
    
    private void removeDiscount() {
        if (selectedDiscount != null) {
            Discount discountToRemove = new Discount(
                selectedDiscount.getName(),
                selectedDiscount.getPercentage(),
                selectedDiscount.getStartDate(),
                selectedDiscount.getEndDate()
            );

            dbconnect.removeDiscount(discountToRemove);
            update_dTable();

        } else {
            // Inform the user to select a row before removing
            System.out.println("Please select a discount to remove.");
        }
    }
}