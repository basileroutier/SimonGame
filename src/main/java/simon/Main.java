/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simon;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Game;

/**
 * The main class that launch all the application
 * @author basile
 */
public class Main extends Application{
    
    
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        var model = new Game();
        var controller = new Controller(model, stage);
    }
}
