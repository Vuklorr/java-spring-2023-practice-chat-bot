package com.practice.chatbot.database.controller;

import com.practice.chatbot.database.entity.Theme;
import com.practice.chatbot.database.service.ThemeService;
import com.practice.chatbot.database.service.implementation.ThemeServiceImpl;

import java.util.List;

public class ThemeController {
    private final ThemeService themeService = new ThemeServiceImpl();

    public Theme findTheme(int id){
        return themeService.findTheme(id);
    }

    public void saveTheme(Theme theme){
        themeService.saveTheme(theme);
    }

    public void deleteTheme(Theme theme){
        themeService.deleteTheme(theme);
    }

    public void updateTheme(Theme theme){
        themeService.updateTheme(theme);
    }


    public List<Theme> findAllThemes(){
        return themeService.findAllThemes();
    }

}
