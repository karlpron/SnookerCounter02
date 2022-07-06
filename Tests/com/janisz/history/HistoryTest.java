package com.janisz.history;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HistoryTest {
    int size = 5;
    int id1 = 1;
    int id2 = 2;
    String[] stringsArray1;
    String first = "Pierwszy";
    String second = "Drugi";
    String third = "Trzeci";
    String fourth = "Czwarty";
    String fifth = "Piąty";
    
    String[] stringsArray2;
    
    private History<String> stringHistory;
    
    @BeforeEach
    void setUp(){
        stringHistory= new History<>();
        stringHistory.create(first,size,id1);
        stringHistory.create(first,size,id2);
        stringsArray1 = new String[size];
    
        stringsArray1[0] = first;
        stringsArray1[1] = second;
        stringsArray1[2] = third;
        stringsArray1[3] = fourth;
        stringsArray1[4] = fifth;
        
        stringsArray2 = new String[size];
    
        stringsArray2[0] = "A";
        stringsArray2[1] = "B";
        stringsArray2[2] = "C";
        stringsArray2[3] = "D";
        stringsArray2[4] = "E";
        
        
        for(String s: stringsArray1){
            stringHistory.put(id1,s);
        }
        for(String s: stringsArray2){
            stringHistory.put(id2,s);
        }
        
    }
    
    @Test
    void getBackSingle() {
        String current = "Zupka";
        for (int i = size-1; i >= 0; i--) {
            assertEquals(stringsArray1[i],stringHistory.getBack(id1,current).orElse("Kicha"));
        }
        assertTrue(stringHistory.getBack(id1,current).isEmpty());
    }
    @Test
    void getBackDouble() {
        String current = "Zupka";
        for (int i = size-1; i >= 0; i--) {
            assertEquals(stringsArray1[i],stringHistory.getBack(id1,current).orElse("Kicha"));
            assertEquals(stringsArray2[i],stringHistory.getBack(id2,current).orElse("Kicha"));
        }
        assertTrue(stringHistory.getBack(id1,current).isEmpty());
        assertTrue(stringHistory.getBack(id2,current).isEmpty());
        
    }
    
    
    @Test
    void getForwardSingle() {
        String[] strings = new String[size];
        strings[0] = "szósty";
        strings[1] = "siódmy";
        strings[2] = "ósmy";
        strings[3] = "dziewiąty";
        strings[4] = "dziesiąty";
        
        String current= "Zupka";
        for (int i = 0; i < size; i++) {
            stringHistory.getBack(id1,strings[i]);
        }
        for (int i = size-1; i >= 0; i--) {
            assertEquals(strings[i],stringHistory.getForward(id1,current).orElse("Kicha"));
        }
        assertTrue(stringHistory.getForward(id1,current).isEmpty());
        
    }
    
    @Test
    void getForwardDouble() {
        String[] strings = new String[size];
        strings[0] = "szósty";
        strings[1] = "siódmy";
        strings[2] = "ósmy";
        strings[3] = "dziewiąty";
        strings[4] = "dziesiąty";
    
        String[] strings2 = new String[size];
        strings2[0] = "F";
        strings2[1] = "G";
        strings2[2] = "H";
        strings2[3] = "I";
        strings2[4] = "J";
        
        String current= "Zupka";
        for (int i = 0; i < size; i++) {
            stringHistory.getBack(id1,strings[i]);
            stringHistory.getBack(id2,strings2[i]);
        }
        
        
        for (int i = size-1; i >= 0; i--) {
            assertEquals(strings[i],stringHistory.getForward(id1,current).orElse("Kicha"));
            assertEquals(strings2[i],stringHistory.getForward(id2,current).orElse("Kicha"));
        }
        assertTrue(stringHistory.getForward(id1,current).isEmpty());
        assertTrue(stringHistory.getForward(id2,current).isEmpty());
    }
    

}