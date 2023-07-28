package adbt171.mmproject;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;

//class for type Rota to be used in tableview and storage to database. filled with getters and setters and a constructor.
public class RotaClass {

    SimpleIntegerProperty rotaID;
    SimpleStringProperty rota_type;
    SimpleStringProperty rota_name;
    SimpleObjectProperty<Date> rota_start_date;
    SimpleObjectProperty<Date> rota_end_date;
    SimpleStringProperty rota_start_time;
    SimpleStringProperty rota_end_time;
    SimpleStringProperty rota_comments;

    public String getRota_type() {
        return rota_type.get();
    }

    public void setRota_type(String rota_type) {
        this.rota_type.set(rota_type);
    }

    public int getRotaID() {
        return rotaID.get();
    }

    public void setRotaID(int rotaID) {
        this.rotaID.set(rotaID);
    }

    public String getRota_name() {
        return rota_name.get();
    }

    public void setRota_name(String rota_name) {
        this.rota_name.set(rota_name);
    }

    public Date getRota_start_date() {
        return rota_start_date.get();
    }

    public void setRota_start_date(Date rota_start_date) {
        this.rota_start_date.set(rota_start_date);
    }

    public Date getRota_end_date() {
        return rota_end_date.get();
    }

    public void setRota_end_date(Date rota_end_date) {
        this.rota_end_date.set(rota_end_date);
    }

    public String getRota_start_time() {
        return rota_start_time.get();
    }

    public void setRota_start_time(String rota_start_time) {
        this.rota_start_time.set(rota_start_time);
    }

    public String getRota_end_time() {
        return rota_end_time.get();
    }

    public void setRota_end_time(String rota_end_time) {
        this.rota_end_time.set(rota_end_time);
    }

    public String getRota_comments() {
        return rota_comments.get();
    }

    public void setRota_comments(String rota_comments) {
        this.rota_comments.set(rota_comments);
    }

    public RotaClass(Integer RotaID, String Rota_type, String Rota_name, Date Rota_start_date, Date Rota_end_date, String Rota_start_time, String Rota_end_time, String Rota_comments) {
        rotaID = new SimpleIntegerProperty(RotaID);
        rota_type = new SimpleStringProperty(Rota_type);
        rota_name = new SimpleStringProperty(Rota_name);
        rota_start_date = new SimpleObjectProperty<Date>(Rota_start_date);
        rota_end_date = new SimpleObjectProperty<Date>(Rota_end_date);
        rota_start_time = new SimpleStringProperty(Rota_start_time);
        rota_end_time = new SimpleStringProperty(Rota_end_time);
        rota_comments = new SimpleStringProperty(Rota_comments);
    }
}
