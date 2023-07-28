package adbt171.mmproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TableController implements Initializable {

// load interactable buttons/textfields.
    @FXML
    private TextField employee_forename;
    @FXML
    private TextField employee_surname;
    @FXML
    private TextField employee_pay_rate;
    @FXML
    private TextField employee_start_date;
    @FXML
    private TextField employee_ni_num;
    @FXML
    private TextField employee_contact_num;
    @FXML
    private TextField employee_address;
    @FXML
    private Button employee_add;
    @FXML
    private Button employee_edit;
    @FXML
    private Button employee_remove;
    @FXML
    private TextField quote_ID;
    @FXML
    private TextField quote_company_name;
    @FXML
    private TextField quote_valuation;
    @FXML
    private TextField quote_date;
    @FXML
    private TextField quote_contact_name;
    @FXML
    private TextField quote_contact_email;
    @FXML
    private TextField quotes_notes;
    @FXML
    private Button quote_add;
    @FXML
    private Button quote_edit;
    @FXML
    private Button quote_remove;
    @FXML
    private TextField contractID;
    @FXML
    private TextField contract_company_name;
    @FXML
    private TextField contract_company_address;
    @FXML
    private TextField contract_primary_contact_name;
    @FXML
    private TextField contract_primary_contact_number;
    @FXML
    private TextField contract_start_date;
    @FXML
    private TextField contract_end_date;
    @FXML
    private TextField contract_value;
    @FXML
    private TextField contract_notes;
    @FXML
    private Button contract_add;
    @FXML
    private Button contract_edit;
    @FXML
    private Button contract_remove;
    @FXML
    private Button change_password;
    @FXML
    private TextField old_password;
    @FXML
    private TextField new_password;
    @FXML
    private TextField new_password_confirmed;
    @FXML
    private TextField rotaID;
    @FXML
    private TextField rota_event;
    @FXML
    private TextField rota_name;
    @FXML
    private TextField rota_start_date;
    @FXML
    private TextField rota_end_date;
    @FXML
    private TextField rota_start_time;
    @FXML
    private TextField rota_end_time;
    @FXML
    private TextField rota_comments;
    @FXML
    private Button rota_add;
    @FXML
    private Button rota_edit;
    @FXML
    private Button rota_remove;
    @FXML
    private TextField remove_entries_date;
    @FXML
    private TextField remove_entries_type;
    @FXML
    private Button remove_entries;

    private String username;

    //no set in initialise as since this controller is used for multiple different fxml screens
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    //to prevent calling methods in initialize that may be set to null as they appear in other fxml files than the one called, added a listener for mouseevents on click
    // so that using an identifier for the fxml value of the button pressed takes to the appropriate add/edit/remove DBConn method for data and class handling.
    //finishes by closing the screen that was loaded for sleeker design.
    @FXML
    void edit_record(MouseEvent event) {
        if (event.getSource() == employee_add) {
            DBConn.addEmployee(employee_forename.getText(),employee_surname.getText(),employee_pay_rate.getText(),employee_start_date.getText(),employee_ni_num.getText(),employee_contact_num.getText(),employee_address.getText(),false);
        } else if (event.getSource() == employee_edit) {
            DBConn.addEmployee(employee_forename.getText(),employee_surname.getText(),employee_pay_rate.getText(),employee_start_date.getText(),employee_ni_num.getText(),employee_contact_num.getText(),employee_address.getText(),true);
        } else if (event.getSource() == employee_remove) {
            DBConn.removeRow("employeedb","NINum", employee_ni_num.getText());
        } else if (event.getSource() == quote_add) {
            DBConn.addQuote(quote_ID.getText(),quote_company_name.getText(),quote_valuation.getText(),quote_date.getText(),quote_contact_name.getText(),quote_contact_email.getText(),quotes_notes.getText(),false);
        } else if (event.getSource() == quote_edit) {
            DBConn.addQuote(quote_ID.getText(), quote_company_name.getText(), quote_valuation.getText(), quote_date.getText(), quote_contact_name.getText(), quote_contact_email.getText(), quotes_notes.getText(), true);
        } else if (event.getSource() == quote_remove) {
            DBConn.removeRow("quotesdb","quotesID",quote_ID.getText());
        } else if (event.getSource() == contract_add) {
            DBConn.addContract(contractID.getText(),contract_company_name.getText(),contract_company_address.getText(),contract_primary_contact_name.getText(),contract_primary_contact_number.getText(),contract_start_date.getText(),contract_end_date.getText(),contract_value.getText(),contract_notes.getText(),false);
        } else if (event.getSource() == contract_edit) {
            DBConn.addContract(contractID.getText(),contract_company_name.getText(),contract_company_address.getText(),contract_primary_contact_name.getText(),contract_primary_contact_number.getText(),contract_start_date.getText(),contract_end_date.getText(),contract_value.getText(),contract_notes.getText(),true);
        } else if (event.getSource() == contract_remove) {
            DBConn.removeRow("contractsdb","contractsID",contractID.getText());
        } else if (event.getSource() == change_password) {
            DBConn.changePassword(username,old_password.getText(),new_password.getText(),new_password_confirmed.getText());
        } else if (event.getSource() == rota_add) {
            DBConn.addRota(rotaID.getText(),rota_event.getText(),rota_name.getText(),rota_start_date.getText(),rota_end_date.getText(),rota_start_time.getText(),rota_end_time.getText(),rota_comments.getText(),false);
        } else if (event.getSource() == rota_edit) {
            DBConn.addRota(rotaID.getText(),rota_event.getText(),rota_name.getText(),rota_start_date.getText(),rota_end_date.getText(),rota_start_time.getText(),rota_end_time.getText(),rota_comments.getText(),true);
        } else if (event.getSource() == rota_remove) {
            DBConn.removeRow("rotadb","rotaID",rotaID.getText());
        } else if (event.getSource() == remove_entries) {
            DBConn.removeEntries(remove_entries_date.getText(),remove_entries_type.getText());
        }
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }


    //setter for when an entry of employee table is selected to display the values contained within the row for quicker editing/deletion.
    public void setEmployeeText(String forename, String surname, String payRate, String startDate, String NINum, String contactNum, String Address) {
        employee_forename.setText(forename);
        employee_surname.setText(surname);
        employee_pay_rate.setText(payRate);
        employee_start_date.setText(startDate);
        employee_ni_num.setText(NINum);
        employee_contact_num.setText(contactNum);
        employee_address.setText(Address);
    }

    //setter for when an entry of quotes table is selected to display the values contained within the row for quicker editing/deletion.
    public void setQuoteText(String ID, String companyName, String Valuation, String quoteDate, String contactName, String contactEmail, String quotesNotes) {
        quote_ID.setText(ID);
        quote_company_name.setText(companyName);
        quote_valuation.setText(Valuation);
        quote_date.setText(quoteDate);
        quote_contact_name.setText(contactName);
        quote_contact_email.setText(contactEmail);
        quotes_notes.setText(quotesNotes);
    }

    //setter for when an entry of contracts table is selected to display the values contained within the row for quicker editing/deletion.
    public void setContractText(String ID, String companyName, String companyAddress, String contactName, String contactNum, String startDate, String endDate, String value, String notes) {
        contractID.setText(ID);
        contract_company_name.setText(companyName);
        contract_company_address.setText(companyAddress);
        contract_primary_contact_name.setText(contactName);
        contract_primary_contact_number.setText(contactNum);
        contract_start_date.setText(startDate);
        contract_end_date.setText(endDate);
        contract_value.setText(value);
        contract_notes.setText(notes);
    }

    //setter for when an entry of rota table is selected to display the values contained within the row for quicker editing/deletion.
    public void setRotaText(String ID, String event, String name, String startDate, String endDate, String startTime, String endTime, String comments) {
        rotaID.setText(ID);
        rota_event.setText(event);
        rota_name.setText(name);
        rota_start_date.setText(startDate);
        rota_end_date.setText(endDate);
        rota_start_time.setText(startTime);
        rota_end_time.setText(endTime);
        rota_comments.setText(comments);
    }

    //set username to be used when calling changePassword to ensure the correct usernme is looked up by the system for the CURRENT logged in user.
    public void setUsername(String username) {
        this.username = username;
    }
}
