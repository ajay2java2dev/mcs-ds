package office;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * User: sameer
 * Date: 15/05/2013
 * Time: 15:12
 */
public class MeetingScheduler {

    private DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
    private DateTimeFormatter separatedTimeFormatter = DateTimeFormat.forPattern("HH:mm");

    /**
     *
     * @param meetingRequest
     * @return
     */
    public MeetingsSchedule schedule(String meetingRequest) {
        String[] requestLines = meetingRequest.split("\n");

        String[] officeHoursTokens = requestLines[0].split(" ");
        LocalTime officeStartTime =  new LocalTime(parseInt(officeHoursTokens[0].substring(0, 2)),
                parseInt(officeHoursTokens[0].substring(2, 4)));
        LocalTime officeFinishTime =  new LocalTime(parseInt(officeHoursTokens[1].substring(0, 2)),
                parseInt(officeHoursTokens[1].substring(2, 4)));

        Map<LocalDate, Set<Meeting>> meetings = new HashMap<LocalDate, Set<Meeting>>();

        String empId = null;
        for(int i=1;i<requestLines.length;i=i+1){
            if (requestLines[i].contains("EMP")) {
                empId = requestLines[i].split(" ")[2];
                continue;
            }
            String[] meetingSlotRequest = requestLines[i].split(" ");
            LocalDate meetingDate = dateFormatter.parseLocalDate(meetingSlotRequest[0]);

            Meeting meeting = extractMeeting(requestLines[i], officeStartTime, officeFinishTime, empId);

            if (meeting!=null) {
                if (meetings.containsKey(meetingDate)) {
                    meetings.get(meetingDate).remove(meeting);
                    meetings.get(meetingDate).add(meeting);
                } else {
                    Set<Meeting> meetingsForDay = new HashSet<Meeting>();
                    meetingsForDay.add(meeting);
                    meetings.put(meetingDate, meetingsForDay);
                }
            }
        }

        Map<LocalDate, Set<Meeting>> sortedMeetings = new TreeMap<LocalDate, Set<Meeting>>();
        if (meetings!=null && !meetings.isEmpty()) {
            sortedMeetings = new TreeMap<LocalDate, Set<Meeting>>(meetings);
            for (Map.Entry<LocalDate, Set<Meeting>> meetingEntry : sortedMeetings.entrySet()) {
                SortedSet<Meeting> sortedSet = new TreeSet<>(meetingEntry.getValue());
                meetingEntry.setValue(sortedSet);
            }
        }

        return new MeetingsSchedule(officeStartTime, officeFinishTime, sortedMeetings);
    }

    private Meeting extractMeeting(String requestLine, LocalTime officeStartTime,
                                   LocalTime officeFinishTime, String empId) {
        String[] employeeRequest = requestLine.split(" ");

        LocalTime meetingStartTime =  separatedTimeFormatter.parseLocalTime(employeeRequest[1]);
        LocalTime meetingFinishTime = new LocalTime(meetingStartTime.getHourOfDay(), meetingStartTime.getMinuteOfHour())
                .plusHours(parseInt(employeeRequest[2]));

        if(meetingTimeOutsideOfficeHours(officeStartTime, officeFinishTime, meetingStartTime, meetingFinishTime)){
            System.out.println("EmployeeId:"+empId+" has requested booking which is outside office hour.");
            return null;
        }else{
            return new Meeting(empId, meetingStartTime, meetingFinishTime);

        }
    }

    private boolean meetingTimeOutsideOfficeHours(LocalTime officeStartTime, LocalTime officeFinishTime, LocalTime meetingStartTime, LocalTime meetingFinishTime) {
        return meetingStartTime.isBefore(officeStartTime)
                || meetingStartTime.isAfter(officeFinishTime)
                || meetingFinishTime.isAfter(officeFinishTime)
                || meetingFinishTime.isBefore(officeStartTime);
    }
}
