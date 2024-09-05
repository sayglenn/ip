package ginger.gui;

import ginger.Ginger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Ginger ginger;

    private Image mickeyImage = new Image(this.getClass().getResourceAsStream("/images/mickey.jpg"));
    private Image sunshineImage = new Image(this.getClass().getResourceAsStream("/images/sunshine.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Ginger instance */
    public void setGinger(Ginger g) {
        ginger = g;
        dialogContainer.getChildren().add(
                DialogBox.getGingerDialog("Hello! I'm Ginger. How can I spice things up for you?" , sunshineImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = ginger.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, mickeyImage),
                DialogBox.getGingerDialog(response, sunshineImage)
        );
        userInput.clear();
    }
}

