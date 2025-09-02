package friday.task;

/**
 * Enum of supported task types and their display icons.
 */
public enum TaskType {
    /** To-do task icon. */
    TODO("[T]"),
    /** Deadline task icon. */
    DEADLINE("[D]"),
    /** Event task icon. */
    EVENT("[E]");

    /** Icon prefix used in toString() of tasks. */
    private final String icon;

    /**
     * Creates a task type with the given icon
     * @param icon the icon prefix representation
     */
    TaskType(String icon) {
        this.icon = icon;
    }

    /**
     * Returns the icon prefix of this task type
     * @return the icon prefix
     */
    public String icon() {
        return icon;
    }
}
