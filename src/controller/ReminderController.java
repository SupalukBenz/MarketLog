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
import javafx.util.StringConverter;
import orm.DatabaseManager;
import orm.RemindersDao;
import program.ChangePage;
import program.Reminders;
import program.Sales;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    DatePicker picker;
    @FXML
    private TextField titleReminder, locationReminder, eventReminder, search;

    @FXML
    private ComboBox minuteReminder, hourReminder;

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
        eventTable.getSortOrder().add(dateTable);
        addTime();
    }

    @FXML
    private void handleAddEvent(ActionEvent e){
        if(check()){
            title = titleReminder.getText();
            LocalDate localDate = picker.getValue();
            date = localDate.toString();
            event = eventReminder.getText();
            location = locationReminder.getText();
            hour = hourReminder.getValue().toString();
            minute = minuteReminder.getValue().toString();
            time = hour + ":" + minute;

            Reminders reminders = new Reminders(remindersDao.getPreviosId(remindersDao)+1, date, title, event, location,time);

            try {
                remindersDao.create(reminders);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        eventTable.getSortOrder().add(dateTable);
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
        if (picker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please, choose date for search or add.");
            alert.showAndWait();
        }

        if (picker.getValue()!=null) {
            LocalDate dateSelected = LocalDate.of(picker.getValue().getYear(), picker.getValue().getMonth(), picker.getValue().getDayOfMonth());
            date = dateSelected.toString();
            System.out.println(date);
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
        };

        eventTable.getSortOrder().add(dateTable);
    }

    @FXML
    private void handleDeleteDetail(){
        if(checkClickTable(eventTable)){
            ObservableList<Reminders> reminderSelected, allReminders;
            allReminders = eventTable.getItems();
            reminderSelected = eventTable.getSelectionModel().getSelectedItems();
            Reminders deleteReminder = reminderSelected.get(0);
            try {
                remindersDao.delete(deleteReminder);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            reminderSelected.forEach(allReminders::remove);
        }

        ChangePage.changeUI("UI/ReminderUI.fxml", pane);
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
        if(!titleReminder.getText().trim().isEmpty() && !locationReminder.getText().trim().isEmpty()
                && !eventReminder.getText().trim().isEmpty()) return true;
        return false;
    }

    private void addTime(){
        List<String> hourList = new ArrayList<>();
        for(int i=0; i<=23; i++){
            if(i<=9) hourList.add(String.valueOf("0"+i));
            else hourList.add(String.valueOf(i));
        }

        List<String> minuteList = new ArrayList<>();
        for (int i=0; i<=59; i++){
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
