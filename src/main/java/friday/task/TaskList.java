package friday.task;

import friday.FridayException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A thin wrapper around a list of tasks.
 * Encapsulates all mutations to the underlying list.
 */
public class TaskList {
    /** Internal list of tasks. */
    private final List<Task> list;

    /** Creates an empty task list. */
    public TaskList() {
        this.list = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.list = new ArrayList<>();
        if (tasks != null) {
            this.list.addAll(tasks);
        }
    }

    /** Returns the number of tasks in the list. */
    public int size() {
        return list.size();
    }

    /** Returns the task at the given index (0-based). */
    public Task get(int index) {
        return list.get(index);
    }

    /**
     * Adds a task to the list.
     * Duplicate detection is based on the task's description only (date/time ignored).
     *
     * @throws FridayException if a task with the same description already exists (case-insensitive)
     */
    public void add(Task task) throws FridayException {
        Objects.requireNonNull(task, "task");
        String newDesc = task.getDescription().trim();

        for (Task existing : list) {
            if (existing.getDescription().trim().equalsIgnoreCase(newDesc)) {
                throw new FridayException("Boss, it seems like that task is already in my database.");
            }
        }
        list.add(task);
    }

    /** Removes and returns the task at the given index (0-based). */
    public Task remove(int index) {
        return list.remove(index);
    }

    /**
     * Returns the internal list reference.
     * Note: Mutations to the returned list will affect this TaskList.
     */
    public List<Task> asList() {
        return list;
    }

    /**
     * Returns true if there is already a task with the given description (case-insensitive).
     * Convenience helper for callers that want to pre-check.
     */
    public boolean containsDescription(String description) {
        if (description == null) {
            return false;
        }
        String needle = description.trim();
        for (Task t : list) {
            if (t.getDescription().trim().equalsIgnoreCase(needle)) {
                return true;
            }
        }
        return false;
    }
}

