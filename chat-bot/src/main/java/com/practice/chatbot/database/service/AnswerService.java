package com.practice.chatbot.database.service;

import com.practice.chatbot.database.entity.Answer;
import com.practice.chatbot.database.implementation.AnswerDAOImpl;

import java.util.List;

public class AnswerService {
    private AnswerDAOImpl AnswerEntity = new AnswerDAOImpl();

    public AnswerService(){
    }

    public Answer findAnswer(int id){
        return AnswerEntity.findByID(id);
    }
    public void saveAnswer(Answer answer){
        AnswerEntity.add(answer);
    }
    public void deleteAnswer(Answer answer){
        AnswerEntity.delete(answer);
    }
    public void updateAnswer(Answer answer){
        AnswerEntity.edit(answer);
    }
    public List<Answer> findAllAnswer(){
        return AnswerEntity.allAnswer();
    }
}
