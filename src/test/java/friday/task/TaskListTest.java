package friday.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {
    @Test
    void addAndRemoveTasks() {
        TaskList list = new TaskList();
        list.add(new Todo("a"));
        list.add(new Todo("b"));

        assertEquals(2, list.size());
        Task removed = list.remove(0);
        assertEquals("[T][ ] a", removed.toString());
        assertEquals(1, list.size());
    }
}
