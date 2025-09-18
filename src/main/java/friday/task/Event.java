package friday.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A task that spans a time interval.
 * <p>
 * The printed dates return in the form {MMM d yyyy}.
 */
public class Event extends Task {
    /** Start date */
    private final LocalDate from;
    /** End date */
    private final LocalDate to;

    /**
     * Creates an event task with a description and a start-to-end date range.
     *
     * @param description task description
     * @param from start date string in ISO format (yyyy-MM-dd)
     * @param to end date string in ISO format (yyyy-MM-dd)
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = LocalDate.parse(from);
        this.to = LocalDate.parse(to);
    }

    /**
     * Returns the formatted start date.
     *
     * @return formatted start date
     */
    public String getFrom() {
        return from.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
    }

    /**
     * Returns the formatted end date.
     *
     * @return formatted end date
     */
    public String getTo() {
        return to.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
    }

    /**
     * Returns the ISO string of the start date.
     *
     * @return ISO start date
     */
    public String getFromIso() {
        return from.toString();
    }

    /**
     * Returns the ISO string of the end date.
     *
     * @return ISO end date
     */
    public String getToIso() {
        return to.toString();
    }

    @Override
    public String toString() {
        return TaskType.EVENT.icon() + super.toString()
                + " (from: " + getFrom() + " to: " + getTo() + ")";
    }
}
