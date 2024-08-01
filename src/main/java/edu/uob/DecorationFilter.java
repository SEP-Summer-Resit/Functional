/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



/**
 *
 * @author liumu
 */
package edu.uob;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DecorationFilter {

    private static final Set<String> DECORATIVE_WORDS = new HashSet<>(Arrays.asList("the", "a", "an", "at", "on", "in", "with", "of", "and", "to", "from", "by"));

    // Method to filter out decorative words from the input command
    public String filterDecorations(String input) {
        List<String> words = Arrays.asList(input.split("\\s+"));
        List<String> filteredWords = words.stream()
                .filter(word -> !DECORATIVE_WORDS.contains(word.toLowerCase()))
                .collect(Collectors.toList());
        return String.join(" ", filteredWords);
    }

    // Method to check if the command contains any decorative words
    public boolean containsDecorativeWords(String input) {
        List<String> words = Arrays.asList(input.split("\\s+"));
        return words.stream().anyMatch(word -> DECORATIVE_WORDS.contains(word.toLowerCase()));
    }

    // Method to provide a list of decorative words
    public Set<String> getDecorativeWords() {
        return DECORATIVE_WORDS;
    }

    // Method to remove specific decorative words from the command
    public String removeSpecificDecorativeWords(String input, Set<String> specificWords) {
        List<String> words = Arrays.asList(input.split("\\s+"));
        List<String> filteredWords = words.stream()
                .filter(word -> !specificWords.contains(word.toLowerCase()))
                .collect(Collectors.toList());
        return String.join(" ", filteredWords);
    }

    // Method to add new decorative words
    public void addDecorativeWords(Set<String> newWords) {
        DECORATIVE_WORDS.addAll(newWords);
    }
}


