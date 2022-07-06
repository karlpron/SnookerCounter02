package com.janisz.game.actors;

public class Position {
    
    private int xPosition;
    private int yPosition;
    
    public Position(){
        this(0,0);
    }
    public Position(int xPosition,int yPosition){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
    private Position(Position position){
        this.xPosition = position.xPosition;
        this.yPosition = position.yPosition;
    }
    //czy position ma byc zawarte w ball, czy w table?
    //
    public Position copy(){
        return new Position(this);
    }
    
    public int getxPosition() {
        return xPosition;
    }
    
    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }
    
    public int getYPosition() {
        return yPosition;
    }
    
    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }
}
