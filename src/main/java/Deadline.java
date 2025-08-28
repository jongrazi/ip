public class Deadline extends Task {
    private final String date;

    public Deadline(String description, String date) {
        super(description);
        this.date = date;
    }

    public String getBy() {
        return this.date;
    }

    @Override
    public String toString() {
        return TaskType.DEADLINE.icon() + super.toString() + " (by: " + date + ")";
    }
}