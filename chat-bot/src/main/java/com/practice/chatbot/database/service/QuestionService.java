package com.practice.chatbot.database.service;

import com.practice.chatbot.database.entity.Answer;
import com.practice.chatbot.database.entity.Question;
import com.practice.chatbot.database.implementation.QuestionDAOImpl;

import java.util.List;

// This is service for working with DAO classes.
public class QuestionService {
    // Creating a
    private QuestionDAOImpl QuestionEntity = new QuestionDAOImpl();

    public QuestionService(){
    }

    public Question findQuesiton(int id){
        return QuestionEntity.findByID(id);
    }
    public void saveQuestion(Question question){
        QuestionEntity.add(question);
    }
    public void deleteQuestion(Question question){
        QuestionEntity.delete(question);
    }
    public void updateQuestion(Question question){
        QuestionEntity.edit(question);
    }
    public List<Question> findAllQuestion(){
        return QuestionEntity.allQuestion();
    }
    public Answer findAnswerByID(int id){
        return QuestionEntity.findAnswerByID(id);
    }
}
