package friday.task;

/**
 * A task that spans a time interval.
 */
public class Event extends Task {
    /** Start time token given by user*/
    private final String from;
    /** End time token given by user*/
    private final String to;

    /**
     * Creates a even task with a description and a time start to end range
     * @param description task description
     * @param from start time token
     * @param to end time token
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the start token
     * @return start token
     */
    public String getFrom() {
        return this.from;
    }

    /**
     * Returns the end token
     * @return end token
     */
    public String getTo() {
        return this.to;
    }

    @Override
    public String toString() {
        return TaskType.EVENT.icon() + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
