package friday;

import friday.logic.CommandResult;
import friday.logic.Logic;
import friday.logic.LogicManager;
import friday.storage.Storage;
import friday.task.TaskList;
import friday.ui.Ui;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FridayCli {
    public static void main(String[] args) {
        final String SEP = "_".repeat(60);

        Ui ui = new Ui();
        ui.showWelcome(SEP);

        Path savePath = Paths.get("data", "duke.txt");
        Storage storage = new Storage(savePath);
        TaskList tasks = new TaskList(storage.load());
        Logic logic = new LogicManager(tasks, storage);

        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNextLine()) {
                String input = sc.nextLine();
                CommandResult res = logic.handle(input);

                System.out.println(SEP);
                System.out.println(res.getFeedbackToUser());
                System.out.println(SEP);

                if (res.isExit()) {
                    break;
                }
            }
        }
    }
}
