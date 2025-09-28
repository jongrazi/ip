package friday;

import java.awt.GraphicsEnvironment;

/**
 * Safe entry point:
 * - If no display (headless/CI), run CLI (friday.Friday)
 * - Else, try JavaFX GUI; if it fails, fall back to CLI
 */
public class Launcher {
    public static void main(String[] args) {
        // If there’s no graphics environment, go straight to CLI.
        if (GraphicsEnvironment.isHeadless()
                || "true".equalsIgnoreCase(System.getenv("CI"))
                || "true".equalsIgnoreCase(System.getProperty("javafx.headless"))) {
            friday.Friday.main(args);
            return;
        }

        try {
            friday.Main.main(args);
        } catch (Throwable t) {
            System.err.println("[Friday] GUI failed to start; falling back to CLI: " + t);
            friday.Friday.main(args);
        }
    }
}
