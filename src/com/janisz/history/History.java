package com.janisz.history;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class History <T> {
   
   

   
   private final Map <Integer, Chronicle<T>> chronicleMap;
   
   public History(){
       chronicleMap = new HashMap<>();
   }
   
   public  void create(T t,int size, int id){
       
       Chronicle<T> aChronicle = new Chronicle<>(size);
       aChronicle.putBack(t);
       chronicleMap.put(id,aChronicle);
   }
   public void put( int id,T t){
       chronicleMap.get(id).putBack(t);
   }
   public Optional<T> getBack(int id,T current) {
        return chronicleMap.get(id).getBack(current);
   }
   public  Optional<T> getForward(int id, T current){
       return chronicleMap.get(id).getForward(current);
   }
    public void clearForward(){
        for(Integer i: chronicleMap.keySet()){
            chronicleMap.get(i).clearForward();
        }
        
    }

   
}
