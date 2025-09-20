package gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class GUIStaticDiscount {
    TextField discount = new TextField();
    Spinner amount = new Spinner<>();
    CheckBox proc = new CheckBox();
    TableView table;

    @SuppressWarnings("unchecked")
    GUIStaticDiscount(String discount, int amount, boolean check, TableView table){
        this.discount.setText(discount);
        this.proc.setSelected(check);
        this.amount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, amount));
        this.table = table;
    }

    public TextField getDiscount() {
        return discount;
    }
    public Object getAmount() {
        return amount;
    }
    public int getValue(){
        return (int) amount.getValue();
    }
    public CheckBox getProc() {
        return proc;
    }
    public Boolean getProcSelected(){
        return proc.isSelected();
    }
}
