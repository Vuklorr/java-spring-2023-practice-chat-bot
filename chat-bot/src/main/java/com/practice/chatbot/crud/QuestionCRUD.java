package com.practice.chatbot.crud;

import com.practice.chatbot.crud.utils.SearchIdAndIndex;
import com.practice.chatbot.database.controller.QuestionController;
import com.practice.chatbot.database.entity.Question;
import com.practice.chatbot.database.entity.Subtheme;
import org.hibernate.JDBCException;

import javax.persistence.PersistenceException;
import java.util.List;

public class QuestionCRUD {
    private static final QuestionController questionController = new QuestionController();

    public static String questionCreate(String uMessage) {
        try {
            String questionContent = uMessage.substring(4);
            int[] subthemeIdAndIndex = SearchIdAndIndex.searchIdAndIndex(questionContent, 0);
            int[] answerIdAndIndex = SearchIdAndIndex.searchIdAndIndex(questionContent, questionContent.length() - 1);

            questionController.saveQuestion(new Question(subthemeIdAndIndex[0], questionContent.substring(subthemeIdAndIndex[1] + 1, answerIdAndIndex[1] + 1), answerIdAndIndex[0]));
        } catch (JDBCException | StringIndexOutOfBoundsException e) {
            return "Неверно введены данные!";
        }

        return "Данные успешно сохранены";
    }

    public static String questionRead() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Таблицы Вопросы.\n\n");
        stringBuilder.append(" | ID |ID ТЕМЫ|  ВОПРОСЫ  |ID ОТВЕТА|\n |------------------------------------|\n");

        List<Question> questionList = questionController.findAllQuestion();

        for(Question question : questionList) {
            stringBuilder.append(" | ").append(question.getId())
                    .append(" | ").append(question.getSubthemeID())
                    .append(" |").append(question.getQuestion())
                    .append(" |").append(question.getAnswerID()).append("\n");
        }

        return stringBuilder.toString();
    }

    public static String questionUpdate(String uMessage) {
        try {
            String subthemeContent = uMessage.substring(4);

            int[] idAndIndexQuestion = SearchIdAndIndex.searchIdAndIndex(subthemeContent, 0);
            String data = subthemeContent.substring(idAndIndexQuestion[1] + 1);

            int[] idAndIndexSubheme = SearchIdAndIndex.searchIdAndIndex(data, 0);
            int[] idAndIndexAnswer = SearchIdAndIndex.searchIdAndIndex(data, data.length() - 1);

            questionController.updateQuestion(new Question(idAndIndexQuestion[0]
                    , idAndIndexSubheme[0]
                    , data.substring(idAndIndexSubheme[1] + 1, idAndIndexAnswer[1])
                    , idAndIndexAnswer[0]));

        } catch (StringIndexOutOfBoundsException | PersistenceException e) {
            return "Неверно введены данные!";
        }

        return "Данные успешно обновлены";
    }
}
