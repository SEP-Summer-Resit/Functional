/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



/**
 *
 * @author liumu
 */
package edu.uob;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class VariabilityTestGenerator {

    // Define trigger words
    private static final List<String> TRIGGER_WORDS = Arrays.asList(
        "look", "inv", "get", "drop", "goto", "reset", "find", "consume"
    );

    // Define subjects
    private static final List<String> SUBJECTS = Arrays.asList(
        "sword", "potion", "apple", "forest", "castle", "treasure", "dragon"
    );

    // Define decorative words
    private static final List<String> DECORATIVE_WORDS = Arrays.asList(
        "the", "a", "an", "some", "one", "two"
    );

    // Define punctuations
    private static final List<String> PUNCTUATIONS = Arrays.asList(
        ".", ",", ";", "!", "?"
    );

    private final Random random = new Random();

    // Generate a random command
    public String generateCommand() {
        String triggerWord = getRandomElement(TRIGGER_WORDS);
        String subject = getRandomElement(SUBJECTS);
        List<String> words = new ArrayList<>();

        // Randomly choose whether to add a decorative word
        if (random.nextBoolean()) {
            words.add(getRandomElement(DECORATIVE_WORDS));
        }

        words.add(subject);

        // Randomly choose whether to add punctuation
        if (random.nextBoolean()) {
            words.add(getRandomElement(PUNCTUATIONS));
        }

        // Randomly choose whether to add extra spaces
        String command = String.join(" ", words);
        if (random.nextBoolean()) {
            command = command.replace(" ", "  ");
        }

        // Determine command structure based on the trigger word
        if (Arrays.asList("get", "drop", "goto", "consume").contains(triggerWord)) {
            return triggerWord + " " + command;
        } else if (triggerWord.equals("find")) {
            return triggerWord + " " + command;
        } else {
            return triggerWord;
        }
    }

    // Get a random element from a list
    private <T> T getRandomElement(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }
}
