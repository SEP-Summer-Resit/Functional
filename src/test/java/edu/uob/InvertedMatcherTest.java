/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

 package edu.uob;

 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;
 
 import java.util.Arrays;
 import java.util.List;
 
 import static org.junit.jupiter.api.Assertions.assertEquals;
 
 public class InvertedMatcherTest {
 
     private InvertedMatcher invertedMatcher;
     private List<String> possibleMatches;
 
     @BeforeEach
     public void setUp() {
         invertedMatcher = new InvertedMatcher();
         possibleMatches = Arrays.asList("look", "inv", "get", "drop", "goto", "reset", "find", "consume");
     }
 
     @Test
     public void testFindMatches() {
         List<String> matches = invertedMatcher.findMatches("go", possibleMatches);
         assertEquals(1, matches.size());
         assertEquals("goto", matches.get(0));
     }
 
     @Test
     public void testHandleAmbiguityMultipleMatches() {
         List<String> matches = Arrays.asList("look", "inv");
         String result = invertedMatcher.handleAmbiguity("o", matches);
         assertEquals("There are multiple matches containing 'o'. Did you mean one of these? [look, inv]", result);
     }
 
     @Test
     public void testHandleAmbiguitySingleMatch() {
         List<String> matches = Arrays.asList("goto");
         String result = invertedMatcher.handleAmbiguity("go", matches);
         assertEquals("Did you mean: goto?", result);
     }
 
     @Test
     public void testHandleAmbiguityNoMatches() {
         List<String> matches = Arrays.asList();
         String result = invertedMatcher.handleAmbiguity("fly", matches);
         assertEquals("No matches found containing 'fly'.", result);
     }
 }
 