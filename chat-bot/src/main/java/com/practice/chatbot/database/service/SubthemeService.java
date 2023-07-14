package com.practice.chatbot.database.service;

import com.practice.chatbot.database.entity.Answer;
import com.practice.chatbot.database.entity.Question;
import com.practice.chatbot.database.entity.Subtheme;

import java.util.List;

public interface SubthemeService {
    public Subtheme findSubtheme(int id);
    public void saveSubtheme(Subtheme subtheme);
    public void deleteSubtheme(Subtheme subtheme);
    public void updateSubtheme(Subtheme subtheme);
    public List<Subtheme> findAllSubthemes();
    public List<Subtheme> query(String uMessage);
    public int findID(String uMessage);
}
