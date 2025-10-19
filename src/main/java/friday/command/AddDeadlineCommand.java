// friday/command/AddDeadlineCommand.java
package friday.command;

import friday.FridayException;
import friday.parser.CommandParser;
import friday.storage.Storage;
import friday.task.Deadline;
import friday.task.TaskList;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AddDeadlineCommand extends Command {
    private final String desc;
    private final String by;

    public AddDeadlineCommand(String desc, String by) {
        this.desc = desc;
        this.by = by;
    }

    @Override
    public String execute(TaskList tasks, Storage storage) {
        if (desc.isEmpty() || by.isEmpty()) {
            throw new FridayException("Seems like you have made a mistake boss, a deadline cannot have empty fields.");
        }
        ensureIso(by);

        Deadline d = new Deadline(desc, by);
        tasks.add(d);
        storage.save(tasks.asList());
        return "Got it boss. I have added this task:\n " + d
                + "\nBoss, you have " + tasks.size() + " tasks in the list.";
    }

    public static class Parser implements CommandParser {
        private static final String CMD = "deadline";
        private static final String TOK_BY = "/by";

        @Override
        public Command parse(String wholeInput) {
            String args = wholeInput.length() > CMD.length()
                    ? wholeInput.substring(CMD.length()).trim()
                    : "";
            if (args.isEmpty()) {
                throw new FridayException("Seems like you have made a mistake boss, a deadline cannot be empty.");
            }

            int pivot = args.indexOf(TOK_BY);
            if (pivot < 0) {
                throw new FridayException("Seems like you have made a mistake boss, please provide /by <yyyy-mm-dd>.");
            }

            String desc = args.substring(0, pivot).trim();
            String by = args.substring(pivot + TOK_BY.length()).trim();
            if (by.startsWith(":")) by = by.substring(1).trim();

            if (desc.isEmpty() || by.isEmpty()) {
                throw new FridayException("Seems like you have made a mistake boss, a deadline cannot have empty fields.");
            }
            ensureIso(by);
            return new AddDeadlineCommand(desc, by);
        }
    }

    private static void ensureIso(String s) {
        try { LocalDate.parse(s); }
        catch (DateTimeParseException e) {
            throw new FridayException("Seems like you have made a mistake boss, please use ISO date format (yyyy-mm-dd).");
        }
    }
}
