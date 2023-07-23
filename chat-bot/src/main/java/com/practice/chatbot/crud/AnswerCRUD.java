package com.practice.chatbot.crud;

import com.practice.chatbot.crud.utils.SearchIdAndIndex;
import com.practice.chatbot.database.controller.AnswerController;
import com.practice.chatbot.database.entity.Answer;
import com.practice.chatbot.database.entity.Subtheme;
import com.practice.chatbot.database.entity.Theme;

import javax.persistence.PersistenceException;
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

    public static String answerUpdate(String uMessage) {
        try {
            String answerContent = uMessage.substring(4);
            int[] idAndIndexAnswer = SearchIdAndIndex.searchIdAndIndex(answerContent, 0);

            answerController.updateAnswer(new Answer(idAndIndexAnswer[0], answerContent.substring(idAndIndexAnswer[1] + 1)));

        } catch (StringIndexOutOfBoundsException | PersistenceException e) {
            return "Неверно введены данные!";
        }

        return "Данные успешно обновлены";
    }
}
