package com.practice.chatbot.utils;

import com.practice.chatbot.database.controller.QuestionController;

public class QuestionSolver {
    public static String findAnswer(int subtheme,String uMessage){
        QuestionController questionController = new QuestionController();
        return questionController.getAnswer(subtheme,uMessage);
    }
}
