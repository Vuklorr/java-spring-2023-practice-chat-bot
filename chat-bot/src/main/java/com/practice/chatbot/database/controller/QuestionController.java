package com.practice.chatbot.database.controller;

import com.practice.chatbot.database.entity.Answer;
import com.practice.chatbot.database.entity.Question;
import com.practice.chatbot.database.service.QuestionService;
import com.practice.chatbot.database.service.implementation.QuestionServiceImpl;

import java.util.List;

public class QuestionController {
    private final QuestionService questionService = new QuestionServiceImpl();

    public Question findQuestion(int id){
        return questionService.findQuestion(id);
    }

    public void saveQuestion(Question question){
        questionService.saveQuestion(question);
    }

    public void deleteQuestion(Question question){
        questionService.deleteQuestion(question);
    }

    public void updateQuestion(Question question){
        questionService.updateQuestion(question);
    }

    public List<Question> findAllQuestion(){
        return questionService.findAllQuestion();
    }

    public Answer findAnswerByID(int id){
        return questionService.findAnswerByID(id);
    }

    public String getAnswer(int subtheme,String uMessage){return questionService.getAnswer(subtheme,uMessage);}
}
