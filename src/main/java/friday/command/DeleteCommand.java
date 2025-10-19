package friday.command;

import friday.parser.CommandParser;
import friday.storage.Storage;
import friday.task.Task;
import friday.task.TaskList;
import friday.FridayException;

public class DeleteCommand extends Command {
    private final int index1Based;

    public DeleteCommand(int index1Based) { this.index1Based = index1Based; }

    @Override
    public String execute(TaskList tasks, Storage storage) {
        int idx = index1Based - 1;
        if (idx < 0 || idx >= tasks.size()) {
            throw new FridayException("Apologies boss, that task number isn't recorded in my database.");
        }
        Task removed = tasks.get(idx);
        tasks.remove(idx);
        storage.save(tasks.asList());
        return "Alright boss. I've removed this task:\n  " + removed + "\nBoss, you now have " + tasks.size() + " tasks in the list.";
    }

    public static class Parser implements CommandParser {
        @Override
        public Command parse(String wholeInput) {
            String rest = wholeInput.substring("delete".length()).trim();
            if (rest.isEmpty()) throw new FridayException("Please provide a task number to delete, boss.");
            try {
                int index = Integer.parseInt(rest);
                return new DeleteCommand(index);
            } catch (NumberFormatException e) {
                throw new FridayException("Apologies boss, that task number isn't recorded in my database.");
            }
        }
    }
}
