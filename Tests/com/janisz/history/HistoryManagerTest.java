package com.janisz.history;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HistoryManagerTest {
    
    HistoryManager<String> stringHistoryManager;
    Map<Integer, String> currentStringMap;
    int historySize = 5;
    int id;
    int statesChangesNUmber = 13;
    
    String[] stringsArray1;
    String first = "Pierwszy";
    String second = "Drugi";
    String third = "Trzeci";
    String fourth = "Czwarty";
    String fifth = "Piąty";
    String sixth = "Szósty";
    String seventh = "Siódmy";
    String eighth = "Ósmy";
    String ninth = "Dziewiąty";
    String tenth = "Dziesiąty";
    String eleventh = "Jedenasty";
    String twelfth = "Dwunasty";
    String thirteenth = "Trzynasty";
    
    @BeforeEach
    void setUp() {
        stringHistoryManager = new HistoryManager<>();
        currentStringMap = new HashMap<>();
        id = stringHistoryManager.createChronicle(first, historySize);
        stringsArray1 = new String[statesChangesNUmber];
        stringsArray1[0] = first;
        stringsArray1[1] = second;
        stringsArray1[2] = third;
        stringsArray1[3] =fourth;
        stringsArray1[4] = fifth;
        stringsArray1[5] = sixth;
        stringsArray1[6] = seventh;
        stringsArray1[7] = eighth;
        stringsArray1[8] =ninth;
        stringsArray1[9] =tenth;
        stringsArray1[10] = eleventh;
        stringsArray1[11] = twelfth;
        stringsArray1[12] = thirteenth;
    }
    
    
    @Test
    void goBack() {
        String current;
        
        for (int i = 0; i < historySize; i++) {
            current = stringsArray1[i];
            currentStringMap.put(id,current);
            stringHistoryManager.putBackState(currentStringMap);
        }
        
        for (int i = historySize-1; i >=0 ; i--) {

            assertEquals(stringsArray1[i],stringHistoryManager.goBack(currentStringMap).get(id).orElse("Kiszka"));
        }
      
        assertTrue(stringHistoryManager.goBack(currentStringMap).isEmpty());
    }
    
    @Test
    void goForward() {
        String current;
    
        for (int i = 0; i < historySize; i++) {
            current = stringsArray1[i];
            currentStringMap.put(id,current);
            stringHistoryManager.putBackState(currentStringMap);
        }
    
        current = stringsArray1[5];
        int thoughtLessClicking =10;
        for (int i = 0; i < historySize +thoughtLessClicking; i++) {
            currentStringMap.put(id,current);
            Map<Integer, Optional <String>> tempMap = stringHistoryManager.goBack(currentStringMap);
            if(!tempMap.isEmpty()) {
                current = tempMap.get(id).orElse("kiszka");
                
            }
            currentStringMap.put(id,current);
        }
        
        assertEquals(stringsArray1[0],currentStringMap.get(id));
        for (int i = 1; i < historySize+1; i++) {
            Map<Integer, Optional<String>> tempMap = stringHistoryManager.goForward(currentStringMap);
            if(!tempMap.isEmpty()){
                current = tempMap.get(id).orElse("Kiszka");
            }
            assertEquals(stringsArray1[i],current);
        }
        
        assertTrue(stringHistoryManager.goForward(currentStringMap).isEmpty());
    }
    //hobbit test
    @Test
    void goForwardAndBack() {
        String current;
        
        for (int i = 0; i < historySize; i++) {
            current = stringsArray1[i];
            currentStringMap.put(id,current);
            stringHistoryManager.putBackState(currentStringMap);
        }
        
        current = stringsArray1[5];
        for (int i = 0,j=historySize-1; i < historySize ; i++,j--) {
            currentStringMap.put(id,current);
            Map<Integer, Optional <String>> tempMap = stringHistoryManager.goBack(currentStringMap);
            if(!tempMap.isEmpty()) {
                current = tempMap.get(id).orElse("kiszka");
                
            }
            assertEquals(stringsArray1[j],current);
            currentStringMap.put(id,current);
        }
        
        assertEquals(stringsArray1[0],currentStringMap.get(id));
        for (int i = 1; i < historySize+1; i++) {
            Map<Integer, Optional<String>> tempMap = stringHistoryManager.goForward(currentStringMap);
            if(!tempMap.isEmpty()){
                current = tempMap.get(id).orElse("Kiszka");
            }
            assertEquals(stringsArray1[i],current);
            currentStringMap.put(id,current);
        }
        
        
        assertTrue(stringHistoryManager.goForward(currentStringMap).isEmpty());
        for (int i = 0; i < historySize ; i++) {
            currentStringMap.put(id,current);
            Map<Integer, Optional <String>> tempMap = stringHistoryManager.goBack(currentStringMap);
            if(!tempMap.isEmpty()) {
                current = tempMap.get(id).orElse("kiszka");
            
            }
            currentStringMap.put(id,current);
        }
    
        assertEquals(stringsArray1[0],currentStringMap.get(id));
    }
    @Test
    void putBackClearForwardTest(){
        String current;
    
        for (int i = 0; i < historySize; i++) {
            current = stringsArray1[i];
            currentStringMap.put(id,current);
            stringHistoryManager.putBackState(currentStringMap);
        }
    
        current = stringsArray1[5];
        for (int i = 0,j=historySize-1; i < historySize ; i++,j--) {
            currentStringMap.put(id,current);
            Map<Integer, Optional <String>> tempMap = stringHistoryManager.goBack(currentStringMap);
            if(!tempMap.isEmpty()) {
                current = tempMap.get(id).orElse("kiszka");
            
            }
            assertEquals(stringsArray1[j],current);
            currentStringMap.put(id,current);
        }
        
        stringHistoryManager.putBackState(currentStringMap);
        assertTrue(stringHistoryManager.goForward(currentStringMap).isEmpty());
        
        
    }
    
}