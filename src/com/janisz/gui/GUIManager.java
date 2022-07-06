package com.janisz.gui;

import com.janisz.game.GameManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


public class GUIManager {
    
    private final Scene scene;
    protected GameManager gameManager;
    private VBox mainVBox;
    private final int[] playerIds;
    private final GUIPlayerPanel[] playerPanels;
    private HBox playerPanelsHBox;
    private VBox lowerVbox;
    private VBox upperVbox;
    
    private double sceneWidth = 1200;
    
    private final String resetString = "Reset";
    
    public GUIManager(){
        gameManager = new GameManager();
        playerIds = gameManager.getPlayersIds();
        playerPanels = new GUIPlayerPanel[playerIds.length];
        makeMainVBox();
        scene = new Scene(mainVBox,sceneWidth,600);
    }
    private void makePlayerPanels(){
        for(int i = 0;i<playerIds.length;i++){
            playerPanels[i]=new GUIPlayerPanel(this,playerIds[i]);
        }
    }
    private void makePLayerPanelsHBox(){
        playerPanelsHBox = new HBox(10);
        playerPanelsHBox.setAlignment(Pos.CENTER);
        makePlayerPanels();
        for (GUIPlayerPanel guiPlayerPanel: playerPanels){
            playerPanelsHBox.getChildren().add(guiPlayerPanel.getMainVBox());
        }
    }
    private void makeLowerVbox(){
        lowerVbox = new VBox(10);
        Button resetButton = new Button(resetString);
        //quick'n'dirty
        resetButton.setOnAction(actionEvent -> {
            gameManager = new GameManager();
            for(GUIPlayerPanel guiPlayerPanel:playerPanels){
                guiPlayerPanel.reset();
            }
            refresh();
        });
        lowerVbox.setAlignment(Pos.CENTER);
        lowerVbox.getChildren().add(resetButton);
    }
    private void makeUpperVbox(){
        Label undoLabel = new Label("UNDO");
        Label redoLabel = new Label("REDO");
        Shape leftArrow = makeLeftArrow();
        Shape rightArrow = makeRightArrow();
        
        undoLabel.setOnMouseClicked(mouseEvent -> {
            undo();
        });
        redoLabel.setOnMouseClicked(mouseEvent -> {
            undo();
        });
        
        leftArrow.setOnMouseClicked(mouseEvent -> {
          undo();
        });
        
        rightArrow.setOnMouseClicked(mouseEvent -> {
            redo();
        });
        upperVbox = new VBox(15);
        upperVbox.setAlignment(Pos.TOP_CENTER);
        HBox historyHBox = new HBox(sceneWidth-250);
        VBox leftVbox = new VBox(10);
        VBox rightVbox = new VBox(10);
        
        historyHBox.setAlignment(Pos.TOP_CENTER);

        
        leftVbox.getChildren().addAll(leftArrow,undoLabel);
        rightVbox.getChildren().addAll(rightArrow,redoLabel);
        
        historyHBox.getChildren().addAll(leftVbox,rightVbox);
        
        
 
        upperVbox.getChildren().add(historyHBox);
    }
    
    private void undo(){
        gameManager.undoLastChange();
        refresh();
    }
    private void redo(){
        gameManager.redoLastChange();
        refresh();
    }
    
    private Shape makeLeftArrow(){
        return makeArrow();
    }
    private Shape makeRightArrow(){
        Shape shape = makeArrow();
        
        shape.setRotate(180);
        return  shape;
    }
    private Shape makeArrow(){
        Polygon triangle = new Polygon(100.0,100.0,
                150.0,75.0,
                150.0,125.0);
        triangle.setFill(Color.GREEN);
    
        Rectangle rectangle = new Rectangle();
        rectangle.setX(150);
        rectangle.setY(90);
        rectangle.setWidth(50);
        rectangle.setHeight(20);
        rectangle.setFill(Color.GREEN);
        
        Shape shape = Shape.union(triangle,rectangle);
        shape.setFill(Color.GREEN);
        return shape;
    }
    
    
    private void makeMainVBox(){
        mainVBox = new VBox(10);
        mainVBox.setAlignment(Pos.TOP_CENTER);
        makeUpperVbox();
        makePLayerPanelsHBox();
        makeLowerVbox();
        mainVBox.getChildren().addAll(upperVbox,playerPanelsHBox,lowerVbox);
    }
    public void refresh(){
        
        for(GUIPlayerPanel guiPlayerPanel:playerPanels){
            guiPlayerPanel.refresh();
        }
    }
    
    public Scene getScene(){
        return scene;
    }
}
