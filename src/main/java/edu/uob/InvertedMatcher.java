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
}
