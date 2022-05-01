package view;

import controller.Controller;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import model.Game;
import model.Model;

/**
 * Contain all the view of the application
 * JavaFX View
 */
public class View implements PropertyChangeListener {

    private Controller controller;
    private Model model;
    private MenuStart mst;
    private Button btnTopLeft = new Button("");
    private Button btnTopRight = new Button("");
    private Button btnBottomLeft = new Button("");
    private Button btnBottomRight = new Button("");
    private SoundEffect sound;
    private String btnTopLeftColorReset = "#1a6e0f";
    private String btnTopRightColorReset = "#6e0f17";
    private String btnBottomLeftColorReset = "#6e6b0f";
    private String btnBottomRightColorReset = "#0f386e";

    private String btnTopLeftColorChange = "#2dc918";
    private String btnTopRightColorChange = "#cc1424";
    private String btnBottomLeftColorChange = "#dbd51d";
    private String btnBottomRightColorChange = "#1a6edb";

    /**
     * Basic controller that initialize the menu for the setting and launch a game
     * And other attribut in the class
     * @param controller Controller: the controller to call the method
     * @param model Model: the Observer class
     */
    public View(Controller controller, Model model) throws MidiUnavailableException {
        mst = new MenuStart();
        sound = new SoundEffect();
        this.controller = controller;
        this.model = model;
        model.addPropertyChangeListener(this);
        mst.addPropertyChangeListener(this);
    }

    /**
     * Initialize all the JavaFx view of the game with all the component
     * Add inside the action of the button
     * @param primaryStage Stage: the stage that contain all the component to show it 
     */
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Simon MB");

        StackPane root = new StackPane();
        root.setPrefSize(550, 550);
        BorderPane center = mst.getMenuStart();

        btnTopLeft.setBackground(new Background(new BackgroundFill(Color.web(btnTopLeftColorReset), CornerRadii.EMPTY, Insets.EMPTY)));
        btnTopRight.setBackground(new Background(new BackgroundFill(Color.web(btnTopRightColorReset), CornerRadii.EMPTY, Insets.EMPTY)));
        btnBottomLeft.setBackground(new Background(new BackgroundFill(Color.web(btnBottomLeftColorReset), CornerRadii.EMPTY, Insets.EMPTY)));
        btnBottomRight.setBackground(new Background(new BackgroundFill(Color.web(btnBottomRightColorReset), CornerRadii.EMPTY, Insets.EMPTY)));

        btnTopLeft.setPrefSize(500, 500);
        btnTopRight.setPrefSize(500, 500);
        btnBottomLeft.setPrefSize(500, 500);
        btnBottomRight.setPrefSize(500, 500);

        GridPane buttonColor = new GridPane();
        buttonColor.add(btnTopLeft, 0, 0);
        buttonColor.add(btnTopRight, 1, 0);
        buttonColor.add(btnBottomLeft, 0, 1);
        buttonColor.add(btnBottomRight, 1, 1);

        root.getChildren().add(buttonColor);
        root.getChildren().add(center);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * Initiliaze all the action on the color button with the particular color and sound
     */
    public void checkColorClick() {
        clickOnButtonColor(btnTopLeft, Color.GREEN, btnTopLeftColorChange, btnTopLeftColorReset, 69);
        clickOnButtonColor(btnTopRight, Color.RED, btnTopRightColorChange, btnTopRightColorReset, 71);
        clickOnButtonColor(btnBottomLeft, Color.YELLOW, btnBottomLeftColorChange, btnBottomLeftColorReset, 72);
        clickOnButtonColor(btnBottomRight, Color.BLUE, btnBottomRightColorChange, btnBottomRightColorReset, 74);
    }

    /**
     * Add the action on a specify button with a specific action that change the color and make sound for the clicker color button
     * And call the click method from the controller
     * @param button Button: the button to put the action
     * @param colorCheck Color: the color to check for the controller method
     * @param colorToChange String: the color to change on the button
     * @param colorReset String: the factory color of the button
     * @param noteSound int: the note of the sound. Like : do, re, mi, fa, sol, ...
     */
    private void clickOnButtonColor(Button button, Color colorCheck, String colorToChange, String colorReset, int noteSound) {
        button.setOnAction(e -> {
            changeColorAndSound(button, colorToChange, colorReset, noteSound);
            controller.click(colorCheck);
        });
    }

