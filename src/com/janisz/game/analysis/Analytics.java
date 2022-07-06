package com.janisz.game.analysis;

import com.janisz.game.BallsEnum;
import com.janisz.game.actors.Player;
import com.janisz.game.actors.Table;

import java.util.*;

public class Analytics {
    
    private static Player player1s;
    private static Player player2s;
    private static Table tables;
    private static BallsEnum bs;
    
    private static int tempValue;
    private static BallsEnum pingPongBall;
    private static int pointsToWin;
    private static PlayerFuture playerFuture;

    private Analytics(){
        throw new AssertionError();
    }
    public static PlayerFuture getPLayerFuture(Player player1, Player player2, Table table, BallsEnum b){
        
        player1s = player1;
        player2s = player2;
        tables = table;
        bs = b;
        
        pointsToWin = getPointsToWin();
        playerFuture = new PlayerFuture();
        
        playerFuture.setPointsToWin(pointsToWin);
        pingPongBall = getPingPongBall();
    
        
        
        
        getUndistributedPoints();
        
        List<BallsEnum> reversed = new ArrayList<>(Arrays.asList(BallsEnum.values()));
        
        reversed.remove(BallsEnum.RED);
        reversed.remove(BallsEnum.WHITE);
        Collections.reverse(reversed);
        
        if(tempValue<pointsToWin){
            int index = 0;
            while(tempValue<pointsToWin&&index<reversed.size()){
                pingPongBall = reversed.get(index);
                playerFuture.addToColorBalls(pingPongBall);
                tempValue+=pingPongBall.value;
                index++;
            }
        }
        
        playerFuture.setEnough(tempValue >= pointsToWin);
        playerFuture.setFutureBallsSetValue(tempValue);
        playerFuture.setSnookersNeeded(getSnookersNeeded(playerFuture));
        return playerFuture;
    }
    private static void getUndistributedPoints(){
        Map<BallsEnum,Integer> ballsOnTable = tables.getBalls();
        int numberOfRedRemaining = ballsOnTable.get(BallsEnum.RED);
        while(numberOfRedRemaining>0&&tempValue<pointsToWin){
        
            playerFuture.addToPairedBalls(pingPongBall);
            tempValue+=pingPongBall.value;
        
            if(pingPongBall==bs){
                pingPongBall=BallsEnum.RED;
            }else{
                pingPongBall=bs;
                numberOfRedRemaining--;
            }
        }
    }
    private static int getPointsToWin(){
    
        int pointsOnTable = tables.getValueOfBallsPresent();
        int player1Score = player1s.getFrameScore();
        int player2Score = player2s.getFrameScore();
        
    
        int scoreToWin = (player1Score+player2Score+pointsOnTable)/2;
        int pointsToWin= scoreToWin-player1Score+1;
        if(player2Score<scoreToWin){
            return pointsToWin;
        }else{
            return (player2Score-player1Score)+1;
        }
        
    }
    private static boolean isStartingRed(){
        List<BallsEnum> playerBallHistory = player1s.getBallsPutFrame();
        
        if(playerBallHistory.isEmpty()){
            return true;
        }
        else{
            return playerBallHistory.get(playerBallHistory.size()-1)!=BallsEnum.RED;
        }
        
    }
    private static BallsEnum getPingPongBall(){
        if(isStartingRed()||!player1s.isActive()){
            return   BallsEnum.RED;
        }
        else{
            return   bs;
        }
    }
    
    private static int getSnookersNeeded(PlayerFuture playerFuture){
        if(playerFuture.isEnough()){
            return 0;
        }
        else{
            return  playerFuture.getPointsToWin()- playerFuture.getFutureBallsSetValue();
        }
    }

}
