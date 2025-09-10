package friday.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        assert list != null : "internal list should be initialized";
    }

    /**
     * Creates a task list initialized with the given tasks
     * @param initial initial list contents
     */
    public TaskList(List<Task> initial) {
        assert initial != null : "initial list must not be null";
        assert initial.stream().noneMatch(Objects::isNull) : "initial list must not contain null tasks";
        this.list = new ArrayList<>(initial);
        assert list.stream().noneMatch(Objects::isNull) : "internal list must not contain null tasks";
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
        assert index >= 0 && index < list.size() : "index out of bounds (get)";
        return list.get(index);
    }

    /**
     * Appends a task to the end of the list.
     *
     * @param t task to add
     */
    public void add(Task t) {
        assert t != null : "task to add must not be null";
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
        assert index >= 0 && index < list.size() : "index out of bounds (remove)";
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
