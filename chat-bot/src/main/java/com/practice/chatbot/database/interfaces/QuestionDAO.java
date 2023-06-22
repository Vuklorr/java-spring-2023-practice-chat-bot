package com.practice.chatbot.database.interfaces;

import com.practice.chatbot.database.entity.Answer;
import com.practice.chatbot.database.entity.Question;

import java.util.List;

public interface QuestionDAO {
    List<Question> allQuestion();
    void add(Question question);
    void delete(Question question);
    void edit(Question question);
    Question findByID(int id);
    Answer findAnswerByID(int id);
}
