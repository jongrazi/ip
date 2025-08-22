import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Friday {
    public static void main(String[] args) {
        String separator = "_".repeat(60);

        System.out.println(separator);
        System.out.println("It's good to have you back sir, I am F.R.I.D.A.Y");
        System.out.println("What can I do for you sir?");
        System.out.println(separator);

        List<Task> tasks = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine().trim();

            if (command.trim().equalsIgnoreCase("bye")) {
                //if bye then go to goodbye block
                System.out.println(separator);
                System.out.println("Affirmative, goodnight boss.");
                System.out.println(separator);
                break;

            } else if (command.equalsIgnoreCase("list")) {
                //if list then show the stored list
                System.out.println(separator);
                System.out.println("Affirmative, loading tasks...");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks.get(i));
            }

            System.out.println(separator);

            } else if (command.startsWith("mark ")) {
                int index = Integer.parseInt(command.substring(5)) - 1;
                if (index >= 0 && index < tasks.size()) {
                    Task t = tasks.get(index);
                    t.markAsDone();
                    System.out.println(separator);
                    System.out.println("Okay boss, I have marked this task as done:");
                    System.out.println(" " + t);
                    System.out.println(separator);
                }

            } else if (command.startsWith("unmark ")) {
                int index = Integer.parseInt(command.substring(7)) - 1;
                if (index >= 0 && index < tasks.size()) {
                    Task t = tasks.get(index);
                    t.markAsNotDone();
                    System.out.println(separator);
                    System.out.println("Okay boss, I have marked this task as not done yet:");
                    System.out.println(" " + t);
                    System.out.println(separator);
                }

            } else if (command.startsWith("todo ")) {
                String desc = command.substring(5).trim();
                Task t = new Todo(desc);
                tasks.add(t);
                System.out.println(separator);
                System.out.println("Got it boss. I have added this task:");
                System.out.println(" " + t);
                System.out.println("Boss, you have " + tasks.size() + " tasks in the list.");
                System.out.println(separator);

            } else if (command.startsWith("deadline ")) {
                String details = command.substring(9).trim();
                String[] parts = details.split(" /by ", 2);
                if (parts.length == 2) {
                    String desc = parts[0].trim();
                    String date = parts[1].trim();
                    Deadline t = new Deadline(desc, date);
                    tasks.add(t);
                    printAddMessage(t, tasks.size(), separator);
                }

            } else if (command.startsWith("event ")) {
                String details = command.substring(6).trim();
                int fromIdx = details.indexOf(" /from ");
                int toIdx = details.indexOf(" /to ");
                if (fromIdx != -1 && toIdx != -1) {
                    String desc = details.substring(0, fromIdx).trim();
                    String from = details.substring(fromIdx + 7, toIdx).trim();
                    String to = details.substring(toIdx + 5).trim();
                    Event t = new Event(desc, from, to);
                    tasks.add(t);
                    printAddMessage(t, tasks.size(), separator);
                }

            } else {
                Task t = new Task(command);
                tasks.add(t);
                System.out.println(separator);
                System.out.println("added: " + command);
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
