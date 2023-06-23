package com.practice.chatbot.database.service;

import com.practice.chatbot.database.entity.Theme;

import java.util.List;

public interface ThemeService {
    public Theme findTheme(int id);
    public void saveTheme(Theme theme);
    public void deleteTheme(Theme theme);
    public void updateTheme(Theme theme);
    public List<Theme> findAllThemes();
}
