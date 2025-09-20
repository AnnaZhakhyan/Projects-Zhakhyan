package gui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

public class LogsController {

    @FXML
    public Label LogLabel;

    @FXML
    private MenuItem saveAsButton;

    @FXML
    private MenuItem saveButton;

    @FXML
    void save(ActionEvent event) throws IOException {
        File file = new File("implementation\\logs_" + LocalDateTime.now().getHour() + LocalDateTime.now().getMinute() + LocalDateTime.now().getSecond() + ".txt");
        System.out.println(LocalDateTime.now().getSecond());
        file.createNewFile();
        try (FileWriter writer = new FileWriter(file.getAbsolutePath())) {
            writer.write(LogLabel.getText());
        }
    }

    @FXML
    void saveAs(ActionEvent event) {

    }
}
