package friday.ui;

import friday.logic.CommandResult;
import friday.logic.Logic;
import friday.storage.Storage;
import friday.task.TaskList;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for MainWindow. all input is delegated to Logic.
 */
public class MainWindow extends AnchorPane {
    @FXML private ScrollPane scrollPane;
    @FXML private VBox dialogContainer;
    @FXML private TextField userInput;
    @FXML private Button sendButton;

    private final Image userImage   = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private final Image fridayImage = new Image(this.getClass().getResourceAsStream("/images/Friday.png"));

    private Ui ui;
    private Storage storage;
    private TaskList tasks;
    private Logic logic;

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void init(Ui ui, Storage storage, TaskList tasks, Logic logic) {
        this.ui = ui;
        this.storage = storage;
        this.tasks = tasks;
        this.logic = logic;
    }

    public void showFridayMessage(String message) {
        dialogContainer.getChildren().add(new DialogBox(message, fridayImage, false));
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();

        if (input != null && !input.isBlank()) {
            dialogContainer.getChildren().add(new DialogBox("> " + input, userImage, true));
        }

        CommandResult res = logic.handle(input == null ? "" : input);
        dialogContainer.getChildren().add(new DialogBox(res.getFeedbackToUser(), fridayImage, false));

        if (res.isExit()) {
            Platform.runLater(Platform::exit);
        }
        userInput.clear();
    }
}
