import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Friday {
    private static final Path SAVE_PATH = Paths.get("data", "duke.txt");

    public static void main(String[] args) {
        String separator = "_".repeat(60);

        System.out.println(separator);
        System.out.println("It's good to have you back sir, I am F.R.I.D.A.Y");
        System.out.println("What can I do for you sir?");
        System.out.println(separator);

        List<Task> tasks = new ArrayList<>();
        tasks.addAll(loadTasks());
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine().trim();
            try {
                if (command.equalsIgnoreCase("bye")) {
                    System.out.println(separator);
                    System.out.println("Affirmative, goodnight boss.");
                    System.out.println(separator);
                    break;
                } else if (command.equalsIgnoreCase("list")) {
                    System.out.println(separator);
                    System.out.println("Affirmative, loading tasks...");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println(" " + (i + 1) + ". " + tasks.get(i));
                    }
                    System.out.println(separator);
                } else if (command.startsWith("delete")) {
                    String[] parts = command.split("\\s+", 2);
                    if (parts.length < 2) {
                        throw new FridayException("Apologies boss, that task number isn't recorded in my database.");
                    }
                    int index = Integer.parseInt(parts[1]) - 1;
                    Task removed = tasks.remove(index);
                    saveTasks(tasks);
                    System.out.println(separator);
                    System.out.println("Noted. I've removed this task:");
                    System.out.println(" " + removed);
                    System.out.println("Boss, you have " + tasks.size() + " tasks in the list.");
                    System.out.println(separator);
                } else if (command.startsWith("mark")) {
                    String[] parts = command.split("\\s+", 2);
                    if (parts.length < 2) {
                        throw new FridayException("Apologies boss, that task number isn't recorded in my database.");
                    }
                    int index = Integer.parseInt(parts[1]) - 1;
                    Task t = tasks.get(index);
                    t.markAsDone();
                    saveTasks(tasks);
                    System.out.println(separator);
                    System.out.println("Okay boss, I have marked this task as done:");
                    System.out.println(" " + t);
                    System.out.println(separator);
                } else if (command.startsWith("unmark")) {
                    String[] parts = command.split("\\s+", 2);
                    if (parts.length < 2) {
                        throw new FridayException("Apologies boss, that task number isn't recorded in my database.");
                    }
                    int index = Integer.parseInt(parts[1]) - 1;
                    Task t = tasks.get(index);
                    t.markAsNotDone();
                    saveTasks(tasks);
                    System.out.println(separator);
                    System.out.println("Okay boss, I have marked this task as not done yet:");
                    System.out.println(" " + t);
                    System.out.println(separator);
                } else if (command.startsWith("todo")) {
                    String desc = command.length() > 4 ? command.substring(4).trim() : "";
                    if (desc.isEmpty()) {
                        throw new FridayException("Seems like you have made a mistake boss, a todo cannot be empty.");
                    }
                    Task t = new Todo(desc);
                    tasks.add(t);
                    saveTasks(tasks);
                    System.out.println(separator);
                    System.out.println("Got it boss. I have added this task:");
                    System.out.println(" " + t);
                    System.out.println("Boss, you have " + tasks.size() + " tasks in the list.");
                    System.out.println(separator);
                } else if (command.startsWith("deadline")) {
                    String details = command.length() > 8 ? command.substring(8).trim() : "";
                    String[] parts = details.split(" /by ", 2);
                    if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                        throw new FridayException("Seems like you have made a mistake boss, a deadline cannot be empty.");
                    }
                    String desc = parts[0].trim();
                    String by = parts[1].trim();
                    Deadline t = new Deadline(desc, by);
                    tasks.add(t);
                    saveTasks(tasks);
                    printAddMessage(t, tasks.size(), separator);
                } else if (command.startsWith("event")) {
                    String details = command.length() > 5 ? command.substring(5).trim() : "";
                    int fromIdx = details.indexOf(" /from ");
                    int toIdx = details.indexOf(" /to ");
                    if (fromIdx == -1 || toIdx == -1) {
                        throw new FridayException("Seems like you have made a mistake boss, an event needs /from and /to.");
                    }
                    String desc = details.substring(0, fromIdx).trim();
                    String from = details.substring(fromIdx + 7, toIdx).trim();
                    String to = details.substring(toIdx + 5).trim();
                    if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                        throw new FridayException("Seems like you have made a mistake boss, an event cannot have empty fields.");
                    }
                    Event t = new Event(desc, from, to);
                    tasks.add(t);
                    saveTasks(tasks);
                    printAddMessage(t, tasks.size(), separator);
                } else {
                    throw new FridayException("I'm sorry boss, I didn't quite catch that...");
                }
            } catch (FridayException e) {
                System.out.println(separator);
                System.out.println(" " + e.getMessage());
                System.out.println(separator);
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                System.out.println(separator);
                System.out.println("Apologies boss, that task number isn't recorded in my database.");
                System.out.println(separator);
            }
        }
        scanner.close();
    }

    private static void printAddMessage(Task t, int totalTasks, String separator) {
        System.out.println(separator);
        System.out.println("Affirmative. I've added this task:");
        System.out.println(" " + t);
        System.out.println("Boss, you have " + totalTasks + " tasks in the list.");
        System.out.println(separator);
    }

    private static List<Task> loadTasks() {
        List<Task> list = new ArrayList<>();
        try {
            if (!Files.exists(SAVE_PATH)) {
                if (SAVE_PATH.getParent() != null) Files.createDirectories(SAVE_PATH.getParent());
                return list;
            }
            try (BufferedReader br = Files.newBufferedReader(SAVE_PATH, StandardCharsets.UTF_8)) {
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

    private static void saveTasks(List<Task> tasks) {
        try {
            if (SAVE_PATH.getParent() != null) Files.createDirectories(SAVE_PATH.getParent());
            try (BufferedWriter bw = Files.newBufferedWriter(SAVE_PATH, StandardCharsets.UTF_8)) {
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
