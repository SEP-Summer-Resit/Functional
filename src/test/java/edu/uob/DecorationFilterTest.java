/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

 package edu.uob;

 import java.util.Arrays;
 import java.util.HashSet;
 import java.util.Set;

 import static org.junit.jupiter.api.Assertions.assertEquals;
 import static org.junit.jupiter.api.Assertions.assertFalse;
 import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
 
 public class DecorationFilterTest {
 
     private DecorationFilter decorationFilter;
 
     @BeforeEach
     public void setUp() {
         decorationFilter = new DecorationFilter();
     }
 
     @Test
     public void testFilterDecorations() {
         String input = "get the sword from the chest";
         String expectedOutput = "get sword chest";
         assertEquals(expectedOutput, decorationFilter.filterDecorations(input));
     }
 
     @Test
     public void testContainsDecorativeWords() {
         String inputWithDecorativeWords = "look at the sky";
         String inputWithoutDecorativeWords = "look sky";
         assertTrue(decorationFilter.containsDecorativeWords(inputWithDecorativeWords));
         assertFalse(decorationFilter.containsDecorativeWords(inputWithoutDecorativeWords));
     }
 
     @Test
     public void testGetDecorativeWords() {
         Set<String> expectedDecorativeWords = new HashSet<>(Arrays.asList("the", "a", "an", "at", "on", "in", "with", "of", "and", "to", "from", "by"));
         assertEquals(expectedDecorativeWords, decorationFilter.getDecorativeWords());
     }
 
     @Test
     public void testRemoveSpecificDecorativeWords() {
         String input = "open the door with a key";
         Set<String> specificWordsToRemove = new HashSet<>(Arrays.asList("the", "a"));
         String expectedOutput = "open door with key";
         assertEquals(expectedOutput, decorationFilter.removeSpecificDecorativeWords(input, specificWordsToRemove));
     }
 
     @Test
     public void testAddDecorativeWords() {
         Set<String> newWords = new HashSet<>(Arrays.asList("about", "around"));
         decorationFilter.addDecorativeWords(newWords);
         assertTrue(decorationFilter.getDecorativeWords().containsAll(newWords));
     }
 }
 