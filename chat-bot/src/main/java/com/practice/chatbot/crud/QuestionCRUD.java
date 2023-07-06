package com.practice.chatbot.crud;

import com.practice.chatbot.database.controller.QuestionController;
import com.practice.chatbot.database.entity.Question;

import java.util.List;

public class QuestionCRUD {
    private static final QuestionController questionController = new QuestionController();
    public static String questionRead() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("| ID |  ID ТЕМЫ  | ВОПРОСЫ |  ID ОТВЕТА  |\n |---------------------|\n");

        List<Question> questionList = questionController.findAllQuestion();

        for(Question question : questionList) {
            stringBuilder.append(" | ").append(question.getId())
                    .append(" | ").append(question.getSubthemeID())
                    .append(" |").append(question.getQuestion())
                    .append(" |").append(question.getAnswerID()).append("\n");
        }

        return stringBuilder.toString();
    }
}
