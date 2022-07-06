package com.janisz.history;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventStackTest {
    
    int size = 5;
    String[] stringsArray;
    String first = "Pierwszy";
    String second = "Drugi";
    String third = "Trzeci";
    String fourth = "Czwarty";
    String fifth = "Piąty";
    
    EventStack<String> stringEventStack;
    @BeforeEach
    void init(){
        stringEventStack = new EventStack<>(size);
        stringsArray = new String[size];
        stringsArray[0] = first;
        stringsArray[1] = second;
        stringsArray[2] = third;
        stringsArray[3] = fourth;
        stringsArray[4] = fifth;
    }
    @Test
    void popTestOnce() {
        stringEventStack.push(first);
        String soup = "Zupa";
        
        assertEquals(first,stringEventStack.pop().orElse(soup));
    
    }
    @Test
    void popTestInSize() {
        for(String s:stringsArray){
            stringEventStack.push(s);
        }
        
        String soup = "Zupa";
        
        for (int i= stringsArray.length-1;i>=0;i--){
            assertEquals(stringsArray[i],stringEventStack.pop().orElse(soup));
        }
        
    }
    
    @Test
    void popTestOversize() {
        for(String s:stringsArray){
            stringEventStack.push(s);
        }
        
        String soup = "Zupa";
        stringEventStack.push(soup);
        assertEquals(soup,stringEventStack.pop().orElse("Kaszka"));
        for (int i= stringsArray.length-1;i > 0;i--){
            assertEquals(stringsArray[i],stringEventStack.pop().orElse(soup));
        }
        assertEquals("Kiszka",stringEventStack.pop().orElse("Kiszka"));
    }
    
    @Test
    void getSize() {
        assertEquals(size,stringEventStack.getSize());
    }
    
    @Test
    void clear() {
        for(String s:stringsArray){
            stringEventStack.push(s);
        }
        stringEventStack.clear();
        assertEquals("Kiszka",stringEventStack.pop().orElse("Kiszka"));
    }
    
    @Test
    void isEmpty() {
        assertTrue(stringEventStack.isEmpty());
    }
}