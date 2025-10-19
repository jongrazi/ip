package friday.command;

import friday.storage.Storage;
import friday.task.TaskList;

public abstract class Command {
    /**
     * Executes the command against the given TaskList and Storage.
     */
    public abstract String execute(TaskList tasks, Storage storage);

    public boolean isExit() {
        return false;
    }
}
