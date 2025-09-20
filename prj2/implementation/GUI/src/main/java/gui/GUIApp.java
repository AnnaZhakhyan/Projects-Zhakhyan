package gui;

import java.io.IOException;

import businesslogic.BusinessLogicAPI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.util.Callback;
import persistence.PersistenceAPI;

/**
 * Main GUI App. Gets Business Logic injected. Delegates the switching of scenes
 * to the SceneManager. The controllerFactory takes care of instantiating the
 * controllers. This enables you to use parameterized constructors and to inject
 * (as in the example below regarding the CustomerController) a link to the
 * business logic layer. Invocation / initialization of this JavaFX app has been
 * slightly adapted compared to a default generated JavaFX app. See
 * documentation
 * above show() method.
 * 
 * @author Informatics Fontys Venlo
 */
public class GUIApp extends Application {

    private BusinessLogicAPI businessLogicAPI;
    private PersistenceAPI persistenceAPI;
    private SceneManager sceneManager;
    private static final String INITIAL_VIEW = "Login";

    private final Callback<Class<?>, Object> controllerFactory = (Class<?> c) -> {
        return switch (c.getName()) {
            case "gui.SearchFlightController" -> new SearchFlightController(this::getSceneManager, businessLogicAPI, persistenceAPI);
            case "gui.ErrorController" -> new ErrorController(this::getSceneManager);
            case "gui.LoginController" -> new LoginController(this::getSceneManager);
            case "gui.DiscountController" -> new DiscountController(this::getSceneManager);
            default -> null;
        };
    };

    public GUIApp show(BusinessLogicAPI businessLogicAPI, PersistenceAPI persistenceAPI) {
        this.businessLogicAPI = businessLogicAPI;
        this.persistenceAPI = persistenceAPI;
        return init(true);
    }

    GUIApp init(boolean startJavaFXToolkit) {
        if (startJavaFXToolkit) {
            Platform.startup(() -> {
            });

            SceneManager sm = initializeSceneManager();

            Platform.runLater(() -> {
                Stage stage = new Stage();
                stage.setMinHeight(440);
                stage.setMinWidth(680);
                try {
                    start(stage);
                } catch (IOException ex) {
                    throw new IllegalStateException(ex);
                }
            });

        } else {
            initializeSceneManager();
        }

        return this;
    }

    private SceneManager initializeSceneManager() {
        sceneManager = new SceneManager(controllerFactory, INITIAL_VIEW);
        return sceneManager;
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Demo Airline Information System");
        sceneManager.displayOn(stage, 680, 440);
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }
}
