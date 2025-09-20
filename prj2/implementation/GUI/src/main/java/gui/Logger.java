package gui;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Logger {
    public void display(Exception e) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("logs.fxml"));
            Parent layout;
            layout = loader.load();
            Scene scene = new Scene(layout, 700, 300);
            LogsController controller = loader.getController();

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            controller.LogLabel.setText(sw.toString());

            stage.setScene(scene);
            stage.setTitle("Logs");
            stage.show();
        } catch (Exception e1) {
            e1.printStackTrace();

        }
    }
}
