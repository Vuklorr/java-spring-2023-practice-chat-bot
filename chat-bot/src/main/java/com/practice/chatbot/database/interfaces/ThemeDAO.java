package com.practice.chatbot.database.interfaces;

import com.practice.chatbot.database.entity.Theme;

import java.util.List;

public interface ThemeDAO {
    List<Theme> allThemes();
    void add(Theme theme);
    void delete(Theme theme);
    void edit(Theme theme);
    Theme findByID(int id);
}
