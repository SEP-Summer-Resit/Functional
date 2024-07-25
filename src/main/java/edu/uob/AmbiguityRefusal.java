/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

 package edu.uob;

 import java.util.List;
 
 public class AmbiguityRefusal {
 
     // Method to handle ambiguity refusal
     public String handleAmbiguity(String action) {
         return "There is more than one thing you can '" + action + "' here... which one do you want?";
     }
 
     // Method to handle ambiguous objects
     public String handleAmbiguousObject(String action, List<String> possibleObjects) {
         return "There is more than one " + action + " you can perform with the following objects: " + possibleObjects + ". Please be specific.";
     }
 
     // Method to handle invalid command format
     public String handleInvalidFormat(String command) {
         return "The command format seems off. Please use the correct format. Example: 'get sword', 'goto forest'.";
     }
 
     // Method to handle unknown commands
     public String handleUnknownCommand(String command) {
         return "I'm not sure what you mean by '" + command + "'. Please use one of the accepted commands: 'look', 'inv', 'get <artefact>', 'drop <artefact>', 'goto <location>', 'reset'.";
     }
 
     // Method to handle empty commands
     public String handleEmptyCommand() {
         return "You didn't enter any command. Please type something.";
     }
 
     // Other ambiguity handling methods can be added here
 }
 