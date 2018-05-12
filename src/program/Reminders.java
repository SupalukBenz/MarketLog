package program;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "reminders")
public class Reminders{

    @DatabaseField(id = true)
    private int number_reminders;
    @DatabaseField
    private String date_reminders;
    @DatabaseField
    private String title_reminders;
    @DatabaseField
    private String event_reminders;
    @DatabaseField
    private String location_reminders;
    @DatabaseField
    private String time_reminders;


    public Reminders(int number_reminders, String date_reminders,String title_reminders,  String event_reminders, String location_reminders, String time_reminders) {
        super();
        this.number_reminders = number_reminders;
        this.date_reminders = date_reminders;
        this.title_reminders = title_reminders;
        this.event_reminders = event_reminders;
        this.location_reminders = location_reminders;
        this.time_reminders = time_reminders;
    }

    public Reminders(String date_reminders,String title_reminders,  String event_reminders, String location_reminders, String time_reminders) {
        super();
        this.date_reminders = date_reminders;
        this.title_reminders = title_reminders;
        this.event_reminders = event_reminders;
        this.location_reminders = location_reminders;
        this.time_reminders = time_reminders;
    }

    public Reminders(){

    }

    public int getNumber_reminders() {
        return number_reminders;
    }

    public String getDate_reminders() {
        return date_reminders;
    }

    public String getTitle_reminders() {
        return title_reminders;
    }

    public String getEvent_reminders() {
        return event_reminders;
    }

    public String getLocation_reminders() {
        return location_reminders;
    }

    public String getTime_reminders() {
        return time_reminders;
    }
}
