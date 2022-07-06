package com.janisz.history;

import java.util.Optional;

public class Chronicle<T> {
    
    private final EventStack<T> back;
    private final EventStack<T> forward;
    
    
    public Chronicle(int size){
        back = new EventStack<>(size);
        forward = new EventStack<>(size);
    }
    public void putBack(T t){
        
        back.push(t);
        forward.clear();
    }
    public Optional <T> getBack(T current) {
//        Optional<T> optionalT = Optional.ofNullable(current);
//        optionalT.ifPresent(forward::push);
        //forward.push(current);
        transferForward(current);
        return back.pop();
    }
    private void transferForward(T current){
        Optional<T> optionalT = Optional.ofNullable(current);
        optionalT.ifPresent(forward::push);
    }
    public Optional <T> getForward(T current ) {
//        Optional<T> optionalT = Optional.ofNullable(current);
//        optionalT.ifPresent(back::push);
        transferBack(current);
        
        return forward.pop();
    }
    private void transferBack(T current){
        Optional<T> optionalT = Optional.ofNullable(current);
        optionalT.ifPresent(back::push);
    }
    public boolean isForwardEmpty(){
        return forward.isEmpty();
    }
    public void clearForward(){
        forward.clear();
    }
}
