package com.janisz.history;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChronicleTest {
    int size = 5;
    String[] stringsArray;
    String first = "Pierwszy";
    String second = "Drugi";
    String third = "Trzeci";
    String fourth = "Czwarty";
    String fifth = "Piąty";
    
    Chronicle<String> stringChronicle;
    
    @BeforeEach
    void setUp() {
        stringChronicle = new Chronicle<>(size);
        stringsArray = new String[size];
        stringsArray[0] = first;
        stringsArray[1] = second;
        stringsArray[2] = third;
        stringsArray[3] = fourth;
        stringsArray[4] = fifth;
    }
    
    @Test
    void getBackTest() {
    
    
        for (String s : stringsArray) {
            stringChronicle.putBack(s);
        }
        String current = "Kaszka";
        for (int i = size-1; i >=0; i--) {
            assertEquals(stringsArray[i],stringChronicle.getBack(current).orElse("Kiszka"));
        }
    
        assertEquals("Kiszka",stringChronicle.getBack(current).orElse("Kiszka"));
    }
    
    @Test
    void getForwardTest() {
        for (String s : stringsArray) {
            stringChronicle.putBack(s);
        }
        String current = "Kaszka";
        String[] strings = new String[size];
        strings[0] = "szósty";
        strings[1] = "siódmy";
        strings[2] = "ósmy";
        strings[3] = "dziewiąty";
        strings[4] = "dziesiąty";
        for (int i = 0; i < strings.length; i++) {
            stringChronicle.getBack(strings[i]);
        }
        for (int i = size - 1; i >= 0; i--) {
            assertEquals(strings[i], stringChronicle.getForward(current).orElse("Kiszka"));
        }
        assertTrue(stringChronicle.isForwardEmpty());
        assertEquals("Kiszka",stringChronicle.getForward(current).orElse("Kiszka"));
        
    }
    @Test
    void isForwardEmpty() {
        assertTrue(stringChronicle.isForwardEmpty());
    }
    
    @Test
    void clearForward() {
        String current = "Kaszka";
        String[] strings = new String[size];
        strings[0] = "szósty";
        strings[1] = "siódmy";
        strings[2] = "ósmy";
        strings[3] = "dziewiąty";
        strings[4] = "dziesiąty";
        for (int i = 0; i < strings.length; i++) {
            stringChronicle.getBack(strings[i]).orElse("Kiszka");
        }
        assertFalse(stringChronicle.isForwardEmpty());
        
        stringChronicle.clearForward();
        assertTrue(stringChronicle.isForwardEmpty());
        assertEquals("Kiszka",stringChronicle.getForward(current).orElse("Kiszka"));
        assertTrue(stringChronicle.getForward(current).isEmpty());
        
    }
}