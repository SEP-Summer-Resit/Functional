/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uob;

import java.util.List;
import java.util.stream.Collectors;

public class InvertedMatcher {

    // Method: Find strings that contain the input substring
    public List<String> findMatches(String input, List<String> possibleMatches) {
        return possibleMatches.stream()
                .filter(match -> match.contains(input))
                .collect(Collectors.toList());
    }

    // Method: Handle cases where multiple matches are found
    public String handleAmbiguity(String input, List<String> matches) {
        if (matches.size() > 1) {
            return "There are multiple matches containing '" + input + "'. Did you mean one of these? " + matches;
        } else if (matches.size() == 1) {
            return "Did you mean: " + matches.get(0) + "?";
        } else {
            return "No matches found containing '" + input + "'.";
        }
    }

    // Method: Suggest similar matches
    public String suggestSimilarMatches(String input, List<String> possibleMatches) {
        List<String> suggestions = possibleMatches.stream()
                .filter(match -> match.contains(input))
                .collect(Collectors.toList());
        return suggestions.isEmpty() ? "No similar matches found." : "Did you mean one of these matches? " + suggestions;
    }

    // Method: Find strings that are similar to the input
    public List<String> findSimilarMatches(String input, List<String> possibleMatches) {
        return possibleMatches.stream()
                .filter(match -> isSimilar(match, input))
                .collect(Collectors.toList());
    }

    // Method: Check if two strings are similar
    private boolean isSimilar(String str1, String str2) {
        int maxDistance = Math.min(str1.length(), str2.length()) / 2;
        return calculateLevenshteinDistance(str1, str2) <= maxDistance;
    }

    // Method: Calculate the Levenshtein distance between two strings
    private int calculateLevenshteinDistance(String str1, String str2) {
        int[][] dp = new int[str1.length() + 1][str2.length() + 1];
        for (int i = 0; i <= str1.length(); i++) {
            for (int j = 0; j <= str2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1] + (str1.charAt(i - 1) == str2.charAt(j - 1) ? 0 : 1),
                            Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1));
                }
            }
        }
        return dp[str1.length()][str2.length()];
    }
}
