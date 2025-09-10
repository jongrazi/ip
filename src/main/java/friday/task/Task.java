package friday.task;

/**
 * Represents a generic task with a description and completion state
 * Subclasses contribute their own type icons and additional fields
 */

public abstract class Task {
    /** Readable description of the task */
    protected final String description;
    /** Whether this task has been marked as done */
    protected boolean isDone;

    /** Creates a new task. Created tasks are not marked done by default
     *
     * @param description of the task
     */
    protected Task(String description) {
        assert description != null : "Your description cannot be empty, boss.";
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        assert description != null : "Your description cannot be empty, boss.";
        return description;
    }

    /** Marks this task as done. */
    public void markAsDone() {
        this.isDone = true;
    }

    /** Marks this task as not done. */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns the description of this task.
     *
     * @return the description string
     */

    /**
     * Returns whether this task is done
     * @return true if done, false otherwise
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns a display string
     * @return user-readable representation of the task
     */
    @Override
    public String toString() {
        return "[" + (isDone ? "X" : " ") + "] " + description;
    }
}
