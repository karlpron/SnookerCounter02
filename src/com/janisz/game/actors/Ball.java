package com.janisz.game.actors;

public class Ball {
    private final int value;
    private final String name;
    private Position position;
    
    public Ball(String name,int value){
        this.name = name;
        this.value =value;
        position = new Position();
    }
    
    public String getName() {
        return name;
    }
    
    public int getValue() {
        return value;
    }
    public Position getPosition() {
        return position.copy();
    }
    public void changePosition(int xPosition,int yPosition){
        this.position.setXPosition(xPosition);
        this.position.setyPosition(yPosition);
    }
    
}
