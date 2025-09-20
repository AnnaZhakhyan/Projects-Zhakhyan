module gui_module {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.base;
    
    requires businesslogic_module;
    requires datarecords_module;
    requires javafx.graphics;
    requires persistence_module;
    requires javafx.base;

    opens gui to javafx.fxml;
    
    exports gui;
}
