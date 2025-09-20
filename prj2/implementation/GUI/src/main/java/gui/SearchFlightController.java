package gui;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.function.Supplier;

import businesslogic.BusinessLogicAPI;
import businesslogic.Verifier;
import datarecords.Account;
import datarecords.Customer;
import datarecords.Flight;
import datarecords.StaticDiscount;
import datarecords.Ticket;
import javafx.beans.Observable;
import javafx.css.converter.StopConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.paint.Color;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import persistence.DatabaseConnection;
import persistence.PersistenceAPI;

class SearchFlightController {

    int fullPrice = 0;
    int ppPrice = 0;

    Verifier verifier;
    DatabaseConnection dbconnect;

    Logger logger = new Logger();
    private final Supplier<SceneManager> sceneManagerSupplier;
    Flight currentFlight;

    @FXML private TabPane tabPane;
    @FXML private Tab bookTab;
    @FXML private Tab finTab;

    @FXML private Label greetLabel;
    @FXML private Label detailLabel;

    @FXML private ChoiceBox<String> numPP;
    @FXML private CheckBox rTripButton;
    @FXML private HBox returnHBox;

    @FXML private ComboBox<String> arrIATA_1;
    @FXML private ComboBox<String> depIATA_1;
    @FXML private DatePicker outDate;

    @FXML private TableView<GUIFlight> flightTable;
    @FXML private TableColumn<GUIFlight, ?> dep_col;
    @FXML private TableColumn<GUIFlight, ?> conn_col;
    @FXML private TableColumn<GUIFlight, ?> arr_col;
    @FXML private TableColumn<GUIFlight, ?> date_col;
    @FXML private TableColumn<GUIFlight, ?> time_col;
    @FXML private TableColumn<GUIFlight, ?> book_col;
    @FXML private TableColumn<GUIFlight, ?> cost_col;

    @FXML private Button searchButton;

    @FXML private Label dep_datetime;
    @FXML private Label src_airport;
    @FXML private Label arr_datetime;
    @FXML private Label des_airport;
    @FXML private Label currentCost;
    @FXML private Label perpersonCost;

    @FXML private Label departAirport;
    @FXML private Label arriveAirport;
    @FXML private Label flightPrice;

    @FXML private TableView<GUIPassenger> passTable;
    @FXML private TableColumn<GUIPassenger, ?> name_col;
    @FXML  private TableColumn<GUIPassenger, ?> docs_col;
    @FXML private TableColumn<GUIPassenger, ?> dcid_col;
    @FXML private TableColumn<GUIPassenger, ?> seat_col;
    @FXML private TableColumn<GUIPassenger, ?> xtra_col;
    @FXML private TableColumn<GUIPassenger, ?> disc_col;
    @FXML private TableColumn<GUIPassenger, ?> price_col;

    @FXML private TableView<GUICustomer> customerTable;
    @FXML private TableColumn<GUICustomer, ?> fn_col;
    @FXML private TableColumn<GUICustomer, ?> ln_col;
    @FXML private TableColumn<GUICustomer, ?> bd_col;
    @FXML private TableColumn<GUICustomer, ?> ad_col;
    @FXML private TableColumn<GUICustomer, ?> em_col;

    @FXML private Button addPassButton;
    @FXML private Button createPassButton;
    @FXML private Button rmvPassButton;

    @FXML private MenuItem viewDiscountsMenuItem; 
    @FXML private TextField transIDLabel; 

    public SearchFlightController(Supplier<SceneManager> sceneManagerSupplier, BusinessLogicAPI businessLogicAPI, PersistenceAPI persistenceAPI) {
        this.sceneManagerSupplier = sceneManagerSupplier;
        
        this.dbconnect = persistenceAPI.getDatabaseConnection();
        this.verifier = businessLogicAPI.getVerifier();
    }
    
    @FXML void viewDiscounts(ActionEvent event) { 
        sceneManagerSupplier.get().changeScene("Discounts");
    }

