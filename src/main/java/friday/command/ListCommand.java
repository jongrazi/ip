package friday.command;

import friday.parser.CommandParser;
import friday.storage.Storage;
import friday.task.Task;
import friday.task.TaskList;

public class ListCommand extends Command {
    @Override
    public String execute(TaskList tasks, Storage storage) {
        if (tasks.size() == 0) {
            return "Boss, your task list is empty.";
        }
        StringBuilder sb = new StringBuilder("Here are your tasks, boss:");
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            sb.append("\n").append(i + 1).append(". ").append(t);
        }
        return sb.toString();
    }

    public static class Parser implements CommandParser {
        @Override public Command parse(String wholeInput) {
            return new ListCommand();
        }
    }
}
