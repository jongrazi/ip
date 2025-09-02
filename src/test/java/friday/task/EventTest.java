package friday.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    @Test
    void eventGetters() {
        Event e = new Event("meeting", "10:00", "12:00");
        assertEquals("10:00", e.getFrom());
        assertEquals("12:00", e.getTo());
    }

    @Test
    void eventToString() {
        Event e = new Event("meeting", "10:00", "12:00");
        assertEquals("[E][ ] meeting (from: 10:00 to: 12:00)", e.toString());
    }
}
