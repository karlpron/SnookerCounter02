package com.janisz.gui;

import com.janisz.game.BallsEnum;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.List;


public class GUIPlayerPanel {
    private VBox mainVBox;
    private final GUIManager guiManager;
    private final int playerId;
    private Label nameLabel;
    private Label frameScoreLabel;
    private Label breakScoreLabel;
    //private Label futureLabel;
    private Label pointsNeededLabel;
    
    private HBox ballShotHBox;
    private HBox ballFaulHBox;
    private HBox futureHBox;
    private HBox frameHistoryHBox;
    private HBox breakHistoryHBox;
    
    private final String frameScoreDesc = "frame score: ";
    private final String breakScoreDesc = "break score: ";
    private final String futureDesc = "points left on the table/ score needed to win";
    private final String snookersNeededDesc = "Snookers needed: ";
    private final String frameHistoryString = "Frame history: ";
    private final String breakHistoryString = "Break history:";
    
    public GUIPlayerPanel(GUIManager guiManager,int playerId){
        
        this.guiManager = guiManager;
        this.playerId = playerId;
        createMainVbox();
    }
    
    
    
    private void createMainVbox(){
        mainVBox = new VBox(10);
        mainVBox.setAlignment(Pos.CENTER);
        nameLabel = new Label(guiManager.gameManager.getPlayerName(playerId));
        frameScoreLabel = new Label(frameScoreDesc+guiManager.gameManager.getPLayerFrameScore(playerId));
        breakScoreLabel = new Label(breakScoreDesc+guiManager.gameManager.getPlayerBreakScore(playerId));
        
        createBallShotHBox();
        createBallFaulHBox();
        createFutureHBox();
        createFrameHistoryHBox();
        createBreakHistoryHBox();
        mainVBox.getChildren().addAll(nameLabel,frameScoreLabel,frameHistoryHBox,breakScoreLabel,breakHistoryHBox,ballShotHBox,ballFaulHBox,futureHBox);
    }
    private void createBallShotHBox(){
    
        this.ballShotHBox = new HBox(10);
        ballShotHBox.setAlignment(Pos.CENTER);
        double radius = 30;
        for(BallsEnum b: BallsEnum.values()){
            Circle circle = createBall(b,radius);
            circle.setOnMouseClicked(mouseEvent -> {
                //guiManager.gameManager.putBall(b,playerId);
                guiManager.gameManager.changeState(b,playerId,guiManager.gameManager::putBall,BallsEnum.BLACK);
                guiManager.refresh();
            });
            if(b==BallsEnum.WHITE){
                circle.setStroke(Color.BLACK);
            }
            ballShotHBox.getChildren().add(circle);
        }
    }
    private void createBallFaulHBox(){
        
        this.ballFaulHBox = new HBox(10);
        ballFaulHBox.setAlignment(Pos.CENTER);
        double radius = 20;
        for(BallsEnum b: BallsEnum.values()){
            Circle circle = createBall(b,radius);
            circle.setOnMouseClicked(mouseEvent -> {
                //guiManager.gameManager.faul(b,playerId);
                guiManager.gameManager.changeState(b,playerId,guiManager.gameManager::faul,BallsEnum.BLACK);
                guiManager.refresh();
            });
            if(b==BallsEnum.WHITE){
                circle.setStroke(Color.BLACK);
            }
            ballFaulHBox.getChildren().add(circle);
        }
    }
    private void createFutureHBox(){
    
        this.futureHBox = new HBox(10);
        futureHBox.setAlignment(Pos.CENTER_LEFT);
        //futureLabel = new Label(futureDesc+": "+guiManager.gameManager.getBallsSet(playerId).getFutureBallsSetValue()+" / "+ guiManager.gameManager.getBallsSet(playerId).getPointsToWin());
        pointsNeededLabel = new Label("proper?"+futureDesc+":"+guiManager.gameManager.getTableValue()+" / "+ (guiManager.gameManager.getPlayerFuture(playerId).getPointsToWin()+guiManager.gameManager.getPLayerFrameScore(playerId)));
        VBox tempVbox = new VBox(10);
        //tempVbox.getChildren().addAll(futureLabel,pointsNeededLabel);
        futureHBox.getChildren().add(pointsNeededLabel);
        
        
    }
    private void createFrameHistoryHBox(){
        this.frameHistoryHBox= new HBox(10);
        frameHistoryHBox.setAlignment(Pos.CENTER_LEFT);
        frameHistoryHBox.getChildren().add(new Label(frameHistoryString));
        List<BallsEnum> temp = guiManager.gameManager.getPlayerFrameHistory(playerId);
        HBox tempHBox = new HBox(10);
        for(BallsEnum b:temp){
            Circle circle = createBall(b,10);
            tempHBox.getChildren().add(circle);
        }
        frameHistoryHBox.getChildren().add(tempHBox);
    }
    private void refreshFrameHistory(){
        List<BallsEnum> temp = guiManager.gameManager.getPlayerFrameHistory(playerId);
        if(frameHistoryHBox.getChildren().get(1) instanceof HBox){
            ((HBox) frameHistoryHBox.getChildren().get(1)).getChildren().clear();
            for(BallsEnum b:temp){
                ((HBox) frameHistoryHBox.getChildren().get(1)).getChildren().add(createBall(b,10));
            }
   
        }
        
    }
    
