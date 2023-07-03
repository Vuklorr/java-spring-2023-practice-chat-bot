package com.practice.chatbot.database.service;

import com.practice.chatbot.database.entity.Answer;
import com.practice.chatbot.database.entity.Question;
import com.practice.chatbot.database.entity.Subtheme;

import java.util.List;

public interface SubthemeService {
    public Subtheme findQuestion(int id);
    public void saveQuestion(Subtheme subtheme);
    public void deleteQuestion(Subtheme subtheme);
    public void updateQuestion(Subtheme subtheme);
    public List<Subtheme> findAllQuestion();
}
