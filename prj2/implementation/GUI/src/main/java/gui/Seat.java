package gui;

import javafx.scene.control.Button;

public class Seat extends Button {

    private String seatID = "";
    private String seatClass = "";
    
    public Seat(String seatID, String seatClass){
        this.seatID = seatID;
        this.seatClass = seatClass;
        setText(seatID);
    }

    public String getSeatID(){
        return seatID;
    }
    public String getSeatClass(){
        return seatClass;
    }
}