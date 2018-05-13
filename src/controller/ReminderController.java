package controller;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import orm.DatabaseManager;
import orm.RemindersDao;
import program.ChangePage;
import program.Reminders;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * ReminderController is class for controller event reminder.
 *
 * @author Supaluk Jaroensuk
 */
public class ReminderController {

    /**
     * AnchorPane of UI
     */
    @FXML
    AnchorPane pane;

    /**
     * TableView that showing all of events.
     */
    @FXML
    private TableView<Reminders> eventTable;

    /**
     * TableColumn for showing date.
     */
    @FXML
    private TableColumn<Reminders, String> dateTable;

    /**
     * TableColumn for showing title.
     */
    @FXML
    private TableColumn<Reminders, String> titleTable;

    /**
     * TableColumn for showing time.
     */
    @FXML
    private TableColumn<Reminders, String> timeTable;

    /**
     * DatePicker for choosing date.
     */
    @FXML
    DatePicker picker;

    /**
     * TextField for inserting text about detail for add or search event.
     */
    @FXML
    private TextField titleReminder, locationReminder, eventReminder, search;

    /**
     * ComboBox stored minute and hour.
     */
    @FXML
    private ComboBox minuteReminder, hourReminder;

    /**
     * Detail of adding event, as a String.
     */
    private String time, event, location, title, hour, minute, date = "";

    /**
     * Id of event that user want to get detail, as a static for using detail controller.
     */
    private static int idSearch;

    /**
     * DatabaseManager class.
     */
    private DatabaseManager db;

    /**
     * ReminderDao access object for handle all database operation.
     */
    private RemindersDao remindersDao = null;

    /**
     * ObservableList of Reminders
     */
    private ObservableList<Reminders> remindersList = FXCollections.observableArrayList();

    /**
     * Initialize DatabaseManager for create ReminderDao and show details events on table.
     */
    @FXML
    private void initialize(){
        db = DatabaseManager.getInstance();
        remindersDao = db.getRemindersDao();

        remindersToTable();
        eventTable.getSortOrder().add(dateTable);
        addTime();
    }

    /**
     * Handle adding event to database.
     * @param e is action of button.
     */
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

            Reminders reminders = new Reminders(remindersDao.getPreviousId(remindersDao)+1, date, title, event, location,time);

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

    /**
     * Handle getting detail of event.
     * @param event is action on button.
     */
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
                stage.setScene(new Scene(root, 1000, 400));
                stage.getIcons().add(new Image("/UI/photos/MarketLogIcon.png"));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Handle setting value when user selected minute.
     */
    @FXML
    private void handleSelectMinute(){
        minute = minuteReminder.getValue().toString();
    }

    /**
     * Handle setting value when user selected hour.
     */
    @FXML
    private void handleSelectHour(){
        hour = hourReminder.getValue().toString();
    }

    /**
     * Handle searching event by date.
     */
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

    /**
     * Handle deleting event from database and table.
     */
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

    /**
     * Insert data of event to table.
     */
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

    /**
     * Check that text of details event was inserted or not.
     * @return true if textfield is not empty, and false if textfield is empty.
     */
    private boolean check(){
        if(!titleReminder.getText().trim().isEmpty() && !locationReminder.getText().trim().isEmpty()
                && !eventReminder.getText().trim().isEmpty()) return true;
        return false;
    }

    /**
     * Add minute and hour to ComboBox.
     */
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

    /**
     * Check that user selected event on table or not.
     * @param table
     * @return
     */
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

    /**
     * Get id that user selected.
     * @return id was selected, as a int.
     */
    public static int getId(){
        return idSearch;
    }



}
