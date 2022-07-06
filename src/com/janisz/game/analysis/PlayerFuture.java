package com.janisz.game.analysis;

import com.janisz.game.BallsEnum;

import java.util.ArrayList;

public class PlayerFuture {
    
    private final ArrayList<BallsEnum> pairedBalls;
    private final ArrayList<BallsEnum> colorBalls;
    private int pointsToWin;
    private int futureBallsSetValue;
    private int snookersNeeded;
    private boolean enough;
    
    public PlayerFuture(){
        pairedBalls = new ArrayList<>();
        colorBalls = new ArrayList<>();
    }
    private PlayerFuture(PlayerFuture playerFuture){
        this.pairedBalls = new ArrayList<>(playerFuture.pairedBalls);
        this.colorBalls = new ArrayList<>(playerFuture.colorBalls);
        this.pointsToWin = playerFuture.pointsToWin;
        this.futureBallsSetValue = playerFuture.futureBallsSetValue;
        this.snookersNeeded = playerFuture.snookersNeeded;
        this.enough = playerFuture.enough;
        
    }
    public PlayerFuture copy(){
        return new PlayerFuture(this);
    }
    
    public void addToPairedBalls(BallsEnum b){
        pairedBalls.add(b);
    }
    public void addToColorBalls(BallsEnum b){
        colorBalls.add(b);
    }
    public boolean isPairedEmpty(){
        return pairedBalls.isEmpty();
    }
    public boolean isColorEmpty(){
        return colorBalls.isEmpty();
    }
    public ArrayList<BallsEnum> getColoredBalls(){
        return new ArrayList<>(colorBalls);
    }
    public ArrayList<BallsEnum> getPairedBalls(){
        return new ArrayList<>(pairedBalls);
    }
    
    public void setEnough(boolean enough) {
        this.enough = enough;
    }
    public boolean isEnough() {
        return enough;
    }
    
    public void setPointsToWin(int pointsToWin) {
        this.pointsToWin = pointsToWin;
    }
    
    public int getPointsToWin() {
        return pointsToWin;
    }
    
    public void setFutureBallsSetValue(int futureBallsSetValue) {
        this.futureBallsSetValue = futureBallsSetValue;
    }
    
    public int getFutureBallsSetValue() {
        return futureBallsSetValue;
    }
    
    public void setSnookersNeeded(int snookersNeeded) {
        this.snookersNeeded = snookersNeeded;
    }
    
    public int getSnookersNeeded() {
        return snookersNeeded;
    }
}
