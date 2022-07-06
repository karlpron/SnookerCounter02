package com.janisz.history;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Deque;

import static org.junit.jupiter.api.Assertions.*;

class FixedSizeArrayDequeTest {
    
    int maxSize;
    Deque<String> stringDeque;
    String[] stringsArray;
    String first = "Pierwszy";
    String second = "Drugi";
    String third = "Trzeci";
    String fourth = "Czwarty";
    String fifth = "Piąty";
    
    @BeforeEach
    void setUp() {
        maxSize = 5;
        stringDeque  = new FixedSizeArrayDeque<>(maxSize);
        stringsArray = new String[maxSize];
        stringsArray[0] = first;
        stringsArray[1] = second;
        stringsArray[2] = third;
        stringsArray[3] = fourth;
        stringsArray[4] = fifth;
        
    }
    
    @Test
    void offerFirstTest() {
        for(int i =0;i<maxSize;i++){
            assertTrue(stringDeque.offerFirst(stringsArray[i]));
        }

    }
    @Test
    
    void offerFirstCheckValue(){
        
        offerFirstTest();
        assertEquals(fifth,stringDeque.poll());
        for(int i = stringDeque.size();i>0;i--){
            assertEquals(stringsArray[i-1],stringDeque.poll());
        }
        
    }
    @Test
    void offerFirstOversize(){
        offerFirstTest();
        String overString = "Kiszka";
        stringDeque.offerFirst(overString);
    
        assertEquals(overString,stringDeque.poll());
        for(int i = stringDeque.size(), j=stringsArray.length-1;i>0;i--,j--){
            
            assertEquals(stringsArray[j],stringDeque.poll());
        }
        assertNull(stringDeque.poll());
    }
    
    @Test
    void changeSizeIncreaseTest(){
        String underString = "Zupa";
        stringDeque.offerFirst(underString);
        ((FixedSizeArrayDeque<String>)stringDeque).changeSize(7);
        offerFirstTest();
        String overString = "Kiszka";
        stringDeque.offerFirst(overString);
        assertEquals(overString,stringDeque.poll());
        for(int i = maxSize; i>0;i--){
            
            assertEquals(stringsArray[i-1],stringDeque.poll());
        }
        assertEquals(underString,stringDeque.poll());
        assertNull(stringDeque.poll());
    }
    @Test
    void changeSizeDecreaseTest(){
        offerFirstTest();
        String overString = "Kaszka";
        stringDeque.offerFirst(overString);
        ((FixedSizeArrayDeque<String>)stringDeque).changeSize(2);
        assertEquals(overString,stringDeque.poll());
        assertEquals(fifth,stringDeque.poll());
        assertNull(stringDeque.poll());
        
    }
    @Test
    void changeSizeEqualTest(){
        offerFirstTest();
        ((FixedSizeArrayDeque<String>)stringDeque).changeSize(5);
        for(int i = stringDeque.size();i>0;i--){
            assertEquals(stringsArray[i-1],stringDeque.poll());
        }
    }
}