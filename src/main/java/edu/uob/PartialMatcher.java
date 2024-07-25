/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



package edu.uob;

import java.util.List;
import java.util.stream.Collectors;

public class PartialMatcher {

    // Method to find possible matches for a given action and input
    public List<String> findMatches(String input, List<String> possibleActions) {
        return possibleActions.stream()
                .filter(action -> action.startsWith(input))
                .collect(Collectors.toList());
    }

    // Method to handle ambiguous matches
    public String handleAmbiguity(String input, List<String> matches) {
        if (matches.size() > 1) {
            return "There is more than one thing you can '" + input + "' here... which one do you want? " + matches;
        } else if (matches.size() == 1) {
            return "Did you mean: " + matches.get(0) + "?";
        } else {
            return "No matches found for '" + input + "'.";
        }
    }

    // Method to provide suggestions for similar actions
    public String provideSuggestions(String input, List<String> possibleActions) {
        List<String> suggestions = possibleActions.stream()
                .filter(action -> action.contains(input))
                .collect(Collectors.toList());
        return suggestions.isEmpty() ? "No similar actions found." : "Did you mean one of these actions? " + suggestions;
    }

    // Method to check if the input is a valid action
    public boolean isValidAction(String input, List<String> possibleActions) {
        return possibleActions.contains(input);
    }

    // Method to check if the input partially matches any actions
    public boolean isPartialMatch(String input, List<String> possibleActions) {
        return possibleActions.stream().anyMatch(action -> action.startsWith(input));
    }

    // Method to suggest corrections for mistyped actions
    public String suggestCorrections(String input, List<String> possibleActions) {
        List<String> corrections = possibleActions.stream()
                .filter(action -> isSimilar(input, action))
                .collect(Collectors.toList());
        return corrections.isEmpty() ? "No corrections found." : "Did you mean: " + corrections + "?";
    }

    // Helper method to determine if two strings are similar (e.g., using Levenshtein distance)
    private boolean isSimilar(String input, String action) {
        int distance = calculateLevenshteinDistance(input, action);
        return distance <= 2; // Allow a small number of edits
    }

    // Method to calculate Levenshtein distance between two strings
    private int calculateLevenshteinDistance(String str1, String str2) {
        int[][] dp = new int[str1.length() + 1][str2.length() + 1];

        for (int i = 0; i <= str1.length(); i++) {
            for (int j = 0; j <= str2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1] 
                                 + (str1.charAt(i - 1) == str2.charAt(j - 1) ? 0 : 1), 
                                 Math.min(dp[i - 1][j] + 1, 
                                 dp[i][j - 1] + 1));
                }
            }
        }

        return dp[str1.length()][str2.length()];
    }
}

