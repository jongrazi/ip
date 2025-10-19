package friday.parser;

import friday.command.Command;

public interface CommandParser {
    Command parse(String wholeInput);
}