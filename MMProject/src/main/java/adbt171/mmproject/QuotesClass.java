package adbt171.mmproject;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;

//class for type Quotes to be used in tableview and storage to database. filled with getters and setters and a constructor.
public class QuotesClass {
    public QuotesClass(Integer QuotesID, String Quotes_company_name, Double Quotes_valuation, Date Quotes_date, String Quotes_primary_contact, String Quotes_contact_email, String Quotes_notes) {
        quotesID = new SimpleIntegerProperty(QuotesID);
        quotes_company_name = new SimpleStringProperty(Quotes_company_name);
        quotes_valuation = new SimpleDoubleProperty(Quotes_valuation);
        quotes_date = new SimpleObjectProperty<Date>(Quotes_date);
        quotes_primary_contact = new SimpleStringProperty(Quotes_primary_contact);
        quotes_contact_email = new SimpleStringProperty(Quotes_contact_email);
        quotes_notes = new SimpleStringProperty(Quotes_notes);
    }

    SimpleIntegerProperty quotesID;
    SimpleStringProperty quotes_company_name;
    SimpleDoubleProperty quotes_valuation;
    SimpleObjectProperty<Date> quotes_date;
    SimpleStringProperty quotes_primary_contact;
    SimpleStringProperty quotes_contact_email;
    SimpleStringProperty quotes_notes;

    public int getQuotesID() {
        return quotesID.get();
    }

    public void setQuotesID(int quotesID) {
        this.quotesID.set(quotesID);
    }

    public String getQuotes_company_name() {
        return quotes_company_name.get();
    }

    public void setQuotes_company_name(String quotes_company_name) {
        this.quotes_company_name.set(quotes_company_name);
    }

    public double getQuotes_valuation() {
        return quotes_valuation.get();
    }

    public void setQuotes_valuation(double quotes_valuation) {
        this.quotes_valuation.set(quotes_valuation);
    }

    public Date getQuotes_date() {
        return quotes_date.get();
    }

    public void setQuotes_date(Date quotes_date) {
        this.quotes_date.set(quotes_date);
    }

    public String getQuotes_primary_contact() {
        return quotes_primary_contact.get();
    }

    public void setQuotes_primary_contact(String quotes_primary_contact) {
        this.quotes_primary_contact.set(quotes_primary_contact);
    }

    public String getQuotes_contact_email() {
        return quotes_contact_email.get();
    }

    public SimpleStringProperty quotes_contact_emailProperty() {
        return quotes_contact_email;
    }

    public void setQuotes_contact_email(String quotes_contact_email) {
        this.quotes_contact_email.set(quotes_contact_email);
    }

    public String getQuotes_notes() {
        return quotes_notes.get();
    }

    public void setQuotes_notes(String quotes_notes) {
        this.quotes_notes.set(quotes_notes);
    }
}
