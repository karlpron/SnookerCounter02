package com.janisz.game.actors;

import com.janisz.game.Archivisable;
import com.janisz.game.BallsEnum;

import java.util.HashMap;
import java.util.Map;

public final class Table implements Archivisable {
    
    private final Map<BallsEnum,Integer> ballsMap;
    private int valueOfBallsPresent;
    
    
    public Table(){
        ballsMap = new HashMap<>();
        for(BallsEnum b:BallsEnum.values()){
            ballsMap.put(b, b.number);
        }
        calculateOverallValue();
    }
    private Table(Map<BallsEnum, Integer> ballsMap){
        this.ballsMap = Map.copyOf(ballsMap);
        calculateOverallValue();
    }
    
    private void calculateOverallValue(){
        int temp = 0;
        for(BallsEnum b:ballsMap.keySet()){
            if(b!=BallsEnum.WHITE){
                if(b!=BallsEnum.RED){
                    temp+=b.value*ballsMap.get(b);
                }else{
                    temp+=(b.value+BallsEnum.BLACK.value)*ballsMap.get(b);
                }
            }
            
        }
        
        valueOfBallsPresent = temp;
    }
    
    public int getValueOfBallsPresent() {
        return valueOfBallsPresent;
    }
    public Map<BallsEnum,Integer> getBalls(){
        return new HashMap<>(ballsMap);
    }
    
    public Table removeBall(BallsEnum b){
        
        if(ballsMap.get(b)>0){
            int temp = ballsMap.get(b)-1;
            Map<BallsEnum, Integer> nMap = new HashMap<>(ballsMap);
            nMap.put(b,temp);
            return new Table(nMap);
        }
        return this;
    }
    
}
