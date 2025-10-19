package friday;

import java.awt.GraphicsEnvironment;

public class Launcher {
    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless()
                || "true".equalsIgnoreCase(System.getenv("CI"))
                || "true".equalsIgnoreCase(System.getProperty("javafx.headless"))) {
            FridayCli.main(args);
            return;
        }
        try {
            friday.Main.main(args);
        } catch (Throwable t) {
            FridayCli.main(args);
        }
    }
}
