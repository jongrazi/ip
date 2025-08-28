import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final Path savePath;

    public Storage(Path path) {
        this.savePath = path;
    }

    public List<Task> load() {
        List<Task> list = new ArrayList<>();
        try {
            if (!Files.exists(savePath)) {
                if (savePath.getParent() != null) Files.createDirectories(savePath.getParent());
                return list;
            }
            try (BufferedReader br = Files.newBufferedReader(savePath, StandardCharsets.UTF_8)) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("\\|", -1);
                    if (parts.length < 3) continue;
                    String type = parts[0].trim();
                    boolean done = "1".equals(parts[1].trim());
                    String desc = parts[2].trim();
                    Task t = null;
                    if ("T".equals(type)) {
                        t = new Todo(desc);
                    } else if ("D".equals(type) && parts.length >= 4) {
                        try {
                            t = new Deadline(desc, parts[3].trim());
                        } catch (java.time.format.DateTimeParseException ex) {
                            t = null;
                        }
                    } else if ("E".equals(type) && parts.length >= 5) {
                        t = new Event(desc, parts[3].trim(), parts[4].trim());
                    }
                    if (t != null) {
                        if (done) t.markAsDone();
                        list.add(t);
                    }
                }
            }
        } catch (IOException e) {
        }
        return list;
    }

    public void save(List<Task> tasks) {
        try {
            if (savePath.getParent() != null) Files.createDirectories(savePath.getParent());
            try (BufferedWriter bw = Files.newBufferedWriter(savePath, StandardCharsets.UTF_8)) {
                for (Task t : tasks) {
                    String line;
                    if (t instanceof Todo) {
                        line = String.join("|", "T", t.isDone() ? "1" : "0", t.getDescription());
                    } else if (t instanceof Deadline) {
                        Deadline d = (Deadline) t;
                        line = String.join("|", "D", d.isDone() ? "1" : "0", d.getDescription(), d.getByIso());
                    } else if (t instanceof Event) {
                        Event e = (Event) t;
                        line = String.join("|", "E", e.isDone() ? "1" : "0", e.getDescription(), e.getFrom(), e.getTo());
                    } else {
                        line = String.join("|", "T", t.isDone() ? "1" : "0", t.getDescription());
                    }
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
        }
    }
}
