package Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    public String eventName;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private LocalDateTime start;
    private LocalDateTime end;

    public Event(String eventName, String start, String end) {
        super(eventName);
        this.eventName = eventName;
        this.start = LocalDateTime.parse(start, formatter);
        this.end = LocalDateTime.parse(end, formatter);
    }

    @Override
    public String toString() {
        return "[E] [" + this.isDoneStatus() + "] " + eventName + " (from " + start.format(formatter) + " to " + end.format(formatter) + ")";
    }
}
