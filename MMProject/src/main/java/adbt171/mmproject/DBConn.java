package adbt171.mmproject;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

//taking an adaptation from https://www.youtube.com/watch?v=ltX5AtW9v30, I have made sure to close all connections at the end of every method. This is to prevent a cluster of connections forming and ensures that connections are only formed when absolutely necessary.

public class DBConn {
    //create variables for the database link with jdbc for easier implementation in other pc's
    private static String dbUrl = "jdbc:mysql://localhost:3306/dbschema";
    private static String dbName = "root";
    private static String dbPass = "root";


    //implemented from https://www.youtube.com/watch?v=ltX5AtW9v30, with adaptations for screen size fixation and added robustness
    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username) {
        Parent root = null;

        if (username != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DBConn.class.getResource(fxmlFile));
                root = loader.load();
                MainHubController mainHubController = loader.getController();
                mainHubController.setUsername(username);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(DBConn.class.getResource(fxmlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        if (fxmlFile == "signup.fxml" || fxmlFile == "login.fxml") {
            stage.setScene(new Scene(root,600,400));
        } else {
            stage.setScene(new Scene(root, 900, 600));
        }
        stage.centerOnScreen();

        stage.show();
    }

    //implemented from https://www.youtube.com/watch?v=ltX5AtW9v30. adapted error messages for easy user troubleshooting.
    public static void signUp(ActionEvent event, String username, String password, String password_confirm) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckExisting = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(dbUrl,dbName,dbPass);
            psCheckExisting = connection.prepareStatement("SELECT * FROM usersdb WHERE username = ?");
            psCheckExisting.setString(1,username);
            resultSet = psCheckExisting.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("User already exists.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR! Please try a different username.");
                alert.show();
            } else if (!(password.equals(password_confirm))) {
                System.out.println("Passwords do not match.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR! Please ensure your passwords are matching.");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO usersdb (username , password) VALUES (?,?)");
                psInsert.setString(1,username);
                psInsert.setString(2,password);
                psInsert.executeUpdate();
                changeScene(event,"mainHub.fxml","CRM & ROTA Management Hub",username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckExisting != null) {
                try {
                    psCheckExisting.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //implemented from https://www.youtube.com/watch?v=ltX5AtW9v30. adapted error messages for easy user troubleshooting
    public static void logIn(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(dbUrl,dbName,dbPass);
            preparedStatement = connection.prepareStatement("SELECT password FROM usersdb WHERE username = ?");
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in the database.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR! Provided details are incorrect.");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String foundPass = resultSet.getString("password");
                    if (foundPass.equals(password)) {
                        changeScene(event, "mainHub.fxml","CRM & ROTA Management Hub",username);
                    } else {
                        System.out.println("Passwords not matching.");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("ERROR! Provided details are incorrect.");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*created a loop for the creation instance of all tables, prevents unnecessary repeated methods with the same functionality by taking in an array of columns which I strategically named
    to be able to remove the Col name to add the correct sql column names to the class values that need to be inserted for a cell value in all the instantiated tables on the initialisation of the controller for the main hub.*/
    public static void displayTable(TableColumn[] cols) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUrl,dbName,dbPass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < cols.length; i++) {
            String columnName = cols[i].getId();
            columnName = columnName.replace("Col","");
            cols[i].setCellValueFactory(new PropertyValueFactory<>(columnName));
        }
    }

    // created baseline for all (minus rota) table refreshing, reduces the amount of repeated code by initialising the connection and diverting to methods depending on the parameter of the table name for unique column data insertion.
    public static void refreshTable(ObservableList list, String tableName, TableView table) {
        Connection connection = null;
        PreparedStatement psSelect = null;
        ResultSet resultSet = null;
        list.clear();
        try {
            connection = DriverManager.getConnection(dbUrl,dbName,dbPass);
            psSelect = connection.prepareStatement("SELECT * FROM " + tableName);
            resultSet = psSelect.executeQuery();
            if (tableName == "employeedb") {
                refreshEmployee(list,table,resultSet);
            } else if (tableName == "quotesdb") {
                refreshQuotes(list,table,resultSet);
            } else if (tableName == "contractsdb") {
                refreshContracts(list,table,resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psSelect != null) {
                try {
                    psSelect.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //loops through complete search through the MySQL database to create objects of class employee to insert into the tableview initialised by the main controller.
    private static void refreshEmployee (ObservableList<EmployeeClass> list, TableView<EmployeeClass> table, ResultSet resultSet) {
        try {
            while(resultSet.next()) {
            list.add(new EmployeeClass(
                    resultSet.getString("forename"),
                    resultSet.getString("surname"),
                    resultSet.getDouble("rateOfPay"),
                    resultSet.getDate("startDate"),
                    resultSet.getString("NINum"),
                    resultSet.getString("contactNum"),
                    resultSet.getString("address")));
            table.setItems(list);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //loops through complete search through the MySQL database to create objects of class quotes to insert into the tableview initialised by the main controller.
    private static void refreshQuotes (ObservableList<QuotesClass> list, TableView<QuotesClass> table, ResultSet resultSet) {
        try {
            while(resultSet.next()) {
                list.add(new QuotesClass(
                        resultSet.getInt("quotesID"),
                        resultSet.getString("quotes_company_name"),
                        resultSet.getDouble("quotes_valuation"),
                        resultSet.getDate("quotes_date"),
                        resultSet.getString("quotes_primary_contact"),
                        resultSet.getString("quotes_contact_email"),
                        resultSet.getString("quotes_notes")));
                table.setItems(list);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //loops through complete search through the MySQL database to create objects of class contracts to insert into the tableview initialised by the main controller.
    private static void refreshContracts (ObservableList<ContractsClass> list, TableView<ContractsClass> table, ResultSet resultSet) {
        try {
            while(resultSet.next()) {
                list.add(new ContractsClass(
                        resultSet.getInt("contractsID"),
                        resultSet.getString("contracts_company_name"),
                        resultSet.getString("contracts_company_address"),
                        resultSet.getString("contracts_primary_contact_name"),
                        resultSet.getString("contracts_primary_contact_number"),
                        resultSet.getDate("contracts_start_date"),
                        resultSet.getDate("contracts_end_date"),
                        resultSet.getDouble("contracts_value"),
                        resultSet.getString("contracts_notes")));
                table.setItems(list);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //different approach with refreshing rota table as I desired to have the rota table organisable by type of event inserted,
    // this means depending on the condition applied, a different contents of the tableview for class type rota should appear.
    public static void refreshRotaTable(ObservableList<RotaClass> list,String conditionTitle, TableView table) {
        Connection connection = null;
        PreparedStatement psSelect = null;
        ResultSet resultSet = null;
        list.clear();
        try {
            connection = DriverManager.getConnection(dbUrl,dbName,dbPass);
            if (conditionTitle == "*") {
                psSelect = connection.prepareStatement("SELECT * FROM rotadb");
            } else {
                psSelect = connection.prepareStatement("SELECT * FROM rotadb WHERE rota_type = ?");
                psSelect.setString(1, conditionTitle);
            }
            resultSet = psSelect.executeQuery();
            while (resultSet.next()) {
                list.add(new RotaClass(
                        resultSet.getInt("rotaID"),
                        resultSet.getString("rota_type"),
                        resultSet.getString("rota_name"),
                        resultSet.getDate("rota_start_date"),
                        resultSet.getDate("rota_end_date"),
                        resultSet.getString("rota_start_time"),
                        resultSet.getString("rota_end_time"),
                        resultSet.getString("rota_comments")));
                table.setItems(list);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psSelect != null) {
                try {
                    psSelect.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //error catching for completed form checks and preventing overriting primary keys and prevents the attempt to add items with duplicate primary keys.
    //boolean value allows the sql to switch depending on whether the add or edit button calls the method to prevent a large sum of duplicate code for class employee
    public static void addEmployee(String forename, String surname, String payRate, String startDate, String NINum, String contactNum, String Address,boolean updatestatus) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckExisting = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(dbUrl,dbName,dbPass);
            psCheckExisting = connection.prepareStatement("SELECT * FROM employeedb WHERE NINum = ?");
            psCheckExisting.setString(1,NINum);
            resultSet = psCheckExisting.executeQuery();
            if (resultSet.isBeforeFirst() && (updatestatus == false)) {
                System.out.println("Employee already exists.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR! An employee with this NI Number already exists.");
                alert.show();
            } else if (forename.trim().isEmpty() || surname.trim().isEmpty() || NINum.trim().isEmpty() || payRate.trim().isEmpty() || startDate.trim().isEmpty() || contactNum.trim().isEmpty() || Address.trim().isEmpty()) {
                System.out.println("Please fill in ALL fields.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR! Please ensure you fill all required fields.");
                alert.show();
            } else {
                if(updatestatus == false) {
                    psInsert = connection.prepareStatement("INSERT INTO employeedb (forename , surname , rateOfPay , startDate , NINum , contactNum , address) VALUES (?,?,?,?,?,?,?)");
                } else if (updatestatus == true) {
                    psInsert = connection.prepareStatement("UPDATE employeedb SET forename = ?, surname = ?, rateOfPay = ?, startDate = ?, NINum = ?, contactNum = ?, address = ? WHERE NINum = ? ");
                    psInsert.setString(8,NINum);
                }
                psInsert.setString(1,forename);
                psInsert.setString(2,surname);
                psInsert.setDouble(3,Double.valueOf(payRate));
                psInsert.setDate(4,java.sql.Date.valueOf(startDate));
                psInsert.setString(5,NINum);
                psInsert.setString(6,contactNum);
                psInsert.setString(7,Address);
                psInsert.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckExisting != null) {
                try {
                    psCheckExisting.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //error catching for completed form checks and preventing overriting primary keys and prevents the attempt to add items with duplicate primary keys.
    //boolean value allows the sql to switch depending on whether the add or edit button calls the method to prevent a large sum of duplicate code for class quotes
    public static void addQuote(String quotesID, String quotes_company_name, String quotes_valuation, String quotes_date, String quotes_primary_contact, String quotes_contact_email, String quotes_notes,boolean updatestatus) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckExisting = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(dbUrl,dbName,dbPass);
            psCheckExisting = connection.prepareStatement("SELECT * FROM quotesdb WHERE quotesID = ?");
            psCheckExisting.setString(1,quotesID);
            resultSet = psCheckExisting.executeQuery();
            if (resultSet.isBeforeFirst() && (updatestatus == false)) {
                System.out.println("Quote ID already exists.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR! A quote with this ID already exists.");
                alert.show();
            } else if (quotesID.trim().isEmpty() || quotes_company_name.trim().isEmpty() || quotes_valuation.trim().isEmpty() || quotes_date.trim().isEmpty() || quotes_primary_contact.trim().isEmpty() || quotes_contact_email.trim().isEmpty() || quotes_notes.trim().isEmpty()) {
                System.out.println("Please fill in ALL fields.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR! Please ensure you fill all required fields.");
                alert.show();
            } else {
                if(updatestatus == false) {
                    psInsert = connection.prepareStatement("INSERT INTO quotesdb (quotesID , quotes_company_name, quotes_valuation , quotes_date , quotes_primary_contact , quotes_contact_email , quotes_notes) VALUES (?,?,?,?,?,?,?)");
                } else if (updatestatus == true) {
                    psInsert = connection.prepareStatement("UPDATE quotesdb SET quotesID = ?, quotes_company_name = ?, quotes_valuation = ?, quotes_date = ?, quotes_primary_contact = ?, quotes_contact_email = ?, quotes_notes = ? WHERE quotesID = ? ");
                    psInsert.setInt(8,Integer.parseInt(quotesID));
                }
                psInsert.setString(1,quotesID);
                psInsert.setString(2,quotes_company_name);
                psInsert.setDouble(3,Double.valueOf(quotes_valuation));
                psInsert.setDate(4,java.sql.Date.valueOf(quotes_date));
                psInsert.setString(5,quotes_primary_contact);
                psInsert.setString(6,quotes_contact_email);
                psInsert.setString(7,quotes_notes);
                psInsert.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckExisting != null) {
                try {
                    psCheckExisting.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //error catching for completed form checks and preventing overriting primary keys and prevents the attempt to add items with duplicate primary keys.
    //boolean value allows the sql to switch depending on whether the add or edit button calls the method to prevent a large sum of duplicate code for class contracts
    public static void addContract(String contractsID, String contracts_company_name, String contracts_company_address, String contracts_primary_contact_name, String contracts_primary_contact_number, String contracts_start_date, String contracts_end_date,String contracts_value,String contracts_notes,boolean updatestatus) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckExisting = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(dbUrl,dbName,dbPass);
            psCheckExisting = connection.prepareStatement("SELECT * FROM contractsdb WHERE contractsID = ?");
            psCheckExisting.setString(1,contractsID);
            resultSet = psCheckExisting.executeQuery();
            if (resultSet.isBeforeFirst() && (updatestatus == false)) {
                System.out.println("Contract ID already exists.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR! A quote with this ID already exists.");
                alert.show();
            } else if (contractsID.trim().isEmpty() || contracts_company_name.trim().isEmpty() || contracts_company_address.trim().isEmpty() || contracts_primary_contact_name.trim().isEmpty() || contracts_primary_contact_number.trim().isEmpty() || contracts_start_date.trim().isEmpty() || contracts_end_date.trim().isEmpty() || contracts_value.trim().isEmpty() || contracts_notes.trim().isEmpty()) {
                System.out.println("Please fill in ALL fields.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR! Please ensure you fill all required fields.");
                alert.show();
            } else {
                if(updatestatus == false) {
                    psInsert = connection.prepareStatement("INSERT INTO contractsdb (contractsID , contracts_company_name, contracts_company_address , contracts_primary_contact_name , contracts_primary_contact_number , contracts_start_date , contracts_end_date, contracts_value, contracts_notes) VALUES (?,?,?,?,?,?,?,?,?)");
                } else if (updatestatus == true) {
                    psInsert = connection.prepareStatement("UPDATE contractsdb SET contractsID = ?, contracts_company_name = ?, contracts_company_address = ?, contracts_primary_contact_name = ?, contracts_primary_contact_number = ?, contracts_start_date = ?, contracts_end_date = ?, contracts_value = ?, contracts_notes = ? WHERE contractsID = ? ");
                    psInsert.setInt(10,Integer.parseInt(contractsID));
                }
                psInsert.setString(1,contractsID);
                psInsert.setString(2,contracts_company_name);
                psInsert.setString(3,contracts_company_address);
                psInsert.setString(4,contracts_primary_contact_name);
                psInsert.setString(5,contracts_primary_contact_number);
                psInsert.setDate(6,java.sql.Date.valueOf(contracts_start_date));
                psInsert.setDate(7,java.sql.Date.valueOf(contracts_end_date));
                psInsert.setDouble(8,Double.valueOf(contracts_value));
                psInsert.setString(9,contracts_notes);
                psInsert.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckExisting != null) {
                try {
                    psCheckExisting.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //error catching for completed form checks and preventing overriting primary keys and prevents the attempt to add items with duplicate primary keys.
    //boolean value allows the sql to switch depending on whether the add or edit button calls the method to prevent a large sum of duplicate code for class rota
    public static void addRota(String rotaID, String rota_type, String rota_name, String rota_start_date, String rota_end_date, String rota_start_time, String rota_end_time,String rota_comments,boolean updatestatus) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckExisting = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(dbUrl,dbName,dbPass);
            psCheckExisting = connection.prepareStatement("SELECT * FROM rotadb WHERE rotaID = ?");
            psCheckExisting.setString(1,rotaID);
            resultSet = psCheckExisting.executeQuery();
            if (resultSet.isBeforeFirst() && (updatestatus == false)) {
                System.out.println("Rota ID already exists.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR! A rota entry with this ID already exists.");
                alert.show();
            } else if (rotaID.trim().isEmpty() || rota_type.trim().isEmpty() || rota_name.trim().isEmpty() || rota_start_date.trim().isEmpty() || rota_end_date.trim().isEmpty() || rota_start_time.trim().isEmpty() || rota_end_time.trim().isEmpty() || rota_comments.trim().isEmpty()) {
                System.out.println("Please fill in ALL fields.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR! Please ensure you fill all required fields.");
                alert.show();
            } else if (!(rota_type.equals("shift") || rota_type.equals("absence") || rota_type.equals("meeting"))) {
                System.out.println("Please ensure the rota entry type is either 'shift', 'absence' or 'meeting'." );
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR! Please ensure the rota entry type is either 'shift', 'absence' or 'meeting'.");
                alert.show();
            } else {
                if(updatestatus == false) {
                    psInsert = connection.prepareStatement("INSERT INTO rotadb (rotaID , rota_type, rota_name , rota_start_date , rota_end_date , rota_start_time , rota_end_time, rota_comments) VALUES (?,?,?,?,?,?,?,?)");
                } else if (updatestatus == true) {
                    psInsert = connection.prepareStatement("UPDATE rotadb SET rotaID = ?, rota_type = ?, rota_name = ?, rota_start_date = ?, rota_end_date = ?, rota_start_time = ?, rota_end_time = ?, rota_comments = ? WHERE rotaID = ? ");
                    psInsert.setInt(9,Integer.parseInt(rotaID));
                }
                psInsert.setString(1,rotaID);
                psInsert.setString(2,rota_type);
                psInsert.setString(3,rota_name);
                psInsert.setDate(4,java.sql.Date.valueOf(rota_start_date));
                psInsert.setDate(5,java.sql.Date.valueOf(rota_end_date));
                psInsert.setString(6,rota_start_time);
                psInsert.setString(7,rota_end_time);
                psInsert.setString(8,rota_comments);
                psInsert.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckExisting != null) {
                try {
                    psCheckExisting.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //uses primary key to remove unnecessary repeated methods to increase code efficiency to be able to remove a specific row from any table that selects the delete option.
    public static void removeRow(String tableName,String condition, String ID) {
        Connection connection = null;
        PreparedStatement psDelete = null;
        try {
            connection = DriverManager.getConnection(dbUrl,dbName,dbPass);
            if (tableName == "quotesID" || tableName == "contractsID") {
                psDelete = connection.prepareStatement("DELETE FROM " + tableName + " WHERE "+condition+" = " + ID);
            } else {
                psDelete = connection.prepareStatement("DELETE FROM " + tableName + " WHERE " + condition + " = '" + ID + "'");
            }
            psDelete.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (psDelete != null) {
                try {
                    psDelete.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //returns the total count for all 4 tables for the count messages on the homepage by looping through the total entries on a table and outputting total count. Implementing like this prevents code wastage with multiple methods with the same functionality with only a difference in primary key and table names.
    public static int getCount(String db) {
        Connection connection = null;
        PreparedStatement psCount = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(dbUrl,dbName,dbPass);
            psCount = connection.prepareStatement("SELECT COUNT(*) AS totalCount FROM "+db);
            resultSet = psCount.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCount != null) {
                try {
                    psCount.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    //recalls a lot of tricks from log in and sign up to use multiple sql statements to perform multiple checks for robustness before altering the user table to update the password value.
    public static void changePassword(String username, String old_password, String new_password, String new_password_confirm) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckExisting = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(dbUrl,dbName,dbPass);
            psCheckExisting = connection.prepareStatement("SELECT * FROM usersdb WHERE username = ? AND password = ?");
            psCheckExisting.setString(1,username);
            psCheckExisting.setString(2,old_password);
            resultSet = psCheckExisting.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Old password incorrect.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR! Please make sure your old password is your current password.");
                alert.show();
            } else if (!(new_password.equals(new_password_confirm))) {
                System.out.println("New passwords do not match.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR! Please ensure your new passwords are matching.");
                alert.show();
            } else if (old_password.equals(new_password)) {
                System.out.println("Passwords identical");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR! Please ensure your new password is not the same as your old password.");
                alert.show();
            } else if (old_password.trim().isEmpty() || new_password.trim().isEmpty() || new_password_confirm.trim().isEmpty()) {
                System.out.println("Incomplete Fields");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR! Please ensure you fill out ALL fields.");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("UPDATE usersdb SET password = ? WHERE username = ?");
                psInsert.setString(1,new_password);
                psInsert.setString(2,username);
                psInsert.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Password confirmed.");
                alert.setContentText("Password successfully changed.");
                alert.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckExisting != null) {
                try {
                    psCheckExisting.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //specific mass quantity entry remover for the rota table for added robustness, using sql statements to determine which type/s of observable rota types to be deleted and finishes by using sql to delete entries under type class 'RotaClass' that fall before a certain date.
    public static void removeEntries(String removalDate, String removalType) {
        Connection connection = null;
        PreparedStatement psDelete = null;
        try {
            connection = DriverManager.getConnection(dbUrl,dbName,dbPass);
            if (!(removalType.equals("shift") || removalType.equals("absence") || removalType.equals("meeting") || removalType.equals("*") )) {
                System.out.println("Please ensure the rota entry type is either '*', 'shift', 'absence' or 'meeting'.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR! Please ensure the rota entry type is either '*', 'shift', 'absence' or 'meeting'.");
                alert.show();
            }
            if (removalType.equals("*")) {
                psDelete = connection.prepareStatement("DELETE FROM rotadb WHERE rota_end_date < '"+java.sql.Date.valueOf(removalDate)+"'");
            } else {
                psDelete = connection.prepareStatement("DELETE FROM rotadb WHERE rota_end_date < '"+java.sql.Date.valueOf(removalDate)+"' AND rota_type = '"+removalType+"'");
            }
            psDelete.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Delete confirmed.");
            alert.setContentText("Entries successfully deleted.");
            alert.show();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (psDelete != null) {
                try {
                    psDelete.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}