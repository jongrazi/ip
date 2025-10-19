// src/main/java/friday/ui/DialogBox.java
package friday.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class DialogBox extends HBox {

    public DialogBox(String text, Image avatarImage, boolean isUser) {

        setSpacing(10);
        setPadding(new Insets(8, 12, 8, 12));
        setMinHeight(Region.USE_PREF_SIZE);

        ImageView avatar = new ImageView(avatarImage);
        avatar.setFitWidth(40);
        avatar.setFitHeight(40);
        Rectangle clip = new Rectangle(40, 40);
        clip.setArcWidth(12);
        clip.setArcHeight(12);
        avatar.setClip(clip);

        Label label = new Label(text);
        label.setWrapText(true);
        label.setMaxWidth(360);

        StackPane bubble = new StackPane(label);
        bubble.setPadding(new Insets(10));
        if (isUser) {
            bubble.setStyle("-fx-background-color: #D9EAFE; -fx-background-radius: 12;");
        } else {
            bubble.setStyle("-fx-background-color: #EFEFEF; -fx-background-radius: 12;");
        }

        if (isUser) {
            setAlignment(Pos.TOP_RIGHT);
            getChildren().addAll(bubble, avatar);
        } else {
            setAlignment(Pos.TOP_LEFT);
            getChildren().addAll(avatar, bubble);
        }
    }
}
