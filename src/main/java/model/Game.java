/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.scene.paint.Color;

/**
 *
 * @author basile
 */
public class Game implements Model {

    private PropertyChangeSupport pcs;
    private List randomColor;
    private List longestSequenceColor;
    private List lastSequenceColor;
    private List currentPickColor;
    private boolean starting;
    private Timer timer;
    public final static String PROPERTY_START="model.Game.start";
    public final static String PROPERTY_TIME_IS_OVER="model.Game.timeIsOver";
    public final static String PROPERTY_ERROR_COLOR="model.Game.errorColor";
    

    public Game() {
        pcs = new PropertyChangeSupport(this);
        randomColor = new ArrayList<Color>();
        longestSequenceColor = new ArrayList<Color>();
        lastSequenceColor = new ArrayList<Color>();
        starting = false;
    }

    @Override
    public void start() {
        if (!starting) {
            startGame();
        }
    }
    
    private void startGame() {
        currentPickColor = new ArrayList();
        starting = true;
        Random rand = new Random();
        int currentSizeColor = randomColor.size();
        List colorList = allColorList();
        int sizeColorList = colorList.size();
        int tmpSizeListColor = currentSizeColor + 1;
        for (var i = currentSizeColor; i < tmpSizeListColor; i++) {
            int tmpRand = rand.nextInt(sizeColorList) + 0;
            randomColor.add(colorList.get(tmpRand));
        }
        change(PROPERTY_START, randomColor);
    }

    @Override
    public void longestGame() {
        if (!starting && !longestSequenceColor.isEmpty()) {
            if (randomColor.isEmpty()) {
                deepCloningList(longestSequenceColor, randomColor);
                starting = true;
                change(PROPERTY_START, randomColor);
            }
        }
    }

    @Override
    public void lastGame() {
        if (!starting && !lastSequenceColor.isEmpty()) {
            if (randomColor.isEmpty()) {
                deepCloningList(lastSequenceColor, randomColor);
                starting = true;
                change(PROPERTY_START, randomColor);
            }
        }
    }

    @Override
    public void launchTimer() {
        var timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (starting) {
                        resetGame();
                        change(PROPERTY_TIME_IS_OVER, "");
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 7000);
    }

    private List allColorList() {
        List colorList = new ArrayList<Color>();
        colorList.add(Color.GREEN);
        colorList.add(Color.RED);
        colorList.add(Color.YELLOW);
        colorList.add(Color.BLUE);
        return colorList;
    }

    @Override
    public void checkColorChoice(Color color) {
        if (starting) {
            timer.cancel();
            if (isChoiceColorCorrect(color)) {
                currentPickColor.add(color);
                launchTimer();
                isSequenceFinish();
            } else {
                saveInfoListColor();
                resetGame();
                change(PROPERTY_ERROR_COLOR, "");
            }
        }
    }

    private void saveInfoListColor() {
        if (randomColor.size() > longestSequenceColor.size()) {
            longestSequenceColor.clear();
            deepCloningList(randomColor, longestSequenceColor);
        }
        lastSequenceColor.clear();
        deepCloningList(randomColor, lastSequenceColor);
    }

    private void deepCloningList(List ListToClone, List listToAdd) {
        for (var color : ListToClone) {
            listToAdd.add(color);
        }
    }

    private void resetGame() {
        randomColor = new ArrayList<>();
        currentPickColor = new ArrayList();
        starting = false;
    }

    private void isSequenceFinish() {
        if (currentPickColor.size() == randomColor.size()) {
            starting = false;
            startGame();
            timer.cancel();
        }
    }

    private boolean isChoiceColorCorrect(Color color) {
        int currentColor = currentPickColor.size();
        if (randomColor.get(currentColor) != color) {
            return false;
        }
        return true;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    @Override
    public void change(String message, Object objectSend) {
        pcs.firePropertyChange(message, null, objectSend);
    }

}
