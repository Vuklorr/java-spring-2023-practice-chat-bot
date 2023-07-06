package com.practice.chatbot.crud;

import com.practice.chatbot.database.controller.ThemeController;
import com.practice.chatbot.database.entity.Subtheme;
import com.practice.chatbot.database.entity.Theme;

import java.util.List;

public class ThemeCRUD {
    private final static ThemeController themeController = new ThemeController();
    public static String themeRead() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("| ID |  ТЕМЫ  |\n").append(" |--------------|\n");

        List<Theme> themeList = themeController.findAllThemes();

        for(Theme theme : themeList) {
           stringBuilder.append(" | ").append(theme.getId())
                   .append(" | ").append(theme.getContent()).append(" |\n");
        }

        return stringBuilder.toString();
    }

}
