
package friday;

import friday.logic.CommandResult;
import friday.logic.Logic;
import friday.logic.LogicManager;
import friday.storage.Storage;
import friday.task.TaskList;
import friday.ui.MainWindow;
import friday.ui.Ui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.nio.file.Path;
import java.nio.file.Paths;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Ui ui = new Ui();
        Path savePath = Paths.get("data", "duke.txt");
        Storage storage = new Storage(savePath);
        TaskList tasks = new TaskList(storage.load());
        Logic logic = new LogicManager(tasks, storage);

        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainWindow controller = fxmlLoader.getController();

        controller.init(ui, storage, tasks, logic);

        stage.setScene(scene);
        stage.setTitle("F.R.I.D.A.Y");
        stage.show();


        String welcome =
                "It's good to have you back sir, I am F.R.I.D.A.Y\n"
                        + "What can I do for you sir?\n"
                        + "Type 'help' to learn more\n";
        controller.showFridayMessage(welcome);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
