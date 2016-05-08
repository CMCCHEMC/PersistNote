package habit.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table CHECK_IN_RECORD.
 */
public class CheckInRecord {

    private Long id;
    private long habitId;
    private long checkInTimestamp;
    private java.util.Date checkInDate;

    public CheckInRecord() {
    }

    public CheckInRecord(Long id) {
        this.id = id;
    }

    public CheckInRecord(Long id, long habitId, long checkInTimestamp, java.util.Date checkInDate) {
        this.id = id;
        this.habitId = habitId;
        this.checkInTimestamp = checkInTimestamp;
        this.checkInDate = checkInDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getHabitId() {
        return habitId;
    }

    public void setHabitId(long habitId) {
        this.habitId = habitId;
    }

    public long getCheckInTimestamp() {
        return checkInTimestamp;
    }

    public void setCheckInTimestamp(long checkInTimestamp) {
        this.checkInTimestamp = checkInTimestamp;
    }

    public java.util.Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(java.util.Date checkInDate) {
        this.checkInDate = checkInDate;
    }

}