package com.janisz.history;

import java.util.Optional;

public class EventStack<T>  {

    private final FixedSizeArrayDeque<T> fixedDeck;
    
    private final int size;
    
    public EventStack(int size){
        this.size = size;
        fixedDeck = new FixedSizeArrayDeque<>(size);
    }
    public Optional<T> pop(){
        return Optional.ofNullable(fixedDeck.poll());
    }
    public void push(T t){
        
        fixedDeck.offerFirst(t);
    }

    public int getSize(){
        return size;
    }
    public void clear(){
        fixedDeck.clear();
    }

    public boolean isEmpty(){
        return fixedDeck.isEmpty();
    }
    
}
