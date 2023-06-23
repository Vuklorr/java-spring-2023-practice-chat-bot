package com.practice.chatbot.database.service;

import com.practice.chatbot.database.entity.Answer;

import java.util.List;

public interface AnswerService {
    public Answer findAnswer(int id);
    public void saveAnswer(Answer answer);
    public void deleteAnswer(Answer answer);
    public void updateAnswer(Answer answer);
    public List<Answer> findAllAnswer();

}
