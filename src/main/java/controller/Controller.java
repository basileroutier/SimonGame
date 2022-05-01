/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.beans.PropertyChangeListener;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.sound.midi.MidiUnavailableException;
import model.Model;
import view.View;

/**
 * Controller that initiliaze all the attribut and make the action if someone asking him
 * @author basile
 */
public class Controller {

    Model model;
    View view;
    
    /**
     * Basic controller that initialize the model, the view
     * And call the view to show the stage
     * @param model Model: the model of the game
     * @param primaryStage Stage: the stage which is used by the View
     * @throws javax.sound.midi.MidiUnavailableException Exception : for sound
     */
    public Controller(Model model, Stage primaryStage) throws MidiUnavailableException{
        this.model = model;
        this.view = new View(this, model);
        this.view.start(primaryStage);
        this.view.checkColorClick();
    }
    
    /**
     * Start a game by giving random color
     */
    public void start(){
        model.start();
    }
    
    /**
     * Save a click and check if the color is correct
     * @param color Color: the color that will be check
     */
    public void click(Color color){
        model.checkColorChoice(color);
    }
    
    /**
     * Start a game by giving the longest game color list
     */
    public void longest(){
        model.longestGame();
    }
    
    /**
     * Start a game by giving the last game color list
     */
    public void last(){
        model.lastGame();
    }
    
    /**
     * Launch the timer of the model
     */
    public void launchTimer(){
        model.launchTimer();
    }
    
}
