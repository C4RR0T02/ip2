package Task;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    public String taskName;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private LocalDateTime deadline;

    public Deadline(String taskName, String deadline) {
        super(taskName);
        this.taskName = taskName;
        this.deadline = parseDateTime(deadline);
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

    public LocalDateTime getDeadline() {
        return deadline;
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
        return "[D] [" + this.isDoneStatus() + "] " + taskName + "(by " + formatDateTime(deadline) + ")";
    }
}
