import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Friday {
    private static final Path SAVE_PATH = Paths.get("data", "duke.txt");

    public static void main(String[] args) {
        String separator = "_".repeat(60);

        Ui ui = new Ui();
        Storage storage = new Storage(SAVE_PATH);
        TaskList tasks = new TaskList(storage.load());
        ui.showWelcome(separator);

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
                    String[] parts = Parser.splitOnce(command, "\\s+");
                    if (parts.length < 2) {
                        throw new FridayException("Apologies boss, that task number isn't recorded in my database.");
                    }
                    int index = Integer.parseInt(parts[1]) - 1;
                    Task removed = tasks.remove(index);
                    storage.save(tasks.asList());
                    System.out.println(separator);
                    System.out.println("Noted. I've removed this task:");
                    System.out.println(" " + removed);
                    System.out.println("Boss, you have " + tasks.size() + " tasks in the list.");
                    System.out.println(separator);
                } else if (command.startsWith("mark")) {
                    String[] parts = Parser.splitOnce(command, "\\s+");
                    if (parts.length < 2) {
                        throw new FridayException("Apologies boss, that task number isn't recorded in my database.");
                    }
                    int index = Integer.parseInt(parts[1]) - 1;
                    Task t = tasks.get(index);
                    t.markAsDone();
                    storage.save(tasks.asList());
                    System.out.println(separator);
                    System.out.println("Okay boss, I have marked this task as done:");
                    System.out.println(" " + t);
                    System.out.println(separator);
                } else if (command.startsWith("unmark")) {
                    String[] parts = Parser.splitOnce(command, "\\s+");
                    if (parts.length < 2) {
                        throw new FridayException("Apologies boss, that task number isn't recorded in my database.");
                    }
                    int index = Integer.parseInt(parts[1]) - 1;
                    Task t = tasks.get(index);
                    t.markAsNotDone();
                    storage.save(tasks.asList());
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
                    storage.save(tasks.asList());
                    System.out.println(separator);
                    System.out.println("Got it boss. I have added this task:");
                    System.out.println(" " + t);
                    System.out.println("Boss, you have " + tasks.size() + " tasks in the list.");
                    System.out.println(separator);
                } else if (command.startsWith("deadline")) {
                    String details = command.length() > 8 ? command.substring(8).trim() : "";
                    String[] parts = Parser.splitOnce(details, " /by ");
                    if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                        throw new FridayException("Seems like you have made a mistake boss, a deadline cannot be empty.");
                    }
                    String desc = parts[0].trim();
                    String by = parts[1].trim();
                    Deadline t = new Deadline(desc, by);
                    tasks.add(t);
                    storage.save(tasks.asList());
                    printAddMessage(t, tasks.size(), separator);
                } else if (command.startsWith("event")) {
                    String details = command.length() > 5 ? command.substring(5).trim() : "";
                    int fromIdx = Parser.indexOf(details, " /from ");
                    int toIdx = Parser.indexOf(details, " /to ");
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
                    storage.save(tasks.asList());
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
}
