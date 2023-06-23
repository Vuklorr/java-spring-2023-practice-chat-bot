package com.practice.chatbot.database.service.implementation;

import com.practice.chatbot.database.dao.AnswerDAO;
import com.practice.chatbot.database.entity.Answer;
import com.practice.chatbot.database.dao.implementation.AnswerDAOImpl;
import com.practice.chatbot.database.service.AnswerService;

import java.util.List;

public class AnswerServiceImpl implements AnswerService {
    private final AnswerDAO answerDAO = new AnswerDAOImpl();


    @Override
    public Answer findAnswer(int id){
        return answerDAO.findByID(id);
    }

    @Override
    public void saveAnswer(Answer answer){
        answerDAO.add(answer);
    }

    @Override
    public void deleteAnswer(Answer answer){
        answerDAO.delete(answer);
    }

    @Override
    public void updateAnswer(Answer answer){
        answerDAO.edit(answer);
    }

    @Override
    public List<Answer> findAllAnswer(){
        return answerDAO.allAnswer();
    }
}
