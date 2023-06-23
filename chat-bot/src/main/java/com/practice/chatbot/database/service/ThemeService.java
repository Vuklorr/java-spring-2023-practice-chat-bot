package com.practice.chatbot.database.service;

import com.practice.chatbot.database.entity.Theme;
import com.practice.chatbot.database.dao.implementation.ThemeDAOImpl;

import java.util.List;

public class ThemeService {
    private ThemeDAOImpl ThemeEntity = new ThemeDAOImpl();

    public ThemeService(){
    }

    public Theme findTheme(int id){
        return ThemeEntity.findByID(id);
    }
    public void saveTheme(Theme theme){
        ThemeEntity.add(theme);
    }
    public void deleteTheme(Theme theme){
        ThemeEntity.delete(theme);
    }
    public void updateTheme(Theme theme){
        ThemeEntity.edit(theme);
    }
    public List<Theme> findAllThemes(){
        return ThemeEntity.allThemes();
    }
}
