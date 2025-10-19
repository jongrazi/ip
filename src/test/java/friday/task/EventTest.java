package friday.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    @Test
    void eventGetters() {
        Event e = new Event("meeting", "2025-10-11", "2025-10-12");
        assertEquals("Oct 11 2025", e.getFrom());
        assertEquals("Oct 12 2025", e.getTo());
    }

    @Test
    void eventToString() {
        Event e = new Event("meeting", "2025-10-11", "2025-10-12");
        assertEquals("[E][ ] meeting (from: Oct 11 2025 to: Oct 12 2025)", e.toString());
    }
}
