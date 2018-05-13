package program;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Reminders is class that persist to SQL database.
 *
 * @author Supaluk
 */
@DatabaseTable(tableName = "reminders")
public class Reminders{

    /**
     * Column of reminders table that store number reminder.
     */
    @DatabaseField(id = true)
    private int number_reminders;

    /**
     * Column of reminders table that store date reminder.
     */
    @DatabaseField
    private String date_reminders;

    /**
     * Column of reminders table that store title reminder.
     */
    @DatabaseField
    private String title_reminders;

    /**
     * Column of reminders table that store event reminder.
     */
    @DatabaseField
    private String event_reminders;

    /**
     * Column of reminders table that store location reminder.
     */
    @DatabaseField
    private String location_reminders;

    /**
     * Column of reminders table that store time reminder.
     */
    @DatabaseField
    private String time_reminders;

    /**
     * Initialize details of reminder.
     * @param number_reminders is number of reminder.
     * @param date_reminders is date of reminder.
     * @param title_reminders is title of reminder.
     * @param event_reminders is event of reminder.
     * @param location_reminders is location of reminder.
     * @param time_reminders is time of reminder.
     */
    public Reminders(int number_reminders, String date_reminders,String title_reminders,  String event_reminders, String location_reminders, String time_reminders) {
        super();
        this.number_reminders = number_reminders;
        this.date_reminders = date_reminders;
        this.title_reminders = title_reminders;
        this.event_reminders = event_reminders;
        this.location_reminders = location_reminders;
        this.time_reminders = time_reminders;
    }

    /**
     * no-arg constructor
     */
    public Reminders(){

    }

    /**
     * Get number of reminder.
     * @return number of reminder.
     */
    public int getNumber_reminders() {
        return number_reminders;
    }

    /**
     * Get date of reminder.
     * @return date of reminder.
     */
    public String getDate_reminders() {
        return date_reminders;
    }

    /**
     * Get title of reminder.
     * @return title of reminder.
     */
    public String getTitle_reminders() {
        return title_reminders;
    }

    /**
     * Get event of reminder.
     * @return event of reminder.
     */
    public String getEvent_reminders() {
        return event_reminders;
    }

    /**
     * Get location of reminder.
     * @return location of reminder.
     */
    public String getLocation_reminders() {
        return location_reminders;
    }

    /**
     * Get time of reminder.
     * @return time of reminder.
     */
    public String getTime_reminders() {
        return time_reminders;
    }
}
