public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public String getStatus() {
        return isDone ? "X" : " ";
    }

    public String getDescription() {
        return this.description;
    }
    public boolean isDone() {
        return this.isDone;
    }

    @Override
    public String toString() {
        return "[" + getStatus() + "] " + description;
    }
}