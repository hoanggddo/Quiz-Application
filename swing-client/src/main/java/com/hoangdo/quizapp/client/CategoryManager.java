package com.hoangdo.quizapp.client;

import javax.swing.*;

/**
 * Adapted from the original DifficultyManager. The API's Question model
 * has no difficulty field -- questions are organized by category
 * (Vocabulary, Grammar, History & Culture) instead. This dialog picks a
 * category rather than a difficulty level. If difficulty is something you
 * want back, it would need to be added as a field on the Question entity
 * in the API first.
 */
public class CategoryManager {

    public static final String[] CATEGORIES = {"Vocabulary", "Grammar", "History & Culture"};

    public static String chooseCategory() {
        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose a quiz category:",
                "Category Selection",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                CATEGORIES,
                CATEGORIES[0]
        );

        if (choice < 0) {
            return CATEGORIES[0];
        }
        return CATEGORIES[choice];
    }
}
