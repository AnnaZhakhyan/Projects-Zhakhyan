package gui;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

public class GUIFeature {
    SimpleStringProperty feature;
    SimpleIntegerProperty price;
    SimpleBooleanProperty check;

    CheckBox checkBox = new CheckBox();

    GUIFeature(String feature, int price){
        this.feature = new SimpleStringProperty(feature);
        this.price = new SimpleIntegerProperty(price);
    }

    public String getFeature() {
        return feature.get();
    }
    public int getPrice() {
        return price.get();
    }
    public CheckBox getCheck() {
        return checkBox;
    }

    public boolean getChecked(){        
        return checkBox.isSelected();
    }
}
