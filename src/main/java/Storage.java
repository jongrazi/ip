import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Storage {

    private static final String DEFAULT_PATH = "data/duke.txt";
    private final Path savePath;

    public Storage() {
        String override = System.getenv("FRIDAY_SAVE_PATH");
        this.savePath = Paths.get(override == null || override.isEmpty() ? DEFAULT_PATH : override);
    }

    public List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        try {
            if (!Files.exists(savePath)) {
                // Create parent dir if missing, don't create file yet
                if (savePath.getParent() != null) {
                    Files.createDirectories(savePath.getParent());
                }
                return tasks; // empty on first run
            }
            try (BufferedReader br = Files.newBufferedReader(savePath, StandardCharsets.UTF_8)) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;
                    Task t = parse(line);
                    if (t != null) {
                        tasks.add(t);
                    }
                }
            }
        } catch (IOException e) {

        }
        return tasks;
    }

    public void save(List<Task> tasks) {
        try {
            if (savePath.getParent() != null) {
                Files.createDirectories(savePath.getParent());
            }
            try (BufferedWriter bw = Files.newBufferedWriter(savePath, StandardCharsets.UTF_8)) {
                for (Task t : tasks) {
                    bw.write(toLine(t));
                    bw.newLine();
                }
            }
        } catch (IOException e) {

        }
    }

    private static String toLine(Task t) {
        if (t instanceof Todo) {
            return String.join("|",
                    "T",
                    t.isDone() ? "1" : "0",
                    t.getDescription());
        } else if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            return String.join("|",
                    "D",
                    t.isDone() ? "1" : "0",
                    t.getDescription(),
                    d.getBy());
        } else if (t instanceof Event) {
            Event e = (Event) t;
            return String.join("|",
                    "E",
                    t.isDone() ? "1" : "0",
                    e.getDescription(),
                    e.getFrom(),
                    e.getTo());
        }

        return String.join("|", "T", t.isDone() ? "1" : "0", t.getDescription());
    }

    private static Task parse(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 3) return null;
        String type = parts[0].trim();
        boolean done = "1".equals(parts[1].trim());
        String desc = parts[2].trim();

        switch (type) {
            case "T": {
                Todo t = new Todo(desc);
                if (done) t.markAsDone();
                return t;
            }
            case "D": {
                if (parts.length < 4) return null;
                String by = parts[3].trim();
                Deadline d = new Deadline(desc, by);
                if (done) d.markAsDone();
                return d;
            }
            case "E": {
                if (parts.length < 5) return null;
                String from = parts[3].trim();
                String to = parts[4].trim();
                Event e = new Event(desc, from, to);
                if (done) e.markAsDone();
                return e;
            }
            default:
                return null;
        }
    }
}