    @FXML void completeBooking(){
        if(customerTable.getItems().isEmpty()){
            notify("A booking has to have a Paying Customer.", true);
        } else if(customerTable.getItems().get(0).isEmpty()){
            notify("A Paying Customer cannot have empty fields.", true);
        } else if(transIDLabel.getText().isEmpty()){
            notify("Transaction ID cannot be empty.", true);
        } else if(!verifier.isEmailValid(customerTable.getItems().get(0).getEmail().getText())){
            notify("Email cannot be invalid.", true);
        } else {
            notify("Booking Complete!", false);
            GUICustomer guiCustomer = customerTable.getItems().get(0);
            Customer customer = new Customer(guiCustomer.getId(),guiCustomer.getEmail().getText(),guiCustomer.getAddress().getText());
            int booking_id = dbconnect.createBooking(Integer.valueOf(sceneManagerSupplier.get().getAccount().getId()), customer, fullPrice, Integer.valueOf(transIDLabel.getText()));
            System.out.println(booking_id);

            ArrayList<Ticket> tickets = new ArrayList<>();
            for(int flightID : currentFlight.getFlights()){
                for(GUIPassenger pass : passTable.getItems()){
                    ArrayList<StaticDiscount> discounts = new ArrayList<>();
                    ArrayList<String> extras = new ArrayList<>();
                    for (GUIStaticDiscount discount : pass.getDiscounts()) {
                        discounts.add(new StaticDiscount(discount.getDiscount().getText(), discount.getValue(), discount.getProcSelected()));
                    }
                    for(String extra : pass.featureList.split(","))
                        extras.add(extra);
                    tickets.add(new Ticket(pass.getId(), pass.getDocumentType(), pass.getDocumentID(), pass.getSeat().getText(), booking_id, flightID, discounts, extras));
                }
            }
            
            for (Ticket ticket : tickets) {
                int ticket_id = dbconnect.createTicket(booking_id, ticket);
                if(!ticket.discounts().isEmpty()) dbconnect.createStaticDiscount(ticket_id, ticket.discounts());
                if(!ticket.extras().isEmpty()) dbconnect.createExtra(ticket_id, ticket.extras());
            }
        }
    }

    @FXML
    void confirmBooking() {
        if(!passTable.getItems().isEmpty()){
            for(GUIPassenger pass : passTable.getItems()){
                if(pass.isEmpty()){
                    notify("Passengers cannot be left with empty fields: " + pass.getName().getText(), true);
                    return;
                }
            }
            notify("Finalizing Booking...", false);
            flightPrice.setText(String.valueOf(fullPrice) + " €");
            finTab.setDisable(false);
            tabPane.getSelectionModel().select(3);
            departAirport.setText(currentFlight.getSource());
            arriveAirport.setText(currentFlight.getDestination());
        } else {
            notify("Passenger Table cannot be Empty!", true);
        }
    }

    @FXML
    void discardBooking() {
        passTable.getItems().clear();
        finTab.setDisable(true);
        bookTab.setDisable(true);
        tabPane.getSelectionModel().select(1);
        notify("Booking discarded.", false);
    }

