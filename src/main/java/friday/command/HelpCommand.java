package friday.command;

import friday.parser.CommandParser;
import friday.storage.Storage;
import friday.task.TaskList;

public class HelpCommand extends Command {

    @Override
    public String execute(TaskList tasks, Storage storage) {
        return String.join("\n",
                "Commands:",
                " list                                   - Show all tasks",
                " todo <desc>                            - Add a todo",
                " deadline <desc> /by <yyyy-mm-dd>       - Add a deadline",
                " event <desc> /from <d> /to <d>         - Add an event",
                " mark <index> / unmark <index>          - Toggle done",
                " delete <index>                         - Delete a task",
                " find <keyword>                         - Search tasks by description",
                " bye                                    - Exit",
                " help                                   - Show this help"
        );
    }

    public static class Parser implements CommandParser {
        @Override
        public Command parse(String wholeInput) {
            return new HelpCommand();
        }
    }
}
