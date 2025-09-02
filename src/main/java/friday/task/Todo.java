package friday.task;

/**
 * Simple task without any other parameters
 */
public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return TaskType.TODO.icon() + super.toString();
    }
}
