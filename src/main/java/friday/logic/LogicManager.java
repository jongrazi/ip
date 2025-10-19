package friday.logic;

import friday.FridayException;
import friday.command.Command;
import friday.storage.Storage;
import friday.task.TaskList;

public class LogicManager implements Logic {
    private final TaskList tasks;
    private final Storage storage;
    private final CommandRouter router;

    public LogicManager(TaskList tasks, Storage storage) {
        this.tasks = tasks;
        this.storage = storage;
        this.router = new CommandRouter();
    }

    @Override
    public CommandResult handle(String userInput) {
        try {
            Command cmd = router.route(userInput);
            String msg = cmd.execute(tasks, storage);
            return new CommandResult(msg, cmd.isExit());
        } catch (FridayException e) {
            return new CommandResult(e.getMessage(), false);
        } catch (java.time.format.DateTimeParseException e) {
            return new CommandResult("Seems like you have made a mistake boss, please use ISO date format (yyyy-mm-dd).", false);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return new CommandResult("Apologies boss, that task number isn't recorded in my database.", false);
        }
    }
}
