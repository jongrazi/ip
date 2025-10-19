
package friday.logic;

import friday.FridayException;
import friday.command.*;
import friday.parser.CommandParser;

import java.util.HashMap;
import java.util.Map;

public class CommandRouter {
    private final Map<String, CommandParser> parsers = new HashMap<>();

    public CommandRouter() {
        parsers.put("help",    new HelpCommand.Parser());
        parsers.put("bye",     new ByeCommand.Parser());
        parsers.put("list",    new ListCommand.Parser());
        parsers.put("todo",    new AddTodoCommand.Parser());
        parsers.put("deadline",new AddDeadlineCommand.Parser());
        parsers.put("event",   new AddEventCommand.Parser());
        parsers.put("mark",    new MarkCommand.Parser());
        parsers.put("unmark",  new UnmarkCommand.Parser());
        parsers.put("delete",  new DeleteCommand.Parser());
        parsers.put("find",    new FindCommand.Parser());
    }

    public Command route(String input) {
        String trimmed = (input == null ? "" : input).trim();

        String keyword = trimmed.split("\\s+", 2)[0].toLowerCase();
        CommandParser p = parsers.get(keyword);
        if (p == null) {
            throw new FridayException("I'm sorry boss, I didn't quite catch that");
        }
        return p.parse(trimmed);
    }
}