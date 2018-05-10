package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import orm.DatabaseManager;
import orm.RemindersDao;
import program.Reminders;

public class EventDetailController {

    @FXML
    AnchorPane pane;

    @FXML
    private Label dateDetail, titleDetail, locationDetail, timeDetail, eventDetail;

    private DatabaseManager db;
    private RemindersDao remindersDao = null;

    @FXML
    private void initialize(){
        db = DatabaseManager.getInstance();
        remindersDao = db.getRemindersDao();

        getDetail(ReminderController.getId());
    }

    private void getDetail(int id){
        Reminders reminders = remindersDao.getEventFromKey("number_reminders", id);

        titleDetail.setText(reminders.getTitle_reminders());
        dateDetail.setText(reminders.getDate_reminders());
        eventDetail.setText(reminders.getEvent_reminders());
        locationDetail.setText(reminders.getLocation_reminders());
        timeDetail.setText(reminders.getTime_reminders());

    }
}
