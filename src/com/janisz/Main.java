package com.janisz;

import com.janisz.gui.GUIManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application {
    
    
    public void init(){
        System.out.println("Initializing firing sequence...");
        
    }
    
    @Override
    public void start(Stage stage) throws Exception {
    

        GUIManager guiManager = new GUIManager();
        
        Scene scene = guiManager.getScene();
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
