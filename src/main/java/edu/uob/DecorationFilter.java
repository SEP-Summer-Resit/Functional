/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



/**
 *
 * @author liumu
 */
package edu.uob;

import java.util.List;
import java.util.stream.Collectors;

public class DecorationFilter {

    // Method to find decorations that match the input
    public List<String> filterDecorations(String input, List<String> availableDecorations) {
        return availableDecorations.stream()
                .filter(decoration -> decoration.contains(input))
                .collect(Collectors.toList());
    }

    // Method to handle ambiguous decoration matches
    public String handleAmbiguousDecorations(String input, List<String> matches) {
        if (matches.size() > 1) {
            return "There are multiple decorations matching '" + input + "'. Did you mean one of these? " + matches;
        } else if (matches.size() == 1) {
            return "Did you mean: " + matches.get(0) + "?";
        } else {
            return "No decorations found matching '" + input + "'.";
        }
    }

    // Method to suggest similar decorations
    public String suggestSimilarDecorations(String input, List<String> availableDecorations) {
        List<String> suggestions = availableDecorations.stream()
                .filter(decoration -> decoration.contains(input))
                .collect(Collectors.toList());
        return suggestions.isEmpty() ? "No similar decorations found." : "Did you mean one of these decorations? " + suggestions;
    }

    // Method to validate decoration input
    public boolean isValidDecoration(String input, List<String> availableDecorations) {
        return availableDecorations.contains(input);
    }

    // Method to suggest corrections for mistyped decorations
    public String suggestDecorationCorrections(String input, List<String> availableDecorations) {
        List<String> corrections = availableDecorations.stream()
                .filter(decoration -> isSimilar(input, decoration))
                .collect(Collectors.toList());
        return corrections.isEmpty() ? "No corrections found." : "Did you mean: " + corrections + "?";
    }

    // Helper method to determine if two strings are similar (e.g., using Levenshtein distance)
    private boolean isSimilar(String input, String decoration) {
        int distance = calculateLevenshteinDistance(input, decoration);
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

