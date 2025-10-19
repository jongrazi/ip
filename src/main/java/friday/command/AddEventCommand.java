package friday.command;

import friday.FridayException;
import friday.parser.CommandParser;
import friday.storage.Storage;
import friday.task.Event;
import friday.task.TaskList;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AddEventCommand extends Command {
    private final String desc;
    private final String from;
    private final String to;

    public AddEventCommand(String desc, String from, String to) {
        this.desc = desc;
        this.from = from;
        this.to = to;
    }

    @Override
    public String execute(TaskList tasks, Storage storage) {
        if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new FridayException("Seems like you have made a mistake boss, an event cannot have empty fields.");
        }
        ensureIso(from);
        ensureIso(to);
        if (LocalDate.parse(from).isAfter(LocalDate.parse(to))) {
            throw new FridayException("Seems like you have made a mistake boss, /from date must not be after /to date.");
        }

        Event e = new Event(desc, from, to);
        tasks.add(e);
        storage.save(tasks.asList());
        return "Affirmative. I've added this task:\n "
                + e + "\nBoss, you have " + tasks.size() + " tasks in the list.";
    }

    public static class Parser implements CommandParser {
        private static final String CMD = "event";
        private static final String TOK_FROM = "/from";
        private static final String TOK_TO   = "/to";

        @Override
        public Command parse(String wholeInput) {
            String args = wholeInput.length() > CMD.length()
                    ? wholeInput.substring(CMD.length()).trim()
                    : "";
            if (args.isEmpty()) {
                throw new FridayException("Seems like you have made a mistake boss, an event cannot be empty.");
            }

            int iFrom = args.indexOf(TOK_FROM);
            int iTo   = args.indexOf(TOK_TO);
            if (iFrom == -1 || iTo == -1 || iFrom > iTo) {
                throw new FridayException("Seems like you have made a mistake boss, an event needs /from and /to.");
            }

            String desc = args.substring(0, iFrom).trim();
            String from = args.substring(iFrom + TOK_FROM.length(), iTo).trim();
            String to   = args.substring(iTo + TOK_TO.length()).trim();
            if (from.startsWith(":")) from = from.substring(1).trim();
            if (to.startsWith(":"))   to   = to.substring(1).trim();

            if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                throw new FridayException("Seems like you have made a mistake boss, an event cannot have empty fields.");
            }

            ensureIso(from);
            ensureIso(to);
            if (LocalDate.parse(from).isAfter(LocalDate.parse(to))) {
                throw new FridayException("Seems like you have made a mistake boss, /from date must not be after /to date.");
            }

            return new AddEventCommand(desc, from, to);
        }
    }

    private static void ensureIso(String s) {
        try { LocalDate.parse(s); }
        catch (DateTimeParseException e) {
            throw new FridayException("Seems like you have made a mistake boss, please use ISO date format (yyyy-mm-dd).");
        }
    }
}

