package com.practice.chatbot.database.service.implementation;

import com.practice.chatbot.database.dao.QuestionDAO;
import com.practice.chatbot.database.entity.Answer;
import com.practice.chatbot.database.entity.Question;
import com.practice.chatbot.database.dao.implementation.QuestionDAOImpl;
import com.practice.chatbot.database.service.QuestionService;

import java.util.List;

// This is service for working with DAO classes.
public class QuestionServiceImpl implements QuestionService {
    // Creating a
    private final QuestionDAO questionDAO = new QuestionDAOImpl();


    @Override
    public Question findQuestion(int id){
        return questionDAO.findByID(id);
    }

    @Override
    public void saveQuestion(Question question){
        questionDAO.add(question);
    }

    @Override
    public void deleteQuestion(Question question){
        questionDAO.delete(question);
    }

    @Override
    public void updateQuestion(Question question){
        questionDAO.edit(question);
    }

    @Override
    public List<Question> findAllQuestion(){
        return questionDAO.allQuestion();
    }

    @Override
    public Answer findAnswerByID(int id){
        return questionDAO.findAnswerByID(id);
    }

    public String getAnswer(int subtheme,String uMessage){return questionDAO.getAnswer(subtheme,uMessage);}
}
