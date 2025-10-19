package friday.command;

import friday.parser.CommandParser;
import friday.storage.Storage;
import friday.task.Task;
import friday.task.TaskList;
import friday.FridayException;

public class MarkCommand extends Command {
    private final int index1Based;

    public MarkCommand(int index1Based) { this.index1Based = index1Based; }

    @Override
    public String execute(TaskList tasks, Storage storage) {
        int idx = index1Based - 1;
        if (idx < 0 || idx >= tasks.size()) {
            throw new FridayException("Apologies boss, that task number isn't recorded in my database.");
        }

        Task t = tasks.get(idx);
        t.markAsDone();
        storage.save(tasks.asList());
        return "Roger that boss. I've marked this task as done:\n  " + t;
    }

    public static class Parser implements CommandParser {
        @Override
        public Command parse(String wholeInput) {
            String rest = wholeInput.substring("mark".length()).trim();
            if (rest.isEmpty()) throw new FridayException("Please provide a task number to mark, boss.");
            try {
                int index = Integer.parseInt(rest);
                return new MarkCommand(index);
            } catch (NumberFormatException e) {
                throw new FridayException("Apologies boss, that task number isn't recorded in my database.");
            }
        }
    }
}
