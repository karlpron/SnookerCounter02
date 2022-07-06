package com.janisz.game;

import javafx.scene.paint.Color;

public enum BallsEnum {
    RED ("red",1,15,false,Color.RED),
    BLACK("black",7,1,true,Color.BLACK),
    PINK("pink",6,1,true,Color.DEEPPINK),
    BLUE("blue",5,1,true,Color.BLUE),
    BROWN("brown",4,1,true,Color.BROWN),
    GREEN("green",3,1,true,Color.GREEN),
    YELLOW("yellow",2,1,true,Color.YELLOW),
    WHITE("white",4,1,true,Color.WHITE);
    
    public final String name;
    public final int value;
    public final int number;
    public final boolean returning;
    public final Color color;
    
    BallsEnum(String name, int value, int number, boolean returning, Color color){
        this.name = name;
        this.value = value;
        this.number = number;
        this.returning = returning;
        this.color =color;
    }
    
    
    
}
