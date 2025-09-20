package gui;

import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GUIPassenger {

    Logger logger = new Logger();

    Label name = new Label();
    int id = -1;
    ChoiceBox<String> docs = new ChoiceBox<String>();
    TextField dcid = new TextField();
    ChoiceBox<String> clas = new ChoiceBox<String>();
    String seat = ("No Seat");
    String xtra = ("Test");
    String featureList = "";
    String seatNumber = "None";

    Button seatButton;
    Button discButton;
    Button xtraButton;

    ArrayList<GUIStaticDiscount> discounts = new ArrayList<>();

    int price;
    int seatPrice = 0;
    int xtraPrice = 0;
    int flatDiscount = 0;
    int procDiscount = 0;

    TableView<GUIPassenger> passTable;

    public GUIPassenger(GUIClient client, TableView tableView, int price) {
        id = Integer.valueOf(client.getId());
        name.setText(client.getFullName());
        docs.getItems().add("Passport");
        docs.getItems().add("ID Card");
        docs.getSelectionModel().select(0);
        dcid.setPromptText("Document Identification");
        clas.getItems().add("Economy");
        clas.getItems().add("Bussines");
        clas.getSelectionModel().select(0);
        this.price = price;
        passTable = tableView;
    }

    public String getDocumentID(){
        return dcid.getText();
    }

    public String getFeatureList() {
        return featureList;
    }

    public int getId() {
        return id;
    }

    public Label getName() {
        return name;
    }

    public ChoiceBox getDocs() {
        return docs;
    }

    public String getDocumentType(){
        return docs.getSelectionModel().getSelectedItem();
    }


    public TextField getDcid() {
        return dcid;
    }

    public ChoiceBox getClas() {
        return clas;
    }

    public Integer getPrice() {
        int finalPrice = price + seatPrice + xtraPrice - flatDiscount;
        finalPrice -= finalPrice * procDiscount/100;
        return finalPrice;
    }

    public Button getSeat() {

        seatButton = new Button(seatNumber);
        seatButton.setMaxWidth(Double.MAX_VALUE);
        seatButton.setOnAction(e -> {
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("SelectSeat.fxml"));
                Parent layout = loader.load();
                Scene scene = new Scene(layout, 288, 350);
                SelectSeatController controller = loader.getController();

                ArrayList<String> seatsTaken = new ArrayList<>();
                for(GUIPassenger item : passTable.getItems()){
                    seatsTaken.add(item.getSeatNumber());
                }
                controller.disableTakenSeats(seatsTaken);
                controller.conButton.setOnAction(event -> {
                    String seat = controller.selectedButton.getText();
                    seatNumber = seat;
                    seatButton.setText(seatNumber);
                    seatPrice = controller.cost;
                    passTable.refresh();
                    stage.close();
                });

                stage.setScene(scene);
                stage.setTitle("Select Seat");
                stage.show();
            } catch (Exception ex) {
                logger.display(ex);
            }

        });
        return seatButton;
    }

    public String getSeatNumber() {
        return seatButton.getText().split(" ")[0];
    }

    public Button getXtra() {
        xtraButton = new Button("Extras");
        xtraButton.setMaxWidth(Double.MAX_VALUE);
        xtraButton.setOnAction(e -> {
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddExtras.fxml"));
                Parent layout = loader.load();
                Scene scene = new Scene(layout, 250, 300);
                AddExtrasController controller = loader.getController();
                controller.setup(featureList);
                controller.conButton.setOnAction(event -> {
                    featureList = "";
                    xtraPrice = 0;
                    for (GUIFeature feature : controller.xtraTable.getItems()) {
                        if (feature.getChecked()) {
                            featureList += feature.getFeature() + ",";
                            xtraPrice += feature.getPrice();
                        }
                    }
                    passTable.refresh();
                    stage.close();
                });

                stage.setScene(scene);
                stage.setTitle("Add Extras");
                stage.show();
            } catch (Exception e1) {
                logger.display(e1);
            }

        });
        return xtraButton;

    }

    public void setSeat(String seat, Boolean random) {
        String string = seat;
        if (random) {
            string += " [R]";
        }
        this.seat = string;
    }

    public void setSeatText(String string) {
        seatButton.setText(string);
    }

    public Button getDisc(){
        String string = "";
        if(flatDiscount != 0){
            string += "- " + flatDiscount + "€";
            if(procDiscount != 0) string += " | ";
        }
        if(procDiscount != 0) string += procDiscount + "%";
        if(flatDiscount == 0 && procDiscount == 0) string = "None";
        discButton = new Button(string);
        discButton.setMaxWidth(Double.MAX_VALUE);

        discButton.setOnAction(event ->{
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("addStaticDiscounts.fxml"));
                Parent layout = loader.load();
                Scene scene = new Scene(layout, 250, 300);
                StaticDiscountsController controller = loader.getController();

                controller.getDiscTable().getItems().addAll(discounts);

                controller.getConButton().setOnAction(e -> {
                    flatDiscount = 0;
                    procDiscount = 0;

                    discounts.clear();
                    for(GUIStaticDiscount discount : controller.getDiscTable().getItems()){
                        discounts.add(discount);

                        if(discount.getProcSelected()){
                            procDiscount += discount.getValue();
                        } else {
                            flatDiscount += discount.getValue();
                        }
                    }
                    if(procDiscount > 100){
                        controller.alert("Discounts can't be >100%!");
                        procDiscount = 0;
                    } else {
                        passTable.refresh();
                        stage.close();
                    }
                    
                });
    
                stage.setScene(scene);
                stage.setTitle("Add Extras");
                stage.show();
            } catch (Exception e) {
                logger.display(e);
            }
        });
        return discButton;
    }


    public ArrayList<GUIStaticDiscount> getDiscounts() {
        return discounts;
    }
    
    public void setDiscounts(ArrayList<GUIStaticDiscount> discounts) {
        this.discounts = discounts;
    }
    
    boolean isEmpty(){
        if (dcid.getText().isEmpty()) {
            return true;
        } else if (getSeatNumber().equals("None")){
            return true;
        } else {
            return false;
        }
    }

}
