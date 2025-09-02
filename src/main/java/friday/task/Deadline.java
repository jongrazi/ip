package friday.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A task that must be done by a given date.
 * <p>
 * The printed date returns in the form {MMM d yyyy}
 */
public class Deadline extends Task {
    private final LocalDate by;

    /**
     * Creates a deadline task
     * @param description task description
     * @param by date string
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDate.parse(by);
    }

    /**
     * Returns the new formatted date
     * @return formatted due date
     */
    public String getBy() {
        return by.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
    }

    /**
     * Returns the ISO-8601 string of due date
     * @return ISO date string
     */
    public String getByIso() {
        return by.toString();
    }

    @Override
    public String toString() {
        return TaskType.DEADLINE.icon() + super.toString() + " (by: " + getBy() + ")";
    }
}
