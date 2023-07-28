package adbt171.mmproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class MainHubController implements Initializable {

    //set FXID for interactive parts of the FXML files to be able to manipulate data/use buttons.
    @FXML
    private Button logout_button;

    @FXML
    private Label hello_msg;

    @FXML
    private Label employee_msg;

    @FXML
    private Label contracts_msg;

    @FXML
    private Label quotes_msg;

    @FXML
    private Label rota_msg;

    @FXML
    private Button go_contract_manager;

    @FXML
    private Button go_employee_manager;

    @FXML
    private Button go_quotes_manager;

    @FXML
    private Button go_rota_manager;

    @FXML
    private Button go_options;

    @FXML
    private Button go_home;

    @FXML
    private Button change_password;

    @FXML
    private Button change_mode;

    @FXML
    private TabPane mainHub_tab;

    @FXML
    public TableView<EmployeeClass> employee_table;

    @FXML
    private TableColumn<EmployeeClass, String> forenameCol;

    @FXML
    private TableColumn<EmployeeClass, String> surnameCol;

    @FXML
    private TableColumn<EmployeeClass, Integer> rateOfPayCol;

    @FXML
    private TableColumn<EmployeeClass, Date> startDateCol;

    @FXML
    private TableColumn<EmployeeClass, String> NINumCol;

    @FXML
    private TableColumn<EmployeeClass, String> contactNumCol;

    @FXML
    private TableColumn<EmployeeClass,String> addressCol;

    @FXML
    private Button add_employee;

    @FXML
    private Button refresh_employee;

    @FXML
    private TableView<QuotesClass> quotes_table;

    @FXML
    private TableColumn<QuotesClass,Integer> quotesIDCol;

    @FXML
    private TableColumn<QuotesClass,String> quotes_company_nameCol;

    @FXML
    private TableColumn<QuotesClass,Double> quotes_valuationCol;

    @FXML
    private TableColumn<QuotesClass,Date> quotes_dateCol;

    @FXML
    private TableColumn<QuotesClass,String> quotes_primary_contactCol;

    @FXML
    private TableColumn<QuotesClass,String> quotes_contact_emailCol;

    @FXML
    private TableColumn<QuotesClass,String> quotes_notesCol;

    @FXML
    private Button add_quote;

    @FXML
    private Button refresh_quotes;

    @FXML
    private TableView<ContractsClass> contracts_table;

    @FXML
    private TableColumn<ContractsClass,Integer> contractsIDCol;

    @FXML
    private TableColumn<ContractsClass,String> contracts_company_nameCol;

    @FXML
    private TableColumn<ContractsClass,String> contracts_company_addressCol;

    @FXML
    private TableColumn<ContractsClass,String> contracts_primary_contact_nameCol;

    @FXML
    private TableColumn<ContractsClass,String> contracts_primary_contact_numberCol;

    @FXML
    private TableColumn<ContractsClass,Date> contracts_start_dateCol;

    @FXML
    private TableColumn<ContractsClass,Date> contracts_end_dateCol;

    @FXML
    private TableColumn<ContractsClass,Double> contracts_valueCol;

    @FXML
    private TableColumn<QuotesClass,String> contracts_notesCol;

    @FXML
    private Button add_contract;

    @FXML
    private Button refresh_contracts;

    @FXML
    private TableView<RotaClass> rota_table;

    @FXML
    private TableColumn<RotaClass,Integer> rotaIDCol;

    @FXML
    private TableColumn<RotaClass,String> rota_typeCol;

    @FXML
    private TableColumn<RotaClass,String> rota_nameCol;

    @FXML
    private TableColumn<RotaClass,Date> rota_start_dateCol;

    @FXML
    private TableColumn<RotaClass,Date> rota_end_dateCol;

    @FXML
    private TableColumn<RotaClass,String> rota_start_timeCol;

    @FXML
    private TableColumn<RotaClass,String> rota_end_timeCol;

    @FXML
    private TableColumn<RotaClass,String> rota_commentsCol;

    @FXML
    private Button add_rota;

    @FXML
    private Button refresh_rota;

    @FXML
    private Button clear_select_rota;

    @FXML
    private ComboBox<String> event_selector;

    //create options for combo box
    ObservableList<String> eventList = FXCollections.observableArrayList("*","shift","absence","meeting");

    //create null states for each type of potential class called/selected and also the current sessions logged in user's username
    private EmployeeClass employee;
    private QuotesClass quote;
    private ContractsClass contract;
    private RotaClass rota;
    private String homeUsername;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //create an array of tablecolumns for each class to create observableArraylists and ininitalise all cell values for each respective tables in the system using the DBConn method displayTable
        TableColumn[] records_employee = {forenameCol,surnameCol,rateOfPayCol,startDateCol,NINumCol,contactNumCol,addressCol};
        TableColumn[] records_quotes = {quotesIDCol,quotes_company_nameCol,quotes_valuationCol,quotes_dateCol,quotes_primary_contactCol,quotes_contact_emailCol,quotes_notesCol};
        TableColumn[] records_contracts = {contractsIDCol,contracts_company_nameCol,contracts_company_addressCol,contracts_primary_contact_nameCol,contracts_primary_contact_numberCol,contracts_start_dateCol,contracts_end_dateCol,contracts_valueCol,contracts_notesCol};
        TableColumn[] records_rota = {rotaIDCol,rota_typeCol,rota_nameCol,rota_start_dateCol,rota_end_dateCol,rota_start_timeCol,rota_end_timeCol,rota_commentsCol};
        ObservableList<EmployeeClass> EmployeeList = FXCollections.observableArrayList();
        ObservableList<QuotesClass> QuotesList = FXCollections.observableArrayList();
        ObservableList<ContractsClass> ContractsList = FXCollections.observableArrayList();
        ObservableList<RotaClass> RotaList = FXCollections.observableArrayList();
        quotes_table.setEditable(true);
        employee_table.setEditable(true);
        contracts_table.setEditable(true);
        rota_table.setEditable(true);
        DBConn.displayTable(records_employee);
        DBConn.displayTable(records_quotes);
        DBConn.displayTable(records_contracts);
        DBConn.displayTable(records_rota);
        setHomeMsg();

        //exit users current session, takes them back to the login page.
        logout_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBConn.changeScene(actionEvent, "login.fxml","Log in!",null);
            }
        });

        //the following refresh buttons update the table by clearing the table and adding each individual row back recursively using DBConn.
        refresh_employee.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBConn.refreshTable(EmployeeList,"employeedb",employee_table);
            }
        });

        refresh_quotes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBConn.refreshTable(QuotesList,"quotesdb",quotes_table);
            }
        });

        refresh_contracts.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) { DBConn.refreshTable(ContractsList,"contractsdb",contracts_table); }
        });

        refresh_rota.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) { DBConn.refreshRotaTable(RotaList,event_selector.getValue(),rota_table); }
        });

        //each go button takes the user to a different tabview which has a table present (minus home and options) and thus each time a tab with a table is viewed, said table must be updated on arrival to the pane to keep tableviews up to date with added entries.
        go_home.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainHub_tab.getSelectionModel().select(0);
                setHomeMsg();
            }
        });

        go_employee_manager.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainHub_tab.getSelectionModel().select(1);
                DBConn.refreshTable(EmployeeList,"employeedb",employee_table);
            }
        });

        go_contract_manager.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainHub_tab.getSelectionModel().select(2);
                DBConn.refreshTable(ContractsList,"contractsdb",contracts_table);
            }
        });

        go_quotes_manager.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainHub_tab.getSelectionModel().select(3);
                DBConn.refreshTable(QuotesList,"quotesdb",quotes_table);
            }
        });

        //creates and adds the multiple options for the combobox for the user to select their view type when they hit the rota refresh table button.
        go_rota_manager.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainHub_tab.getSelectionModel().select(4);
                event_selector.setItems(eventList);
                event_selector.getSelectionModel().selectFirst();
                DBConn.refreshRotaTable(RotaList,"*",rota_table);
            }
        });

        go_options.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainHub_tab.getSelectionModel().select(5);
            }
        });

        //each add button calls the method popUpScreen with a differing fxml file for the location of the file to be displayed (connected to the table controller class)
        add_employee.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                popUpScreen("addEmployee");
            }
        });

        add_quote.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                popUpScreen("addQuote");
            }
        });

        add_contract.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) { popUpScreen("addContract");}
        });

        change_password.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                popUpScreen("changePassword");
            }
        });

        add_rota.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) { popUpScreen("addRota"); }
        });

        clear_select_rota.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) { popUpScreen("removeRota"); }
        });
    }

    //on selecting a row inside a table, sets the previously null objects of the tables class type to the values of the row to pull up data and display in the add/edit/remove screens.
    @FXML
    void rowSelected(MouseEvent event) {
        if (event.getSource() == employee_table) {
            employee = employee_table.getSelectionModel().getSelectedItem();
        } else if (event.getSource() == quotes_table) {
            quote = quotes_table.getSelectionModel().getSelectedItem();
        } else if (event.getSource() == contracts_table) {
            contract = contracts_table.getSelectionModel().getSelectedItem();
        } else if (event.getSource() == rota_table) {
            rota = rota_table.getSelectionModel().getSelectedItem();
        }
    }

    //adapted from https://www.youtube.com/watch?v=ltX5AtW9v30
    public void setUsername(String username) {
        hello_msg.setText ("hello, "+username+".");
        homeUsername = username;
    }

    //simply edits messages when the user sees the home page to show the count of rows in each table stored.
    public void setHomeMsg() {
        employee_msg.setText("Current number of employees: "+DBConn.getCount("employeedb"));
        contracts_msg.setText("Current number of contracts: "+DBConn.getCount("contractsdb"));
        quotes_msg.setText("Current number of quotes: "+DBConn.getCount("quotesdb"));
        rota_msg.setText("Current number of rota entries: "+DBConn.getCount("rotadb"));

    }

    private void popUpScreen(String fxmlFile) {
        //load fxml file taken as parameter, check if the object of the same type as the table where this method is called from is set to null.
        //if null, do not edit the text fields for the associated fxml file.
        //if not null, set the text fields to contain the selected row data for easier editing/removal.
        //afterwards, set all class objects used to listen for row selection to null to ensure only the selected rows display for the correct add/edit/remove screens when calling their respective fxml files.
        //finish by creating new stage and displaying which is closed at the end of every button operation in the fxml files handled by table controller.
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlFile+".fxml"));
            loader.load();
            TableController tableController = loader.getController();
            if (fxmlFile == "addEmployee") {
                if (!(this.employee == null)) {
                    tableController.setEmployeeText(employee.getForename(),employee.getSurname(),String.valueOf(employee.getRateOfPay()),String.valueOf(employee.getStartDate()),employee.getNINum(),employee.getContactNum(),employee.getAddress());
                }
            } else if (fxmlFile == "addQuote") {
                if (!(this.quote == null)) {
                    tableController.setQuoteText(String.valueOf(quote.getQuotesID()),quote.getQuotes_company_name(),String.valueOf(quote.getQuotes_valuation()),String.valueOf(quote.getQuotes_date()),quote.getQuotes_primary_contact(),quote.getQuotes_contact_email(),quote.getQuotes_notes());
                }
            } else if (fxmlFile == "addContract") {
                if (!(this.contract == null)) {
                    tableController.setContractText(String.valueOf(contract.getContractsID()),contract.getContracts_company_name(),contract.getContracts_company_address(),contract.getContracts_primary_contact_name(),contract.getContracts_primary_contact_number(),String.valueOf(contract.getContracts_start_date()),String.valueOf(contract.getContracts_end_date()),String.valueOf(contract.getContracts_value()),contract.getContracts_notes());
                }
            } else if (fxmlFile == "changePassword") {
                tableController.setUsername(homeUsername);
            } else if (fxmlFile == "addRota") {
                if (!(this.rota == null)) {
                    tableController.setRotaText(String.valueOf(rota.getRotaID()),rota.getRota_type(),rota.getRota_name(),String.valueOf(rota.getRota_start_date()),String.valueOf(rota.getRota_end_date()),rota.getRota_start_time(),rota.getRota_end_time(),rota.getRota_comments());
                }
            }
            employee = null;
            quote = null;
            contract = null;
            rota = null;
            Stage stage = new Stage();
            Scene scene = new Scene(loader.getRoot());
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
