package friday.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private final LocalDate by;

    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDate.parse(by);
    }

    public String getBy() {
        return by.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
    }

    public String getByIso() {
        return by.toString();
    }

    @Override
    public String toString() {
        return TaskType.DEADLINE.icon() + super.toString() + " (by: " + getBy() + ")";
    }
}
