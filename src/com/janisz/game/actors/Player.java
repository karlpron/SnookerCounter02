package com.janisz.game.actors;

import com.janisz.game.Archivisable;
import com.janisz.game.BallsEnum;

import java.util.ArrayList;
import java.util.List;


public final class Player implements Archivisable {
    private final String name;
    private final int frameScore;
    private final int breakScore;
    private final ArrayList<BallsEnum> ballsPutFrame;
    private final ArrayList<BallsEnum> ballsPutBreak;
    private final boolean active;
    
    public Player(){
        this("Kiszka.player");
    }
    public Player(String name){
        this(name,0,0);
    }

    public Player(String name,int frameScore,int breakScore){

        this(name,frameScore,breakScore,new ArrayList<>(),new ArrayList<>(),false);
    }
    private Player(String name, int frameScore, int breakScore, List<BallsEnum> ballsPutFrame,List<BallsEnum> ballsPutBreak, boolean active){
        this.name = name;
        this.frameScore = frameScore;
        this.breakScore = breakScore;
        this.ballsPutFrame = new ArrayList<>(ballsPutFrame);
        this.ballsPutBreak = new ArrayList<>(ballsPutBreak);
        this.active = active;
        
    }
    
    private Player(Player player){
        this.name = player.name;
        this.frameScore = player.frameScore;
        this.breakScore = player.breakScore;
        this.ballsPutFrame = player.ballsPutFrame;
        this.ballsPutBreak = player.ballsPutBreak;
        this.active = player.active;
    }
    public Player copy(){
        return new Player(this);
    }
    
    public int getFrameScore(){
        return frameScore;
    }
    public int getBreakScore(){
        return breakScore;
    }

    public String getName(){
        return name;
    }
    public ArrayList<BallsEnum> getBallsPutFrame(){
        return new ArrayList<>(ballsPutFrame);
    }
    public ArrayList<BallsEnum> getBallsPutBreak(){
        return new ArrayList<>(ballsPutBreak);
    }
    public boolean isActive(){
        return active;
    }
    public Player addFrameScore(int points){
        return new Player(this.name,this.frameScore +points,0,this.ballsPutFrame,this.ballsPutBreak,this.active);
    }
    public Player changeFrameScore(int frameScore){
        return new Player(this.name,frameScore,0);
    }
    public Player changeName(String name){
        return new Player(name,this.frameScore,this.breakScore,this.ballsPutFrame,this.ballsPutBreak,this.active);
    }
    public Player addBreakScore(BallsEnum b){
        List<BallsEnum> tempFrameList = new ArrayList<>(ballsPutFrame);
        tempFrameList.add(b);
        List<BallsEnum> tempBreakList = new ArrayList<>(ballsPutBreak);
        tempBreakList.add(b);
        return new Player(this.name,this.frameScore+b.value,this.breakScore+b.value,tempFrameList,tempBreakList,this.active);
    }
    public Player resetBreakScore(){
        
        return new Player(this.name,this.frameScore,0,this.ballsPutFrame,new ArrayList<>(),false);
    }
}
