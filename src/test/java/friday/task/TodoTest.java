package friday.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TodoTest {
    @Test
    void todoToString_defaultIsUnchecked() {
        Todo t = new Todo("buy milk");
        assertEquals("[T][ ] buy milk", t.toString());
    }
}
