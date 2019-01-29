package office;

import org.joda.time.Interval;
import org.joda.time.LocalTime;

import java.util.Objects;

/**
 * User: sameer
 * Date: 16/05/2013
 * Time: 10:03
 */
public class Meeting implements Comparable<Meeting>{

    private String employeeId;

    private LocalTime startTime;

    private LocalTime finishTime;

    public Meeting(String employeeId, LocalTime startTime, LocalTime finishTime) {
        this.employeeId = employeeId;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }


    public String getEmployeeId() {
        return employeeId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getFinishTime() {
        return finishTime;
    }

    @Override
    public int compareTo(Meeting that) {
        Interval meetingInterval = new Interval(startTime.toDateTimeToday(), finishTime.toDateTimeToday());
        Interval toCompareMeetingInterval = new Interval(that.getStartTime().toDateTimeToday(), that.getFinishTime().toDateTimeToday());

        if(meetingInterval.overlaps(toCompareMeetingInterval)){
            return 0;
        }else{
            return this.getStartTime().compareTo(that.getStartTime());
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meeting)) return false;
        Meeting meeting = (Meeting) o;
        return Objects.equals(getStartTime(), meeting.getStartTime()) &&
                Objects.equals(getFinishTime(), meeting.getFinishTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartTime(), getFinishTime());
    }
}