    @FXML
    void viewStops(){
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Stops.fxml"));
            Parent layout = loader.load();
            Scene scene = new Scene(layout, 438, 279);
            StopsController controller = loader.getController();

            controller.setup(currentFlight);

            stage.setScene(scene);
            stage.setTitle("Stops");
            stage.show();
        } catch (Exception e) {
            logger.display(e);
        }
    }

    @FXML
    void searchAccount() {

        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchAccount.fxml"));
            Parent layout = loader.load();
            Scene scene = new Scene(layout, 600, 346);
            SearchAccountController controller = loader.getController();

            controller.getCreateAccount().setOnAction(e -> {
                createAccount();
            });

            stage.setScene(scene);
            stage.setTitle("Search Account");
            stage.show();
        } catch (Exception e) {
            logger.display(e);
        }

    }

    @FXML
    void createAccount() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateAccount.fxml"));
            Parent layout = loader.load();
            Scene scene = new Scene(layout, 460, 315);

            stage.setMaxHeight(315);
            stage.setMinHeight(315);

            stage.setScene(scene);
            stage.setTitle("Create Account");
            stage.show();
        } catch (Exception e) {
            logger.display(e);
        }
    }

    @FXML
    void createPassenger(ActionEvent event) {
        try {
            createPassenger();
            notify("Passenger Added Correctly!", false);
        } catch (Exception e) {
            logger.display(e);
        }
    }

    @FXML
    void addPassenger(ActionEvent event) {
        findClient(null);
    }

    @FXML
    void removePassenger(ActionEvent event) {
        if (passTable.getSelectionModel().getSelectedIndex() != -1) {
            passTable.getItems().remove(passTable.getSelectionModel().getSelectedIndex());
            notify("Passenger Removed Correctly!", false);
        } else {
            notify("No Passenger Selected.", true);
        }
    }

    @FXML
    private Button chngSeatButton;
    @FXML
    private Button addXtraButton;

    @FXML
    private Label notifLabel;

    void notify(String text, Boolean error) {
        notifLabel.setText(text);
        if (error)
            notifLabel.setTextFill(Color.RED);
        else
            notifLabel.setTextFill(Color.BLACK);
    }

    void clearNotify() {
        notifLabel.setText("");
    }

    void findClient(String string) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("searchCustomer.fxml"));
            Parent layout = loader.load();
            Scene scene = new Scene(layout, 531, 270);

            TableView<GUIClient> table = (TableView<GUIClient>) scene.lookup("#table");
            Button selectButton = (Button) scene.lookup("#selectPass");
            Button searchButton = (Button) scene.lookup("#search");
            Button createPassButton = (Button) scene.lookup("#createPass");
            TextField textField = (TextField) scene.lookup("#textField");

            selectButton.setOnAction(e -> {
                if(!table.getSelectionModel().isEmpty()){
                    passTable.getItems().add(new GUIPassenger(table.getSelectionModel().getSelectedItem(), passTable, ppPrice));
                    updatePrice();
                    stage.close();
                }
            });

            createPassButton.setOnAction(e -> {
                try {
                    createPassenger();
                } catch (Exception e1) {
                    logger.display(e1);
                }
                stage.close();
            });

            if (string != null) {
                textField.setText(string);
                searchButton.fire();
            }

            stage.setScene(scene);
            stage.setTitle("Add Existing Passenger");
            stage.show();

            notify("Passenger Added Correctly!", false);
        } catch (Exception e) {
            logger.display(e);
        }
    }

    @FXML
    void logOut(){
        sceneManagerSupplier.get().changeScene("login");
    }

    void createPassenger() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("registerPassenger.fxml"));
            Parent layout = loader.load();
            Scene scene = new Scene(layout, 250, 245);
            RegisterPassengerController controller = loader.getController();

            controller.getSaveButton().setOnAction(e -> {
                if(controller.isReady()){
                    controller.saveClient();
                    stage.close();
                }
            });

            stage.setScene(scene);
            stage.setTitle("Register Client");
            stage.show();

        } catch (IOException e) {
            logger.display(e);
        }
    }

    void setupBooking(Flight flight) {
        try {
            currentFlight = flight;
            src_airport.setText(currentFlight.getSource());
            des_airport.setText(currentFlight.getDestination());
            dep_datetime.setText(currentFlight.getDeparutre(0));
            if(currentFlight.getAirportsSize()>2) arr_datetime.setText(currentFlight.getArrival(1));
            else arr_datetime.setText(currentFlight.getArrival(0));
            currentCost.setText("Cost: " + String.valueOf(fullPrice) + "€");
            perpersonCost.setText(currentFlight.getPrice() + " per person");
            ppPrice = currentFlight.getPrice();
        } catch (Exception e) {
            logger.display(e);
        }
    }

    

    @FXML
    void roundTripCheck(ActionEvent event) {
        returnHBox.setDisable(!rTripButton.isSelected());
    }

    @FXML
    void searchFlights(ActionEvent event) {
        if (depIATA_1.getEditor().getText().isEmpty() || arrIATA_1.getEditor().getText().isEmpty()
                || outDate.getValue() == null) {
            notify("PARAMETERS CAN'T BE EMPTY!", true);
        } else {
            if (verifier.inThePast(outDate.getValue())) {
                notify("DATE CANNOT BE IN THE PAST", true);
                return;
            }
            String dep = depIATA_1.getEditor().getText().split(" - ")[0];
            String arr = arrIATA_1.getEditor().getText().split(" - ")[0];
            ArrayList<Flight> routesList = dbconnect.findFlights(dep, arr, outDate.getValue().toString());
            notify("Found " + String.valueOf(routesList.size()) + " flights matching the criteria.", false);
            flightTable.getItems().clear();
            for (Flight flight : routesList) {
                GUIFlight guiFlight = new GUIFlight(flight, tabPane);

                guiFlight.getButton().setOnAction(e -> {
                    tabPane.getTabs().get(2).setDisable(false);
                    tabPane.getSelectionModel().select(2);

                    setupBooking(flight);
                });

                flightTable.getItems().add(guiFlight);
            }
            tabPane.getTabs().get(1).setDisable(false);
            tabPane.getSelectionModel().select(1);

        }
    }

    @FXML
    void addCustomer() throws IOException {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("searchCustomer.fxml"));
            Parent layout = loader.load();
            Scene scene = new Scene(layout, 531, 270);
            SearchCustomerController controller = loader.getController();

            controller.getSelectPass().setOnAction(e -> {
                customerTable.getItems().clear();
                customerTable.getItems().add(new GUICustomer(controller.getTable().getSelectionModel().getSelectedItem()));
                stage.close();
            });

            stage.setScene(scene);
            stage.setTitle("Select Customer");
            stage.show();
        } catch (Exception e) {
            logger.display(e);
        }
        
    }

    @FXML
    void registerCustomer() throws IOException {
        Stage stage = new Stage();
        Parent layout = FXMLLoader.load(getClass().getResource("registerCustomer.fxml"));
        Scene scene = new Scene(layout, 700, 270);

        stage.setScene(scene);
        stage.setTitle("Register new customer");
        stage.show();
    }

    void updatePrice() {
        int price = 0;
        for (GUIPassenger item : passTable.getItems()) {
            price += item.getPrice();
        }
        fullPrice = price;
        currentCost.setText("Cost: " + price + "€");

    }

    @FXML
    void initialize() {
        Account account = sceneManagerSupplier.get().getAccount();
        greetLabel.setText("Hello " + account.getFirstname() + " " + account.getLastname() + "!");
        String[] time = String.valueOf(LocalDateTime.now().withSecond(0).withNano(0)).split("T");
        detailLabel.setText("Account ID: " + account.getId() + "        Role: " + account.getRole()
                + "        Session Started: " + time[0] + " " + time[1]);

        depIATA_1.valueProperty().addListener((observable, oldValue, newValue) -> {
            depIATA_1.hide();
            depIATA_1.show();
            depIATA_1.getItems().clear();
            if (!depIATA_1.getEditor().getText().isEmpty() && newValue.length() < 20) {
                depIATA_1.getItems().addAll(dbconnect.getAirport(newValue));
            } else {
                depIATA_1.hide();
            }
        });

        arrIATA_1.valueProperty().addListener((observable, oldValue, newValue) -> {
            arrIATA_1.hide();
            arrIATA_1.show();
            arrIATA_1.getItems().clear();
            if (!arrIATA_1.getEditor().getText().isEmpty() && newValue.length() < 20) {
                arrIATA_1.getItems().addAll(dbconnect.getAirport(newValue));
            } else {
                arrIATA_1.hide();
            }
        });

        dep_col.setCellValueFactory(new PropertyValueFactory<>("source"));
        conn_col.setCellValueFactory(new PropertyValueFactory<>("connection"));
        arr_col.setCellValueFactory(new PropertyValueFactory<>("destination"));
        date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
        time_col.setCellValueFactory(new PropertyValueFactory<>("time"));
        cost_col.setCellValueFactory(new PropertyValueFactory<>("price"));
        book_col.setCellValueFactory(new PropertyValueFactory<>("button"));

        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        docs_col.setCellValueFactory(new PropertyValueFactory<>("docs"));
        dcid_col.setCellValueFactory(new PropertyValueFactory<>("dcid"));
        seat_col.setCellValueFactory(new PropertyValueFactory<>("seat"));
        xtra_col.setCellValueFactory(new PropertyValueFactory<>("xtra"));
        disc_col.setCellValueFactory(new PropertyValueFactory<>("disc"));
        price_col.setCellValueFactory(new PropertyValueFactory<>("price"));

        fn_col.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        ln_col.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        bd_col.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        ad_col.setCellValueFactory(new PropertyValueFactory<>("address"));
        em_col.setCellValueFactory(new PropertyValueFactory<>("email"));

        passTable.getProperties().addListener((Observable o) -> {
            updatePrice();
        });

        transIDLabel.addEventFilter(javafx.scene.input.KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("\\d")) {
                event.consume();
            }
        });

        System.out.println("Can't think of anything.");
    }
}
