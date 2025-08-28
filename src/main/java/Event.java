public class Event extends Task {
    private final String from;
    private final String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return this.from;
    }
    public String getTo() {
        return this.to;
    }

    @Override
    public String toString() {
        return TaskType.EVENT.icon() + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
