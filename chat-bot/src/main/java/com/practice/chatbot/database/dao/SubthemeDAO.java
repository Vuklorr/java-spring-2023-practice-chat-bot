package com.practice.chatbot.database.dao;

import com.practice.chatbot.database.entity.Answer;
import com.practice.chatbot.database.entity.Question;
import com.practice.chatbot.database.entity.Subtheme;

import java.util.List;

public interface SubthemeDAO {
    List<Subtheme> allSubthemes();
    void add(Subtheme subtheme);
    void delete(Subtheme subtheme);
    void edit(Subtheme subtheme);
    Subtheme findByID(int id);
    List<Subtheme> query(String uMessage);
    int findID(String content);
    }
