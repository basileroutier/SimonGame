/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Class that have the menu to launch a game and control the different settings
 * @author basile
 */
public class MenuStart{
    
    public final static String PROPERTY_BUTTON_START="view.MenuStart.buttonStart";
    public final static String PROPERTY_BUTTON_LONGEST="view.MenuStart.buttonLongest";
    public final static String PROPERTY_BUTTON_LAST="view.MenuStart.buttonLast";
    private PropertyChangeSupport pcs;
    private Button btnStart = new Button("Start");
    private Button btnLast = new Button("Last");
    private Button btnLongest = new Button("Longest");
    private Slider slider = new Slider(0, 100, 0);
    private CheckBox checkBoxSilentMode;
     

    /**
     * Controller that initialize the Observer class
     */
    public MenuStart() {
        pcs = new PropertyChangeSupport(this);
    }

    /**
     * Return the menu that contain all the setting and can launch a game
     * @return a borderPane menu that contain all the setting of the game and the controller
     */
    public BorderPane getMenuStart() {
        
        BorderPane center = new BorderPane();
        center.setMaxWidth(270);
        center.setMaxHeight(270);
        center.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        VBox settingGameTop = new VBox();

        Label lblTitle = new Label("simon");
        lblTitle.setFont(Font.font("System", FontWeight.BOLD, 35));
        Label sliderTile = new Label("Speed");
        sliderTile.setFont(Font.font("System", FontWeight.BOLD, 16));
        sliderTile.setTextFill(Color.web("#444d52"));

        slider.setValue(50);
        slider.setShowTickLabels(false);
        slider.setShowTickMarks(true);
        slider.setBlockIncrement(10);
        slider.setMaxWidth(150);
        slider.setMaxHeight(60);
        settingGameTop.getChildren().addAll(lblTitle, slider, sliderTile);
        settingGameTop.setAlignment(Pos.TOP_CENTER);
        VBox.setMargin(lblTitle, new Insets(15, 0, 3, 0));
        center.setTop(settingGameTop);

        HBox playSettingCenter = new HBox(15);
        Font font = Font.font("System", FontWeight.BOLD, 16);
        

        

        btnLongest.setTextFill(Color.WHITE);
        btnStart.setTextFill(Color.WHITE);
        btnLast.setTextFill(Color.WHITE);

        btnLongest.setFont(font);
        btnStart.setFont(font);
        btnLast.setFont(font);

        btnLongest.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        btnStart.setBackground(new Background(new BackgroundFill(Color.PURPLE, CornerRadii.EMPTY, Insets.EMPTY)));
        btnLast.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));

        btnLongest.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
        btnStart.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
        btnLast.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
        btnLongest.setShape(new Ellipse(150.0f, 120.0f, 130.0f, 100.0f));

        int maxSize = 81;
        int minSize = 70;

        btnLongest.setMinSize(maxSize, minSize);
        btnLongest.setMaxSize(maxSize, minSize);
        btnLast.setShape(new Ellipse(150.0f, 120.0f, 130.0f, 100.0f));
        btnLast.setMinSize(maxSize, minSize);
        btnLast.setMaxSize(maxSize, minSize);
        int r = 33;
        btnStart.setShape(new Circle(r));
        btnStart.setMinSize(2 * r, 2 * r);
        btnStart.setMaxSize(2 * r, 2 * r);

        BorderPane.setMargin(playSettingCenter, new Insets(23, 0, 0, 0));
        playSettingCenter.getChildren().addAll(btnLongest, btnStart, btnLast);
        playSettingCenter.setAlignment(Pos.TOP_CENTER);
        center.setCenter(playSettingCenter);

        VBox settingGameBottom = new VBox();
        Label lblInfo = new Label("Info");
        lblInfo.setFont(Font.font("System", FontWeight.LIGHT, 13));
        lblInfo.setTextFill(Color.web("#818182"));
        checkBoxSilentMode = new CheckBox("Silent Mode");
        checkBoxSilentMode.setAllowIndeterminate(true);

        VBox.setMargin(lblInfo, new Insets(0, 0, 5, 0));
        BorderPane.setMargin(settingGameBottom, new Insets(0, 0, 3, 0));
        settingGameBottom.getChildren().addAll(lblInfo, checkBoxSilentMode);
        settingGameBottom.setAlignment(Pos.TOP_CENTER);
        center.setBottom(settingGameBottom);
        isStartButtonClicked();
        isLongestButtonClicked();
        isLastButtonClicked();
        return center;
    }
    
    /**
     * Return if the game is in silent mode or not
     * Return true if not, else false
     * @return if the game is in silent mode or not
     */
    public boolean isNotSilentMode(){
        return !checkBoxSilentMode.isSelected();
    }

    /**
     * return the slider value
     * @return the slider value
     */
    public double getSliderValue() {
        
        double valueSlider = slider.getValue() / 100;
        if(valueSlider<0.01){
            return 0.01;
        }
        return valueSlider;
    }

    /**
     * Set action on the start button and notify all the observable if it is click
     */
    private void isStartButtonClicked() {
        btnStart.setOnAction(e -> change(PROPERTY_BUTTON_START));
    }
    
    /**
     * Set action on the longest button and notify all the observable if it is click
     */
    private void isLongestButtonClicked() {
        btnLongest.setOnAction(e -> change(PROPERTY_BUTTON_LONGEST));
    }
    
    /**
     * Set action on the last button and notify all the observable if it is click
     */
    private void isLastButtonClicked() {
        btnLast.setOnAction(e -> change(PROPERTY_BUTTON_LAST));
    }
    
    /**
     * Add an observable in the PropertyChange to Observe the current class
     * @param listener PropertyChangeListener: the Listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    /**
     * Remove an observable in the PropertyChange to Observe the current class
     * @param listener PropertyChangeListener: the Listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    
    /**
     * Notify all the class that Observe the current class from a modification
     * @param listener PropertyChangeListener: the Listener
     */
    public void change(String buttonClick) {
        pcs.firePropertyChange(buttonClick, null, true);
    }
}
