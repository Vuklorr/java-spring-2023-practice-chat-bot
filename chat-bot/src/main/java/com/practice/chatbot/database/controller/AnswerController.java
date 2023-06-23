package com.practice.chatbot.database.controller;

import com.practice.chatbot.database.entity.Answer;
import com.practice.chatbot.database.service.AnswerService;
import com.practice.chatbot.database.service.implementation.AnswerServiceImpl;

import java.util.List;

public class AnswerController {
    private final AnswerService answerService = new AnswerServiceImpl();

    public Answer findAnswer(int id){
        return answerService.findAnswer(id);
    }

    public void saveAnswer(Answer answer){
        answerService.saveAnswer(answer);
    }

    public void deleteAnswer(Answer answer){
        answerService.deleteAnswer(answer);
    }

    public void updateAnswer(Answer answer){
        answerService.updateAnswer(answer);
    }

    public List<Answer> findAllAnswer(){
        return answerService.findAllAnswer();
    }

}
