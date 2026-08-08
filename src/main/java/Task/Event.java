package Task;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    public String eventName;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private LocalDateTime start;
    private LocalDateTime end;

    public Event(String eventName, String start, String end) {
        super(eventName);
        this.eventName = eventName;
        this.start = parseDateTime(start);
        this.end = parseDateTime(end);
    }

    /**
     * Parses a date or date-time, using midnight when the time is omitted.
     */
    private LocalDateTime parseDateTime(String dateTime) {
        if (dateTime.length() == 10) {
            dateTime += " 00:00:00";
        }
        return LocalDateTime.parse(dateTime, formatter);
    }

    /**
     * Returns this event's start date and time.
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Returns this event's end date and time.
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Formats a date-time without a time when it is midnight.
     */
    private String formatDateTime(LocalDateTime dateTime) {
        if (dateTime.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            return dateTime.format(dateFormatter);
        }
        return dateTime.format(formatter);
    }

    @Override
    public String toString() {
        return "[E] [" + this.isDoneStatus() + "] " + eventName + " (from " + formatDateTime(start)
                + " to " + formatDateTime(end) + ")";
    }
}
