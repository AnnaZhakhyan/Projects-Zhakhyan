package gui;

import java.util.ArrayList;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;

public class SelectSeatController {

    @FXML private FlowPane flowPane;

    @FXML private Label selLabel;
    @FXML private Label classLabel;

    @FXML private Button rdmButton;
    @FXML public Button conButton;


    Random rng = new Random();
    private ArrayList<Button> buttonList = new ArrayList<>();
    public Button selectedButton;
    public int cost;
    public boolean random = false;

    public void disableTakenSeats(ArrayList<String> buttons){
        ArrayList<Button> toBeRemoved = new ArrayList<>();
        for (Button button : buttonList) {
            if(buttons.contains(button.getText())){
                button.setDisable(true);
                toBeRemoved.add(button);
            }
        }
        for (Button button : toBeRemoved) {
            buttonList.remove(button);
        }
        
    }

    @FXML
    void selectRandomSeat(){
        random = true;
        conButton.setDisable(false);
        conButton.requestFocus();
        int random = rng.nextInt(buttonList.size()-17)+17;
        if(selectedButton != null){
            selectedButton.setDisable(false);
            buttonList.add(selectedButton);
        }
        selectedButton = buttonList.get(random);
        buttonList.remove(random);
        selectedButton.setDisable(true);
        cost = 0;
        selLabel.setText("Current Seat: " + selectedButton.getText() + " ( +0€ )");
        classLabel.setText("Class: Economy ( +0€ )");
    }

    void selectSeat(Button button){
        cost = 0;
        random = false;
        conButton.setDisable(false);
        conButton.requestFocus();
        if(selectedButton != null){
            selectedButton.setDisable(false);
            buttonList.add(selectedButton);
        }
        selectedButton = button;
        if(Integer.valueOf(selectedButton.getText().split("")[0]) < 5){
            classLabel.setText("Class: Business ( +30€ )");
            cost += 30;
        } else {
            classLabel.setText("Class: Economy ( +0€ )");
        }
        selectedButton.setDisable(true);
        buttonList.remove(button);
        cost += 15;
        selLabel.setText("Current Seat: " + selectedButton.getText() + " ( +15€ )");
    }

    @FXML
    void initialize(){
        String[] abc = {"a","b","c","d","e","f"};
        int row = 1;
        int seat = 0;
        // Bussiness Class
        for(int i = 0; i!=20; i++){
            if(i == 2 || i == 7 || i == 12 || i == 17){
                Separator sep = new Separator();
                sep.setPrefWidth(36);
                sep.setVisible(false);
                flowPane.getChildren().add(sep);
            } else {
                Button button = new Button(row + abc[seat].toUpperCase());
                button.setPrefWidth(54);
                button.setPrefHeight(34);
                button.setOnAction(e -> {selectSeat(button);});
                flowPane.getChildren().add(button);
                buttonList.add(button);
                seat++;
            }
            if(seat == 4){
                seat = 0;
                row++;
            }
        }
        int corridor = 3;
        for(int i = 0; i!=196; i++){
            if(corridor == 6){
                Separator sep = new Separator();
                sep.setPrefWidth(36);
                sep.setVisible(false);
                flowPane.getChildren().add(sep);
                corridor = 0;
            } else {
                Button button = new Button(row + abc[seat].toUpperCase());
                button.setFont(new Font(11));
                button.setPrefWidth(36);
                button.setPrefHeight(34);
                button.setOnAction(e -> {selectSeat(button);});
                flowPane.getChildren().add(button);
                buttonList.add(button);
                corridor++;
                seat++;
            }
            if(seat == 6){
                seat = 0;
                row++;
            }
        }
        
    }
}
