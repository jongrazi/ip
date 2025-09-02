package friday.task;

import java.util.ArrayList;
import java.util.List;

/**
 * A thin wrapper around a list of tasks
 * <p>
 */
public class TaskList {
    /** List of tasks */
    private final List<Task> list;

    /** Creates an empty task list */
    public TaskList() {
        this.list = new ArrayList<>();
    }

    /**
     * Creates a task list initialized with the given tasks
     * @param initial initial list contents
     */
    public TaskList(List<Task> initial) {
        this.list = new ArrayList<>(initial);
    }

    /**
     * Returns number of tasks in the list
     * @return size of the list
     */
    public int size() {
        return list.size();
    }

    /**
     * Returns the task at the given index
     * @param index zero-based index
     * @return the task at the given index
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public Task get(int index) {
        return list.get(index);
    }

    /**
     * Appends a task to the end of the list.
     *
     * @param t task to add
     */
    public void add(Task t) {
        list.add(t);
    }
    /**
     * Removes and returns the task at the given index.
     *
     * @param index zero-based index
     * @return the removed task
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public Task remove(int index) {
        return list.remove(index);
    }

    /**
     * Returns the list. Mutations will affect this task list
     *
     * @return the internal list
     */
    public List<Task> asList() {
        return list;
    }
}
