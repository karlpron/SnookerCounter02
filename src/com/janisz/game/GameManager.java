package com.janisz.game;

import com.janisz.game.actors.Player;
import com.janisz.game.actors.Table;
import com.janisz.game.analysis.Analytics;
import com.janisz.game.analysis.PlayerFuture;
import com.janisz.history.HistoryManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//monsterClass :(
public class GameManager {
    
    private Table table;
    private final Map<Integer, PlayerFuture> playersFutureData;
    private final Map<Integer,Archivisable> archivisableMap;
    private final int tableId;
    private final int player1Id;
    private final int player2Id;
    private int activePLayer = -1;
    private int inActivePlayer =-1;
    
    
    private boolean accessible;
    
    private final HistoryManager<Archivisable> historyManager;
    
    private final Map<Integer,Archivisable> changed;
    
    public GameManager (){
        

        archivisableMap = new HashMap<>();
        playersFutureData = new HashMap<>();
        changed = new HashMap<>();

        
        Player player1 = new Player();
        Player player2 = new Player();
        table = new Table();
        
        historyManager = new HistoryManager<>();
        int historySize = 30;
        player1Id = historyManager.createChronicle(player1, historySize);
        player2Id = historyManager.createChronicle(player2, historySize);
        tableId = historyManager.createChronicle(table, historySize);
        

        archivisableMap.put(tableId,table);
        archivisableMap.put(player1Id,player1);
        archivisableMap.put(player2Id,player2);
        
        playersFutureData.put(player1Id, Analytics.getPLayerFuture((Player) archivisableMap.get(player1Id),(Player) archivisableMap.get(player2Id),table,BallsEnum.BLACK));
        playersFutureData.put(player2Id,Analytics.getPLayerFuture((Player) archivisableMap.get(player2Id),(Player) archivisableMap.get(player1Id),table,BallsEnum.BLACK));
    }
    public void changeState(BallsEnum playedBall, int playerId, FunInterface funInterface,BallsEnum futureBall){
        accessible = true;
        if(activePLayer!=playerId){
            setActivePlayer(playerId);
        }
        
        
        funInterface.doFun(playedBall,playerId);
        
        playersFutureData.put(activePLayer,Analytics.getPLayerFuture((Player) archivisableMap.get(activePLayer),(Player)archivisableMap.get(inActivePlayer),table,futureBall));
        playersFutureData.put(inActivePlayer,Analytics.getPLayerFuture((Player) archivisableMap.get(inActivePlayer),(Player)archivisableMap.get(activePLayer),table,futureBall));
        
        pushChange();
        
        accessible = false;
    }
    public void resetBreaks(){
        resetPLayerBreakScore(inActivePlayer);
        resetPLayerBreakScore(activePLayer);
        pushChange();
    }
    //zabezpieczyć przed bezpośrednim dostępem
    public void putBall(BallsEnum b,int playerId){
        if(accessible){
            resetPLayerBreakScore(inActivePlayer);
            removeBallFromTable(b);
            int score = b.value;
            if(b!=BallsEnum.WHITE) {
                addPlayerBreakScore(activePLayer,b);
            }
            else{
                resetPLayerBreakScore(activePLayer);
                addPlayerFrameScore(inActivePlayer,score);
            }
        }else{
            changeState(b,playerId,this::putBall,BallsEnum.BLACK);
        }

    }
    //if used directly you lose
    public void faul(BallsEnum b,int playerId){
        if(accessible){
            resetPLayerBreakScore(activePLayer);
            int score;
            if(b==BallsEnum.RED){
                score = BallsEnum.WHITE.value;
            }
            else{
                score = b.value;
            }
            addPlayerFrameScore(inActivePlayer,score);
        }else{
            changeState(b,playerId,this::faul,BallsEnum.BLACK);
        }
    }
    
    private void removeBallFromTable(BallsEnum b){

        commitChange(tableId);

        table = table.removeBall(b);
    }
    
    private void addPlayerBreakScore(int playerId, BallsEnum b){

        commitChange(playerId);
        
        Player tempPlayer = ((Player)archivisableMap.get(playerId)).addBreakScore(b);
        

        archivisableMap.put(playerId,tempPlayer);
    }
    private void addPlayerFrameScore(int playerId, int score){

        commitChange(playerId);
        
        Player tempPlayer =( (Player) archivisableMap.get(playerId)).addFrameScore(score);
        archivisableMap.put(playerId, tempPlayer);
    }
    private void resetPLayerBreakScore(int playerId){

        commitChange(playerId);
        
        archivisableMap.put(playerId,((Player)archivisableMap.get(playerId)).resetBreakScore());
    }
    
    
    private void setActivePlayer(int id){
        activePLayer = id;
        if(activePLayer==player1Id){
            inActivePlayer=player2Id;
        }else{
            inActivePlayer=player1Id;
        }
    }

    public void undoLastChange() {
        Map<Integer,Optional<Archivisable>> tempMap = historyManager.goBack(archivisableMap);
        for (Integer i: tempMap.keySet()){
            tempMap.get(i).ifPresent(v->archivisableMap.put(i,v));
        }
    }
    public void redoLastChange(){
        Map<Integer,Optional<Archivisable>> tempMap = historyManager.goForward(archivisableMap);
        for(Integer i:tempMap.keySet()){
            tempMap.get(i).ifPresent(v->archivisableMap.put(i,v));
        }
    }
    
    public void changePLayerName(String name, int playerId){

        commitChange(playerId);
        Player tempPlayer= ((Player)archivisableMap.get(playerId)).changeName(name);
        archivisableMap.put(playerId,tempPlayer);
        

        pushChange();

    }
    public void changePLayerScore(int score,int playerId){

        commitChange(playerId);
       archivisableMap.put(playerId,((Player)archivisableMap.get(playerId)).changeFrameScore(score));
        pushChange();
    }


    public int[] getPlayersIds(){
        return new int[]{player1Id,player2Id};
    }
    
    public int getPLayerFrameScore(int playerId){
        return ((Player)archivisableMap.get(playerId)).getFrameScore();
    }
    public int getPlayerBreakScore(int playerId){
        return ((Player)archivisableMap.get(playerId)).getBreakScore();
    }
    public String getPlayerName(int playerId){
        return ((Player)archivisableMap.get(playerId)).getName();
    }

    public PlayerFuture getPlayerFuture(int playerId){
        return playersFutureData.get(playerId).copy();
    }
    public int getTableValue(){
        return table.getValueOfBallsPresent();
    }
    public List<BallsEnum> getPlayerFrameHistory(int playerId){
        return ((Player)archivisableMap.get(playerId)).getBallsPutFrame();
    }
    public List<BallsEnum> getPlayerBreakHistory(int playerId){
        return ((Player)archivisableMap.get(playerId)).getBallsPutBreak();
    }
 
    private void commitChange(int id){
        changed.put(id,archivisableMap.get(id));
    }
    private void pushChange(){
        historyManager.putBackState(changed);
        changed.clear();
    }


}
