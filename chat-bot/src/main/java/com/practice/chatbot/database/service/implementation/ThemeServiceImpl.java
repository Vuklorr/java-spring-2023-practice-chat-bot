package com.practice.chatbot.database.service.implementation;

import com.practice.chatbot.database.dao.ThemeDAO;
import com.practice.chatbot.database.entity.Theme;
import com.practice.chatbot.database.dao.implementation.ThemeDAOImpl;
import com.practice.chatbot.database.service.ThemeService;

import java.util.List;

public class ThemeServiceImpl implements ThemeService {
    private final ThemeDAO themeDAO = new ThemeDAOImpl();


    @Override
    public Theme findTheme(int id){
        return themeDAO.findByID(id);
    }

    @Override
    public void saveTheme(Theme theme){
        themeDAO.add(theme);
    }

    @Override
    public void deleteTheme(Theme theme){
        themeDAO.delete(theme);
    }

    @Override
    public void updateTheme(Theme theme){
        themeDAO.edit(theme);
    }

    @Override
    public List<Theme> findAllThemes(){
        return themeDAO.allThemes();
    }
}
