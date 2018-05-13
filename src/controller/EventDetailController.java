package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import orm.DatabaseManager;
import orm.RemindersDao;
import program.Reminders;

/**
 * EventDetailController is class for showing details of event that you have been saving.
 *
 * @author Supaluk Jaroensuk
 */
public class EventDetailController {

    /**
     * AnchorPane of UI
     */
    @FXML
    AnchorPane pane;

    /**
     * Text label of event details.
     */
    @FXML
    private Label dateDetail, titleDetail, locationDetail, timeDetail, eventDetail;

    /**
     * DatabaseManager
     */
    private DatabaseManager db;

    /**
     * ReminderDao access object for handle all database operation.
     */
    private RemindersDao remindersDao = null;

    /**
     * Initialize DatabaseManager for create ReminderDao and show detail of event.
     */
    @FXML
    private void initialize(){
        db = DatabaseManager.getInstance();
        remindersDao = db.getRemindersDao();

        getDetail(ReminderController.getId());
    }

    /**
     * Get detail and set to label.
     * @param id
     */
    private void getDetail(int id){
        Reminders reminders = remindersDao.getEventFromKey("number_reminders", id);

        titleDetail.setText(reminders.getTitle_reminders());
        dateDetail.setText(reminders.getDate_reminders());
        eventDetail.setText(reminders.getEvent_reminders());
        locationDetail.setText(reminders.getLocation_reminders());
        timeDetail.setText(reminders.getTime_reminders());

    }
}
