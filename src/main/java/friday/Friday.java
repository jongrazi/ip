package friday;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import friday.parser.Parser;
import friday.storage.Storage;
import friday.task.Deadline;
import friday.task.Event;
import friday.task.Task;
import friday.task.TaskList;
import friday.task.Todo;
import friday.ui.Ui;

public class Friday {
    private static final Path SAVE_PATH = Paths.get("data", "duke.txt");
    private static final String SEPARATOR = "_".repeat(60);

    private static final String CMD_BYE = "bye";
    private static final String CMD_LIST = "list";
    private static final String CMD_TODO = "todo";
    private static final String CMD_DEADLINE = "deadline";
    private static final String CMD_EVENT = "event";
    private static final String CMD_MARK = "mark";
    private static final String CMD_UNMARK = "unmark";
    private static final String CMD_DELETE = "delete";
    private static final String CMD_FIND = "find";

    private static final String TOK_BY = " /by ";
    private static final String TOK_FROM = " /from ";
    private static final String TOK_TO = " /to ";

    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage(SAVE_PATH);
        TaskList tasks = new TaskList(storage.load());
        ui.showWelcome(SEPARATOR);

        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine().trim();
                try {
                    if (command.equalsIgnoreCase(CMD_BYE)) {
                        printGoodbye();
                        break;
                    } else if (command.equalsIgnoreCase(CMD_LIST)) {
                        printTaskList(tasks);
                    } else if (command.startsWith(CMD_DELETE)) {
                        handleDelete(command, tasks, storage);
                    } else if (command.startsWith(CMD_MARK)) {
                        handleMark(command, tasks, storage);
                    } else if (command.startsWith(CMD_UNMARK)) {
                        handleUnmark(command, tasks, storage);
                    } else if (command.startsWith(CMD_TODO)) {
                        handleTodo(command, tasks, storage);
                    } else if (command.startsWith(CMD_DEADLINE)) {
                        handleDeadline(command, tasks, storage);
                    } else if (command.startsWith(CMD_EVENT)) {
                        handleEvent(command, tasks, storage);
                    } else if (command.startsWith(CMD_FIND)) {
                        handleFind(command, tasks);
                    } else {
                        throw new FridayException("I'm sorry boss, I didn't quite catch that.");
                    }
                } catch (FridayException e) {
                    System.out.println(SEPARATOR);
                    System.out.println(" " + e.getMessage());
                    System.out.println(SEPARATOR);
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    System.out.println(SEPARATOR);
                    System.out.println("Apologies boss, that task number isn't recorded in my database.");
                    System.out.println(SEPARATOR);
                }
            }
        }
    }

    private static void handleDelete(String command, TaskList tasks, Storage storage) throws FridayException {
        String[] parts = Parser.splitOnce(command, "\\s+");
        if (parts.length < 2) {
            throw new FridayException("Apologies boss, that task number isn't recorded in my database.");
        }
        int index = Integer.parseInt(parts[1]) - 1;
        Task removed = tasks.remove(index);
        storage.save(tasks.asList());
        System.out.println(SEPARATOR);
        System.out.println("Noted. I've removed this task:");
        System.out.println(" " + removed);
        System.out.println("Boss, you have " + tasks.size() + " tasks in the list.");
        System.out.println(SEPARATOR);
    }

    private static void handleMark(String command, TaskList tasks, Storage storage) throws FridayException {
        String[] parts = Parser.splitOnce(command, "\\s+");
        if (parts.length < 2) {
            throw new FridayException("Apologies boss, that task number isn't recorded in my database.");
        }
        int index = Integer.parseInt(parts[1]) - 1;
        Task t = tasks.get(index);
        t.markAsDone();
        storage.save(tasks.asList());
        System.out.println(SEPARATOR);
        System.out.println("Okay boss, I have marked this task as done:");
        System.out.println(" " + t);
        System.out.println(SEPARATOR);
    }

    private static void handleUnmark(String command, TaskList tasks, Storage storage) throws FridayException {
        String[] parts = Parser.splitOnce(command, "\\s+");
        if (parts.length < 2) {
            throw new FridayException("Apologies boss, that task number isn't recorded in my database.");
        }
        int index = Integer.parseInt(parts[1]) - 1;
        Task t = tasks.get(index);
        t.markAsNotDone();
        storage.save(tasks.asList());
        System.out.println(SEPARATOR);
        System.out.println("Okay boss, I have marked this task as not done yet:");
        System.out.println(" " + t);
        System.out.println(SEPARATOR);
    }

    private static void handleTodo(String command, TaskList tasks, Storage storage) throws FridayException {
        String desc = command.length() > CMD_TODO.length()
                ? command.substring(CMD_TODO.length()).trim()
                : "";
        if (desc.isEmpty()) {
            throw new FridayException("Seems like you have made a mistake boss, a todo cannot be empty.");
        }
        Task t = new Todo(desc);
        tasks.add(t);
        storage.save(tasks.asList());
        System.out.println(SEPARATOR);
        System.out.println("Got it boss. I have added this task:");
        System.out.println(" " + t);
        System.out.println("Boss, you have " + tasks.size() + " tasks in the list.");
        System.out.println(SEPARATOR);
    }

    private static void handleDeadline(String command, TaskList tasks, Storage storage) throws FridayException {
        String details = command.length() > CMD_DEADLINE.length()
                ? command.substring(CMD_DEADLINE.length()).trim()
                : "";
        String[] parts = Parser.splitOnce(details, TOK_BY);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new FridayException("Seems like you have made a mistake boss, a deadline cannot be empty.");
        }
        String desc = parts[0].trim();
        String by = parts[1].trim();
        Deadline t = new Deadline(desc, by);
        tasks.add(t);
        storage.save(tasks.asList());
        printAddMessage(t, tasks.size());
    }

    private static void handleEvent(String command, TaskList tasks, Storage storage) throws FridayException {
        String details = command.length() > CMD_EVENT.length()
                ? command.substring(CMD_EVENT.length()).trim()
                : "";
        int fromIdx = Parser.indexOf(details, TOK_FROM);
        int toIdx = Parser.indexOf(details, TOK_TO);
        if (fromIdx == -1 || toIdx == -1) {
            throw new FridayException("Seems like you have made a mistake boss, an event needs /from and /to.");
        }
        String desc = details.substring(0, fromIdx).trim();
        String from = details.substring(fromIdx + TOK_FROM.length(), toIdx).trim();
        String to = details.substring(toIdx + TOK_TO.length()).trim();
        if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new FridayException("Seems like you have made a mistake boss, an event cannot have empty fields.");
        }
        Event t = new Event(desc, from, to);
        tasks.add(t);
        storage.save(tasks.asList());
        printAddMessage(t, tasks.size());
    }

    private static void handleFind(String command, TaskList tasks) throws FridayException {
        String keyword = command.length() > CMD_FIND.length()
                ? command.substring(CMD_FIND.length()).trim()
                : "";
        if (keyword.isEmpty()) {
            throw new FridayException("Seems like you have made a mistake boss, a find cannot be empty.");
        }
        String needle = keyword.toLowerCase();

        System.out.println(SEPARATOR);
        System.out.println("Here are the matching tasks in your list:");
        int shown = 0;
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            if (t.getDescription().toLowerCase().contains(needle)) {
                System.out.println(" " + (++shown) + ". " + t);
            }
        }
        if (shown == 0) {
            System.out.println(" (no matching tasks)");
        }
        System.out.println(SEPARATOR);
    }

    private static void printTaskList(TaskList tasks) {
        System.out.println(SEPARATOR);
        System.out.println("Affirmative, loading tasks...");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + tasks.get(i));
        }
        System.out.println(SEPARATOR);
    }

    private static void printGoodbye() {
        System.out.println(SEPARATOR);
        System.out.println("Affirmative, goodnight boss.");
        System.out.println(SEPARATOR);
    }

    private static void printAddMessage(Task t, int totalTasks) {
        System.out.println(SEPARATOR);
        System.out.println("Affirmative. I've added this task:");
        System.out.println(" " + t);
        System.out.println("Boss, you have " + totalTasks + " tasks in the list.");
        System.out.println(SEPARATOR);
    }
}
