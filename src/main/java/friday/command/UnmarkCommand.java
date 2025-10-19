package friday.command;

import friday.parser.CommandParser;
import friday.storage.Storage;
import friday.task.Task;
import friday.task.TaskList;
import friday.FridayException;

public class UnmarkCommand extends Command {
    private final int index1Based;

    public UnmarkCommand(int index1Based) { this.index1Based = index1Based; }

    @Override
    public String execute(TaskList tasks, Storage storage) {
        int idx = index1Based - 1;
        if (idx < 0 || idx >= tasks.size()) {
            throw new FridayException("Apologies boss, that task number isn't recorded in my database.");
        }

        Task t = tasks.get(idx);
        t.markAsNotDone();
        storage.save(tasks.asList());
        return "Understood boss. I've marked this task as not done yet:\n  " + t;
    }

    public static class Parser implements CommandParser {
        @Override
        public Command parse(String wholeInput) {
            String rest = wholeInput.substring("unmark".length()).trim();
            if (rest.isEmpty()) throw new FridayException("Please provide a task number to unmark, boss.");
            try {
                int index = Integer.parseInt(rest);
                return new UnmarkCommand(index);
            } catch (NumberFormatException e) {
                throw new FridayException("Apologies boss, that task number isn't recorded in my database.");
            }
        }
    }
}
