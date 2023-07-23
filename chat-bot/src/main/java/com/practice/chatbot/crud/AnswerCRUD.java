package com.practice.chatbot.crud;

import com.practice.chatbot.database.controller.AnswerController;
import com.practice.chatbot.database.entity.Answer;
import com.practice.chatbot.database.entity.Theme;

import java.util.List;

public class AnswerCRUD {
    private static final AnswerController answerController = new AnswerController();

    public static String answerCreate(String uMessage) {
        try {
            String answerContent = uMessage.substring(4);
            answerController.saveAnswer(new Answer(answerContent));
        } catch (StringIndexOutOfBoundsException e) {
            return "Вы ввели пустую строку!";
        }

        return "Данные успешно сохранены";
    }

    public static String answerRead() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Таблица Ответы.\n\n");
        stringBuilder.append(" | ID |  Ответы  |\n |---------------------|\n");

        List<Answer> answersList = answerController.findAllAnswer();

        for(Answer answer : answersList) {
            stringBuilder.append(" | ").append(answer.getId())
                    .append(" | ").append(answer.getContent()).append(" |\n");
        }

        return stringBuilder.toString();
    }
}
