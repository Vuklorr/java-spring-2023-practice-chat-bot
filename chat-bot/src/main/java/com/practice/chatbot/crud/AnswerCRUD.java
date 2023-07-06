package com.practice.chatbot.crud;

import com.practice.chatbot.database.controller.AnswerController;
import com.practice.chatbot.database.controller.SubthemeController;
import com.practice.chatbot.database.entity.Answer;
import com.practice.chatbot.database.entity.Subtheme;

import java.util.List;

public class AnswerCRUD {
    private static final AnswerController answerController = new AnswerController();
    public static String answerRead() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("| ID |  Ответы  |\n |-----------------|\n");

        List<Answer> answersList = answerController.findAllAnswer();

        for(Answer answer : answersList) {
            stringBuilder.append(" | ").append(answer.getId())
                    .append(" |   ").append(answer.getContent()).append(" |\n");
        }

        return stringBuilder.toString();
    }
}
