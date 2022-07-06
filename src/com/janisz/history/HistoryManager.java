package com.janisz.history;

import org.jetbrains.annotations.NotNull;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HistoryManager <T> {
    
    private final Deque <int[]> backIds;
    private final Deque <int[]> forwardIds;
    static final int DEFAULT_SIZE = 1 << 4;
    private int index = 0;
    private int numberOfObjects = 0;
    private int length = 10;
    
    private final History<T> history;
    
    
    public HistoryManager(){
        history = new History<>();
        
        backIds = new FixedSizeArrayDeque<>(DEFAULT_SIZE);
        forwardIds =new FixedSizeArrayDeque<>(DEFAULT_SIZE);
    }
    public <U extends T> int createChronicle(@NotNull U u, int size){
        //put the first state of object, just to make sure nobody changes their mind later
        history.create(u,size,index);
        numberOfObjects++;
        length +=size;
        changeSize();
        return index++;
    }
    
    public  void putBackState(@NotNull Map<Integer,T> changedObjects){

        int [] changedIds = changedObjects.keySet().stream().mapToInt(s->s).toArray();
        backIds.offerFirst(changedIds);
        for(Integer i: changedObjects.keySet()){
            history.put(i,changedObjects.get(i));
        }
        forwardIds.clear();
        history.clearForward();
    }

    //may return empty map
    public  Map<Integer,Optional <T>> goBack(Map<Integer,T> current){
        int[] temp =Optional.ofNullable( backIds.poll()).orElse(new int[0]);
        
        Map<Integer, Optional<T>> optionalMap = new HashMap<>();
        for(int i:temp){
            optionalMap.put(i,history.getBack(i, current.get(i)));
        }
        if(temp.length>0){
            forwardIds.offerFirst(temp);
        }

        return optionalMap;
    }
    //may return empty map
    public Map<Integer, Optional<T>> goForward(Map<Integer,T> current){
        int[] temp = Optional.ofNullable(forwardIds.poll()).orElse(new int[0]);
        Map<Integer,Optional<T>> optionalMap =new HashMap<>();
        for(int i:temp){
            optionalMap.put(i,history.getForward(i, current.get(i)));
        }
        if(temp.length>0){
            backIds.offerFirst(temp);
        }

        return optionalMap;
    }
    
    
    private void changeSize(){
        //I don't like it, uncle Bob also wouldn't
        //make fixedDeque interface, or clone
        int size = (numberOfObjects* (length/numberOfObjects))+DEFAULT_SIZE;
        
        ((FixedSizeArrayDeque<int[]>) backIds).changeSize(size);
        ((FixedSizeArrayDeque<int[]>) forwardIds).changeSize(size);
    }
    
    
    //implement deleting history of individual objects (e.g. deleted rectangle in graphic program)

    
}
