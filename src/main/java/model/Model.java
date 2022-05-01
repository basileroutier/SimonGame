/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model;

import java.beans.PropertyChangeListener;
import java.util.List;
import javafx.scene.paint.Color;

/**
 *
 * @author basile
 */
public interface Model {

    /**
     * check if the game is already starting if not, It will launch the game by
     * create a list of random color And initialize some attribut Notify all the
     * observable by a change
     */
    public void start();

    /**
     * check if the game is already starting and if their is at least one game
     * in the history, It will give at first the longest game that a player made
     * After this It will call the start method to start at a specify color list
     */
    public void longestGame();

    /**
     * check if the game is already starting and if their is at least one game
     * in the history, It will give at first the last game that a player made
     * After this It will call the start method to start at a specify color list
     */
    public void lastGame();

    /**
     * If the game is starting Check from a choice, if the color is correct.If
     * it is, it will check if the sequence is finish And Cancel the timer and
     * reload it Else it will call a method that check if it can save the
     * current game information into longest and last list Reset the game and
     * notify the Observable
     *
     * @param color Color: color to check if it is correct or not
     */
    public void checkColorChoice(Color color);

    /**
     * Re/Launch the timer 
     * And check if the running time is ending if it is finish
     * If not, it will reset the game and notify the observable
     */
    public void launchTimer();

    /**
     * Add a listenner to the list
     * @param listener PropertyChangeListener: listener that can listen the modification if it is append
     */
    public void addPropertyChangeListener(PropertyChangeListener listener);

    /**
     * Remove a listener to the list
     * @param listener PropertyChangeListener: listener that couldn't more listen modificaiton
     */
    public void removePropertyChangeListener(PropertyChangeListener listener);

    /**
     * Notify all the Listener from a modification in the Model
     * @param message String: the message of the modification
     * @param objectSend Object: the object to send if their is a modification
     */
    public void change(String message, Object objectSend);
}