    /**
     * Change the color and make a sound for a specify button with a Duration
     * Play the sequence and after put back the old color to the button
     * @param button Button: the button to put the action
     * @param colorToChange String: the color to change on the button
     * @param colorReset String: the factory color of the button
     * @param noteSound int: the note of the sound. Like : do, re, mi, fa, sol, ...
     */
    private void changeColorAndSound(Button button, String colorToChange, String colorReset, int noteSound) {
        var pause = new PauseTransition(Duration.millis(500));
        pause.setOnFinished(event -> {
            try {
                makeSound(noteSound);
            } catch (MidiUnavailableException ex) {
                ex.printStackTrace();
            }
            button.setBackground(new Background(new BackgroundFill(Color.web(colorReset), CornerRadii.EMPTY, Insets.EMPTY)));
        });

        pause.play();
        button.setBackground(new Background(new BackgroundFill(Color.web(colorToChange), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    /**
     * If the silent mode is active, it would make some sound
     * Else it will make a sound with the specific value
     * @param noteSound int: the note of the sound. Like : do, re, mi, fa, sol, ...
     * @throws MidiUnavailableException : Exception if the synth have a trouble
     */
    private void makeSound(int noteSound) throws MidiUnavailableException{ 
        sound.makeSound(noteSound, mst.isNotSilentMode());
    }

    /**
     * Play a sequence of color in the list
     * Check what is the different color in the list and change the color and make sound for the specify color choose
     * Launch a timer for put the good color in a given time
     * Continue the sequence such as the cycle is finish and put the speed of the sequence at the slider value
     * @param listColorSequence List: list of a random color sequence
     */
    private void playSequence(List<Color> listColorSequence) {
        final int currentIndexColor[] = {0};
        
        var timeline = new Timeline();
        KeyFrame colorButton = new KeyFrame(Duration.millis(800),
                (e) -> {
                    var colorSequence = listColorSequence.get(currentIndexColor[0]);
                    if(colorSequence==Color.GREEN){
                    changeColorAndSound(btnTopLeft, btnTopLeftColorChange, btnTopLeftColorReset, 69);
                    }else if(colorSequence==Color.RED){
                        changeColorAndSound(btnTopRight, btnTopRightColorChange, btnTopRightColorReset, 71);
                    }else if(colorSequence==Color.YELLOW){
                        changeColorAndSound(btnBottomLeft, btnBottomLeftColorChange, btnBottomLeftColorReset, 72);
                    }else if(colorSequence==Color.BLUE){
                        changeColorAndSound(btnBottomRight, btnBottomRightColorChange, btnBottomRightColorReset, 74);
                    }
                    ++currentIndexColor[0];
                    if(currentIndexColor[0]==listColorSequence.size()){
                        controller.launchTimer();
                    }
                }
        );
        timeline.getKeyFrames().add(colorButton);
        timeline.setCycleCount(listColorSequence.size());
        timeline.setRate(mst.getSliderValue());
        timeline.play();
    }
    
    /**
     * Display with a alert popup the error with a title, header and message content
     * @param title String: the title of the popup
     * @param headerText String : the message in the header of the popup
     * @param contextText String : context message error that append
     */
    private void displayError(String title, String headerText, String contextText){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
	alert.setHeaderText(headerText);
	alert.setContentText(contextText);
        alert.showAndWait();
    }

    
    /**
     * Get an event from the Observer class that he watch
     * Check which button or action has been give
     * And ask to the controller or display something depending the event
     * @param evt PropertyChangeEvent: the notification that append if something have change
     */
    public void propertyChange(PropertyChangeEvent evt) {
        String eventChange = evt.getPropertyName();
        switch (eventChange) {
            case MenuStart.PROPERTY_BUTTON_START:
                controller.start();
                break;
            case MenuStart.PROPERTY_BUTTON_LONGEST:
                controller.longest();
                break;
            case MenuStart.PROPERTY_BUTTON_LAST:
                controller.last();
                break;
            case Game.PROPERTY_START:
                playSequence((List<Color>) evt.getNewValue());
                break;
            case Game.PROPERTY_ERROR_COLOR:
                displayError("Mauvaise couleur :/", "La couleur rentré n'est pas la bonne", "Si vous souhaitez rejouez cliqué sur le bouton start :D");
                break;
            case Game.PROPERTY_TIME_IS_OVER:
                displayError("Temps écoulé", "Le temps est écoulé", "Si vous souhaitez rejouez cliqué sur start");
                break;
            default:
                throw new AssertionError();
        }
    }

}
