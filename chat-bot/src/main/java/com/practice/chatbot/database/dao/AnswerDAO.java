package com.practice.chatbot.database.dao;

import com.practice.chatbot.database.entity.Answer;

import java.util.List;

public interface AnswerDAO {
    List<Answer> allAnswer();
    void add(Answer answer);
    void delete(Answer answer);
    void edit(Answer answer);
    Answer findByID(int id);
}
