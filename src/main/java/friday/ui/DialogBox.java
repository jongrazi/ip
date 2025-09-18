package friday.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class DialogBox extends HBox {

    public DialogBox(String text, Image avatar, boolean isUser) {
        Label label = new Label(text);
        label.setWrapText(true);

        ImageView img = new ImageView(avatar);
        img.setFitWidth(80);
        img.setFitHeight(80);
        img.setPreserveRatio(true);

        setSpacing(10);

        if (isUser) {
            getChildren().addAll(label, img);
            setAlignment(Pos.TOP_RIGHT);
        } else {
            getChildren().addAll(img, label);
            setAlignment(Pos.TOP_LEFT);
        }

        label.setMaxWidth(400);
    }
}
