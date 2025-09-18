package friday.ui;

import friday.FridayException;
import friday.parser.Parser;
import friday.storage.Storage;
import friday.task.Deadline;
import friday.task.Event;
import friday.task.Task;
import friday.task.TaskList;
import friday.task.Todo;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class MainWindow {
    @FXML private ScrollPane scrollPane;
    @FXML private VBox dialogContainer;
    @FXML private TextField userInput;
    @FXML private Button sendButton;

    private Ui ui;
    private Storage storage;
    private TaskList tasks;

    // Avatars
    private Image userImage;
    private Image fridayImage;

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        if (userInput != null) {
            userInput.setOnAction(e -> handleUserInput());
        }

        // load avatars from resources/images/
        userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
        fridayImage = new Image(this.getClass().getResourceAsStream("/images/friday.png"));
    }

    /** Called by Main.java after FXML load. */
    public void init(Ui ui, Storage storage, TaskList tasks) {
        this.ui = ui;
        this.storage = storage;
        this.tasks = tasks;
    }

    public void showFridayMessage(String msg) {
        addBot(msg);
    }

    @FXML
    void handleUserInput() {
        String input = userInput.getText() == null ? "" : userInput.getText().trim();
        if (input.isEmpty()) return;

        addUser(input);
        String response = getResponse(input);
        addBot(response);
        userInput.clear();

        if (input.equalsIgnoreCase("bye")) {
            Platform.exit();
        }
    }

    // === Command handling: mirrors your CLI behaviour ===
    private String getResponse(String command) {
        try {
            if (command.equalsIgnoreCase("bye")) {
                return "Affirmative, goodnight boss.";
            } else if (command.equalsIgnoreCase("help")) {
                return String.join("\n",
                        "Okay boss, here are the available commands:",
                        "  list",
                        "  todo <desc>",
                        "  deadline <desc> /by <yyyy-mm-dd>",
                        "  event <desc> /from <yyyy-mm-dd> /to <yyyy-mm-dd>",
                        "  mark <number> | unmark <number> | delete <number>",
                        "  find <keyword>",
                        "  help",
                        "  bye"
                );
            } else if (command.equalsIgnoreCase("list")) {
                StringBuilder sb = new StringBuilder("Affirmative, loading tasks...\n");
                for (int i = 0; i < tasks.size(); i++) {
                    sb.append(" ").append(i + 1).append(". ").append(tasks.get(i)).append("\n");
                }
                return sb.toString().trim();
            } else if (command.startsWith("delete")) {
                String[] parts = Parser.splitOnce(command, "\\s+");
                if (parts.length < 2) throw new FridayException("Apologies boss, that task number isn't recorded in my database.");
                int index = Integer.parseInt(parts[1]) - 1;
                Task removed = tasks.remove(index);
                storage.save(tasks.asList());
                return "Noted. I've removed this task:\n " + removed + "\nBoss, you have " + tasks.size() + " tasks in the list.";
            } else if (command.startsWith("mark")) {
                String[] parts = Parser.splitOnce(command, "\\s+");
                if (parts.length < 2) throw new FridayException("Apologies boss, that task number isn't recorded in my database.");
                int index = Integer.parseInt(parts[1]) - 1;
                Task t = tasks.get(index);
                t.markAsDone();
                storage.save(tasks.asList());
                return "Okay boss, I have marked this task as done:\n " + t;
            } else if (command.startsWith("unmark")) {
                String[] parts = Parser.splitOnce(command, "\\s+");
                if (parts.length < 2) throw new FridayException("Apologies boss, that task number isn't recorded in my database.");
                int index = Integer.parseInt(parts[1]) - 1;
                Task t = tasks.get(index);
                t.markAsNotDone();
                storage.save(tasks.asList());
                return "Okay boss, I have marked this task as not done yet:\n " + t;
            } else if (command.startsWith("todo")) {
                String desc = command.length() > 4 ? command.substring(4).trim() : "";
                if (desc.isEmpty()) throw new FridayException("Seems like you have made a mistake boss, a todo cannot be empty.");
                Task t = new Todo(desc);
                tasks.add(t);
                storage.save(tasks.asList());
                return "Got it boss. I have added this task:\n " + t + "\nBoss, you have " + tasks.size() + " tasks in the list.";
            } else if (command.startsWith("deadline")) {
                String details = command.length() > 8 ? command.substring(8).trim() : "";
                String[] parts = Parser.splitOnce(details, " /by ");
                if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                    throw new FridayException("Seems like you have made a mistake boss, a deadline cannot be empty.");
                }
                Deadline t = new Deadline(parts[0].trim(), parts[1].trim());
                tasks.add(t);
                storage.save(tasks.asList());
                return "Affirmative. I've added this task:\n " + t + "\nBoss, you have " + tasks.size() + " tasks in the list.";
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
                return "Affirmative. I've added this task:\n " + t + "\nBoss, you have " + tasks.size() + " tasks in the list.";
            } else if (command.startsWith("find")) {
                String keyword = command.length() > 4 ? command.substring(4).trim() : "";
                if (keyword.isEmpty()) throw new FridayException("Seems like you have made a mistake boss, a find cannot be empty.");
                String needle = keyword.toLowerCase();
                StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
                int shown = 0;
                for (int i = 0; i < tasks.size(); i++) {
                    Task t = tasks.get(i);
                    if (t.getDescription().toLowerCase().contains(needle)) {
                        sb.append(" ").append(++shown).append(". ").append(t).append("\n");
                    }
                }
                if (shown == 0) sb.append(" (no matching tasks)\n");
                return sb.toString().trim();
            } else {
                return "I'm sorry boss, I didn't quite catch that.";
            }
        } catch (FridayException e) {
            return e.getMessage();
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return "Apologies boss, that task number isn't recorded in my database.";
        }
    }

    // === Updated: use DialogBox with avatars ===
    private void addUser(String text) {
        dialogContainer.getChildren().add(new DialogBox("> " + text, userImage, true));
    }

    private void addBot(String text) {
        dialogContainer.getChildren().add(new DialogBox(text, fridayImage, false));
    }
}
