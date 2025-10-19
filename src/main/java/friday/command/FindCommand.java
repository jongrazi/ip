package friday.command;

import friday.parser.CommandParser;
import friday.storage.Storage;
import friday.task.Task;
import friday.task.TaskList;
import friday.FridayException;

public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) { this.keyword = keyword; }

    @Override
    public String execute(TaskList tasks, Storage storage) {
        String q = keyword.trim();
        if (q.isEmpty()) {
            throw new FridayException("Seems like you have made a mistake boss, a find cannot be empty.");
        }
        String needle = q.toLowerCase();

        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:");
        int shown = 0;
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            if (t.getDescription().toLowerCase().contains(needle)) {
                sb.append("\n ").append(++shown).append(". ").append(t);
            }
        }
        if (shown == 0) {
            sb.append("\n (no matching tasks)");
        }
        return sb.toString();
    }

    public static class Parser implements CommandParser {
        @Override
        public Command parse(String wholeInput) {
            String rest = wholeInput.length() > 4 ? wholeInput.substring(4).trim() : "";
            return new FindCommand(rest);
        }
    }
}
