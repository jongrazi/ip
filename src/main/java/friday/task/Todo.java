package friday.task;

/**
 * Simple task without any other parameters
 */
public class Todo extends Task {

    public Todo(String description) {
        super(description);
        assert !description.isBlank() : "todo description must not be blank, boss.";
    }

    @Override
    public String toString() {
        return TaskType.TODO.icon() + super.toString();
    }
}
