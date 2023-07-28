package adbt171.mmproject;


import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;

//class for type Employee to be used in tableview and storage to database. filled with getters and setters and a constructor.
public class EmployeeClass {

    SimpleStringProperty forename;
    SimpleStringProperty surname;
    SimpleDoubleProperty rateOfPay;
    SimpleObjectProperty<Date> startDate;
    SimpleStringProperty NINum;
    SimpleStringProperty contactNum;
    SimpleStringProperty address;

    public EmployeeClass(String Forename, String Surname, double RateOfPay, Date StartDate, String niNum, String ContactNum, String Address) {
        forename = new SimpleStringProperty(Forename);
        surname = new SimpleStringProperty(Surname);
        rateOfPay = new SimpleDoubleProperty(RateOfPay);
        startDate = new SimpleObjectProperty<Date>(StartDate);
        NINum = new SimpleStringProperty(niNum);
        contactNum = new SimpleStringProperty(ContactNum);
        address = new SimpleStringProperty(Address);
    }

    public String getForename() {
        return forename.get();
    }

    public void setForename(String forename) {
        this.forename.set(forename);
    }

    public String getSurname() {
        return surname.get();
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public double getRateOfPay() {
        return rateOfPay.get();
    }

    public void setRateOfPay(double rateOfPay) {
        this.rateOfPay.set(rateOfPay);
    }

    public Date getStartDate() {
        return startDate.get();
    }

    public void setStartDate(Date startDate) {
        this.startDate.set(startDate);
    }

    public String getNINum() {
        return NINum.get();
    }

    public void setNINum(String NINum) {
        this.NINum.set(NINum);
    }

    public String getContactNum() {
        return contactNum.get();
    }

    public void setContactNum(String contactNum) {
        this.contactNum.set(contactNum);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }
}