    private void createBreakHistoryHBox(){
        this.breakHistoryHBox = new HBox(10);
        breakHistoryHBox.setAlignment(Pos.CENTER_LEFT);
        breakHistoryHBox.getChildren().add(new Label(breakHistoryString));
        List<BallsEnum> temp = guiManager.gameManager.getPlayerBreakHistory(playerId);
        HBox tempHBox = new HBox(10);
        for(BallsEnum b:temp){
            Circle circle = createBall(b,10);
            tempHBox.getChildren().add(circle);
        }
        breakHistoryHBox.getChildren().add(tempHBox);
    }
    private void refreshBreakHistory(){
        List<BallsEnum> temp = guiManager.gameManager.getPlayerBreakHistory(playerId);

        if(breakHistoryHBox.getChildren().get(1) instanceof HBox){
            ((HBox) breakHistoryHBox.getChildren().get(1)).getChildren().clear();
            for(BallsEnum b:temp){
                ((HBox) breakHistoryHBox.getChildren().get(1)).getChildren().add(createBall(b,10));
            }
            
    
            
        } else if (temp.isEmpty()) {
            ((HBox) breakHistoryHBox.getChildren().get(1)).getChildren().clear();
        }
    
    }
    
    
    
    private Circle createBall(BallsEnum b, double radius){
        Circle circle = new Circle();
        circle.setFill(b.color);
        circle.setRadius(radius);
        return circle;
    }
    
    
    public void refresh(){
        nameLabel.setText(guiManager.gameManager.getPlayerName(playerId));
        frameScoreLabel.setText(frameScoreDesc+guiManager.gameManager.getPLayerFrameScore(playerId));
        breakScoreLabel.setText(breakScoreDesc+guiManager.gameManager.getPlayerBreakScore(playerId));
        //futureLabel.setText(futureDesc+": "+guiManager.gameManager.getBallsSet(playerId).getFutureBallsSetValue()+" / "+ guiManager.gameManager.getBallsSet(playerId).getPointsToWin());
        pointsNeededLabel.setText(futureDesc+":"+guiManager.gameManager.getTableValue()+" / "+ (guiManager.gameManager.getPlayerFuture(playerId).getPointsToWin()+guiManager.gameManager.getPLayerFrameScore(playerId)));
        refreshFrameHistory();
        refreshBreakHistory();
    }
    public void reset(){
        if(frameHistoryHBox.getChildren().get(1) instanceof HBox){
            ((HBox) frameHistoryHBox.getChildren().get(1)).getChildren().clear();
        }
    }
    
    public VBox getMainVBox(){
        return mainVBox;
    }
    
}
