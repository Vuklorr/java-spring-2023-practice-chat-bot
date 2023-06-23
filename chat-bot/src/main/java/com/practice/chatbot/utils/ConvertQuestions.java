package com.practice.chatbot.utils;

import java.util.Arrays;

public class ConvertQuestions {
    public static String convertQuestion (final String userQuestion) {
        StringBuilder convertedQuestion = new StringBuilder();
        String[] convertedQuestionList = userQuestion.toLowerCase().split(" ");
        Arrays.sort(convertedQuestionList);

        for(String str : convertedQuestionList) {
            convertedQuestion.append(str).append(" ");
        }

        return String.valueOf(convertedQuestion);
    }
}
