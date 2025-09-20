package assembler;

import businesslogic.BusinessLogicAPI;
import businesslogic.BusinessLogicAPIInterface;
import gui.GUIApp;
import persistence.DatabaseConnection;
import persistence.PersistenceAPI;

/**
 * Assembler to setup layers and start the GUI.
 * @author Informatics Fontys Venlo
 */
public class Assembler {

    public static void main(String[] args) {
        
        BusinessLogicAPI businessLogicAPI = new BusinessLogicAPI();
        PersistenceAPI persistenceAPI = new PersistenceAPI();

        new GUIApp().show(businessLogicAPI, persistenceAPI);
    }
}