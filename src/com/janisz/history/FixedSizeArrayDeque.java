package com.janisz.history;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;

public class FixedSizeArrayDeque<T> extends ArrayDeque<T> {
    private int maxSize;
    
    public FixedSizeArrayDeque(int maxSize){
        super(maxSize);
        this.maxSize = maxSize;
    }
    
    @Override
    public boolean offerFirst(@NotNull T t){
        if (this.size() >= maxSize) {
            this.removeLast();
        }
        return super.offerFirst(t);
    }
    public void changeSize(int newSize){
        if(newSize>maxSize) {
            maxSize = newSize;
        }
        else{
            while(maxSize-- > newSize){
                //removeLast throws an exception if the value is null
                this.pollLast();
            }
        }
    }
    
    
}
