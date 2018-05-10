package controller;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import orm.DatabaseManager;
import orm.RemindersDao;
import program.ChangePage;
import program.Reminders;
import program.Sales;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReminderController {
    @FXML
    AnchorPane pane;

    @FXML
    private TableView<Reminders> eventTable;

    @FXML
    private TableColumn<Reminders, String> dateTable;

    @FXML
    private TableColumn<Reminders, String> titleTable;

    @FXML
    private TableColumn<Reminders, String> timeTable;

    @FXML
    private DatePicker dateReminder;

    @FXML
    private TextField titleReminder, locationReminder, eventReminder, search;

    @FXML
    private ComboBox minuteReminder, hourReminder;

    @FXML
    private Label dateDetail, titleDetail, locationDetail, timeDetail, eventDetail;


    private String time, event, location, title, hour, minute = "";
    private String date;
    private static int idSearch;
    private DatabaseManager db;
    private RemindersDao remindersDao = null;

    private ObservableList<Reminders> remindersList = FXCollections.observableArrayList();

    @FXML
    private void initialize(){
        db = DatabaseManager.getInstance();
        remindersDao = db.getRemindersDao();
        remindersToTable();
        dateReminder = new DatePicker();
        dateReminder.setValue(LocalDate.now());
        addTime();
    }

    @FXML
    private void handleAddEvent(ActionEvent e){
        if(check()){
            title = titleReminder.getText();
            LocalDate localDate = dateReminder.getValue();
            date = localDate.toString();
            event = eventReminder.getText();
            location = locationReminder.getText();
            hour = hourReminder.getValue().toString();
            minute = minuteReminder.getValue().toString();
            time = hour + ":" + minute;

            Reminders reminders = new Reminders(date, title, event, location,time);

            try {
                remindersDao.create(reminders);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Added Remainder");
        alert.setHeaderText(null);
        alert.setContentText("Adding Reminder, Successful!");

        alert.showAndWait();
        ChangePage.changeUI("UI/ReminderUI.fxml", pane);
    }

    @FXML
    private void handleGetDetail(ActionEvent event){
        if(checkClickTable(eventTable)) {
            ObservableList<Reminders> eventList = eventTable.getSelectionModel().getSelectedItems();
            idSearch = eventList.get(0).getNumber_reminders();
            Parent root;
            try {
                root = (Parent) FXMLLoader.load(getClass().getResource("/UI/ReminderDetailUI.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Event Detail");
                stage.setScene(new Scene(root, 600, 500));
                stage.getIcons().add(new Image("/UI/photos/MarketLogIcon.png"));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    private void handleSelectMinute(){
        minute = minuteReminder.getValue().toString();
    }

    @FXML
    private void handleSelectHour(){
        hour = hourReminder.getValue().toString();
    }

    @FXML
    private void handleSearchDetail(){
        if(!dateReminder.getValue().equals("")) {
            LocalDate localDate = dateReminder.getValue();
            date = localDate.toString();
            search.setText(date);

            search.textProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    if (search.textProperty().get().isEmpty()) {
                        eventTable.setItems(remindersList);
                        return;
                    }
                    ObservableList<Reminders> tableData = FXCollections.observableArrayList();
                    ObservableList<TableColumn<Reminders, ?>> cols = eventTable.getColumns();

                    for (int i = 0; i < remindersList.size(); i++) {

                        for (int k = 0; k < cols.size(); k++) {
                            TableColumn col = cols.get(k);
                            String data = col.getCellData(remindersList.get(i)).toString().toLowerCase();
                            System.out.println();
                            if (data.contains(search.textProperty().get().toLowerCase())) {
                                tableData.add(remindersList.get(i));
                                break;
                            }
                        }
                    }
                    eventTable.setItems(tableData);
                }
            });
        }
    }

    private void remindersToTable(){
        for(Reminders reminders: remindersDao){
            remindersList.add(reminders);
        }
        timeTable.setCellValueFactory(new PropertyValueFactory<>("time_reminders"));
        titleTable.setCellValueFactory(new PropertyValueFactory<>("title_reminders"));
        dateTable.setCellValueFactory(new PropertyValueFactory<>("date_reminders"));

        eventTable.setItems(null);
        eventTable.setItems(remindersList);
    }

    private boolean check(){
        if(!dateReminder.getValue().equals("") && !titleReminder.getText().trim().isEmpty() && !locationReminder.getText().trim().isEmpty()
                && !eventReminder.getText().trim().isEmpty()) return true;
        return false;
    }

    private void addTime(){
        List<String> hourList = new ArrayList<>();
        for(int i=0; i<=24; i++){
            if(i<=9) hourList.add(String.valueOf("0"+i));
            else hourList.add(String.valueOf(i));
        }

        List<String> minuteList = new ArrayList<>();
        for (int i=0; i<=60; i++){
            if(i<=9) minuteList.add(String.valueOf("0"+i));
            else minuteList.add(String.valueOf(i));
        }
        hourReminder.getItems().addAll(hourList);
        hourReminder.getSelectionModel().select(0);
        hour = hourReminder.getValue().toString();

        minuteReminder.getItems().addAll(minuteList);
        minuteReminder.getSelectionModel().select(0);
        minute = minuteReminder.getValue().toString();
    }

    private boolean checkClickTable(TableView<?> table){
        if(table.getSelectionModel().getSelectedItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please, choose event list in table.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public static int getId(){
        return idSearch;
    }



}
