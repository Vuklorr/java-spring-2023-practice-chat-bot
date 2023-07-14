package com.practice.chatbot.utils;

import java.util.Arrays;

public class ConvertQuestions {
    public static String convertQuestion (String userQuestion) {
        StringBuilder convertedQuestion = new StringBuilder();
        if(userQuestion.contains("?")) {
            userQuestion = userQuestion.substring(0, userQuestion.length() - 1);
        }
        String[] convertedQuestionList = userQuestion.toLowerCase().split(" ");
        Arrays.sort(convertedQuestionList);

        for(String str : convertedQuestionList) {
            convertedQuestion.append(str).append(" ");
        }

        return String.valueOf(convertedQuestion);
    }
}
