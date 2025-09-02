package friday.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeadlineTest {
    @Test
    void deadlineFormatsDatesCorrectly() {
        Deadline d = new Deadline("submit report", "2025-09-30");
        assertEquals("2025-09-30", d.getByIso());
        assertEquals("Sep 30 2025", d.getBy());
    }
}
