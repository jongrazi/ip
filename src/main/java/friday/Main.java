package friday;

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

/**
 * GUI entrypoint that mirrors CLI behaviour and welcome.
 */
public class Main extends Application {

    private Ui ui;
    private Storage storage;
    private TaskList tasks;

    @Override
    public void start(Stage stage) throws Exception {
        ui = new Ui();
        Path savePath = Paths.get("data", "duke.txt");   // same file as CLI
        storage = new Storage(savePath);
        tasks = new TaskList(storage.load());

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Friday");

        MainWindow controller = fxmlLoader.getController();
        controller.init(ui, storage, tasks);

        stage.show();


        String welcome =
                "It's good to have you back sir, I am F.R.I.D.A.Y\n"
                + "What can I do for you sir?\n"
                + "Type 'help' to learn more\n";

        controller.showFridayMessage(welcome); // shows in GUI
    }

    public static void main(String[] args) {
        launch(args);
    }
}
