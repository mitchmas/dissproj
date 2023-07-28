package adbt171.mmproject;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;

//class for type Contracts to be used in tableview and storage to database. filled with getters and setters and a constructor.
public class ContractsClass {

    public ContractsClass(Integer ContractsID, String Contracts_company_name, String Contracts_company_address, String Contracts_primary_contact_name, String Contracts_primary_contact_number, Date Contracts_start_date, Date Contracts_end_date, Double Contracts_value, String Contracts_notes) {
        contractsID = new SimpleIntegerProperty(ContractsID);
        contracts_company_name = new SimpleStringProperty(Contracts_company_name);
        contracts_company_address = new SimpleStringProperty(Contracts_company_address);
        contracts_primary_contact_name = new SimpleStringProperty(Contracts_primary_contact_name);
        contracts_primary_contact_number = new SimpleStringProperty(Contracts_primary_contact_number);
        contracts_start_date = new SimpleObjectProperty<Date>(Contracts_start_date);
        contracts_end_date = new SimpleObjectProperty<Date>(Contracts_end_date);
        contracts_value = new SimpleDoubleProperty(Contracts_value);
        contracts_notes = new SimpleStringProperty(Contracts_notes);
    }

    SimpleIntegerProperty contractsID;
    SimpleStringProperty contracts_company_name;
    SimpleStringProperty contracts_company_address;
    SimpleStringProperty contracts_primary_contact_name;
    SimpleStringProperty contracts_primary_contact_number;
    SimpleObjectProperty<Date> contracts_start_date;
    SimpleObjectProperty<Date> contracts_end_date;
    SimpleDoubleProperty contracts_value;
    SimpleStringProperty contracts_notes;

    public int getContractsID() {
        return contractsID.get();
    }

    public void setContractsID(int contractsID) {
        this.contractsID.set(contractsID);
    }

    public String getContracts_company_name() {
        return contracts_company_name.get();
    }

    public void setContracts_company_name(String contracts_company_name) {
        this.contracts_company_name.set(contracts_company_name);
    }

    public String getContracts_company_address() {
        return contracts_company_address.get();
    }

    public void setContracts_company_address(String contracts_company_address) {
        this.contracts_company_address.set(contracts_company_address);
    }

    public String getContracts_primary_contact_name() {
        return contracts_primary_contact_name.get();
    }

    public void setContracts_primary_contact_name(String contracts_primary_contact_name) {
        this.contracts_primary_contact_name.set(contracts_primary_contact_name);
    }

    public String getContracts_primary_contact_number() {
        return contracts_primary_contact_number.get();
    }

    public void setContracts_primary_contact_number(String contacts_primary_contact_number) {
        this.contracts_primary_contact_number.set(contacts_primary_contact_number);
    }

    public Date getContracts_start_date() {
        return contracts_start_date.get();
    }

    public void setContracts_start_date(Date contracts_start_date) {
        this.contracts_start_date.set(contracts_start_date);
    }

    public Date getContracts_end_date() {
        return contracts_end_date.get();
    }

    public void setContracts_end_date(Date contracts_end_date) {
        this.contracts_end_date.set(contracts_end_date);
    }

    public double getContracts_value() {
        return contracts_value.get();
    }

    public void setContracts_value(double contracts_value) {
        this.contracts_value.set(contracts_value);
    }

    public String getContracts_notes() {
        return contracts_notes.get();
    }

    public void setContracts_notes(String contracts_notes) {
        this.contracts_notes.set(contracts_notes);
    }
}
