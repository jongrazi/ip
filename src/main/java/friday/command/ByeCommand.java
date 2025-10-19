package friday.command;

import friday.parser.CommandParser;
import friday.storage.Storage;
import friday.task.TaskList;

/**
 * Exits the application.
 */
public class ByeCommand extends Command {
    @Override
    public String execute(TaskList tasks, Storage storage) {
        return "Affirmative, goodnight boss.";
    }

    @Override
    public boolean isExit() {
        return true;
    }

    public static class Parser implements CommandParser {
        @Override
        public Command parse(String wholeInput) {
            return new ByeCommand();
        }
    }
}
