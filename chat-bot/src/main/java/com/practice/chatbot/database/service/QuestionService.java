package com.practice.chatbot.database.service;

import com.practice.chatbot.database.entity.Answer;
import com.practice.chatbot.database.entity.Question;

import java.util.List;

public interface QuestionService {
    public Question findQuestion(int id);
    public void saveQuestion(Question question);
    public void deleteQuestion(Question question);
    public void updateQuestion(Question question);
    public List<Question> findAllQuestion();
    public Answer findAnswerByID(int id);
    public String getAnswer(int subtheme, String uMessage);
}
