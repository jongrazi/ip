package friday.command;

import friday.FridayException;
import friday.parser.CommandParser;
import friday.storage.Storage;
import friday.task.TaskList;
import friday.task.*;

public class AddTodoCommand extends Command {
    private final String desc;

    public AddTodoCommand(String desc) { this.desc = desc; }

    @Override
    public String execute(TaskList tasks, Storage storage) {
        if (desc.isEmpty()) {
            throw new FridayException("Seems like you have made a mistake boss, a todo cannot be empty.");
        }
        Todo t = new Todo(desc);
        tasks.add(t);
        storage.save(tasks.asList());
        return "Got it boss. I have added this task:\n  " + t + "\nBoss, you have " + tasks.size() + " tasks in the list.";
    }

    public static class Parser implements CommandParser {
        @Override
        public Command parse(String wholeInput) {
            String rest = wholeInput.length() > 4 ? wholeInput.substring(4).trim() : "";
            return new AddTodoCommand(rest);
        }
    }
}

