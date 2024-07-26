/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

 package edu.uob;

 import java.util.Arrays;
 import java.util.List;
 
 import static org.junit.jupiter.api.Assertions.assertEquals;
 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;
 
 public class AmbiguityRefusalTest {
 
     private AmbiguityRefusal ambiguityRefusal;
 
     @BeforeEach
     public void setUp() {
         ambiguityRefusal = new AmbiguityRefusal();
     }
 
     @Test
     public void testHandleAmbiguity() {
         String result = ambiguityRefusal.handleAmbiguity("get");
         assertEquals("There is more than one thing you can 'get' here... which one do you want?", result);
     }
 
     @Test
     public void testHandleAmbiguousObject() {
         List<String> objects = Arrays.asList("sword", "potion", "shield");
         String result = ambiguityRefusal.handleAmbiguousObject("use", objects);
         assertEquals("There is more than one use you can perform with the following objects: [sword, potion, shield]. Please be specific.", result);
     }
 
     @Test
     public void testHandleInvalidFormat() {
         String result = ambiguityRefusal.handleInvalidFormat("goto");
         assertEquals("The command format seems off. Please use the correct format. Example: 'get sword', 'goto forest'.", result);
     }
 
     @Test
     public void testHandleUnknownCommand() {
         String result = ambiguityRefusal.handleUnknownCommand("fly");
         assertEquals("I'm not sure what you mean by 'fly'. Please use one of the accepted commands: 'look', 'inv', 'get <artefact>', 'drop <artefact>', 'goto <location>', 'reset'.", result);
     }
 
     @Test
     public void testHandleEmptyCommand() {
         String result = ambiguityRefusal.handleEmptyCommand();
         assertEquals("You didn't enter any command. Please type something.", result);
     }
 }
 