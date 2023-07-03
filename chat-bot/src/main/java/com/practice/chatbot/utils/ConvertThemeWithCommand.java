package com.practice.chatbot.utils;

import com.practice.chatbot.database.controller.ThemeController;
import com.practice.chatbot.database.entity.Theme;

import java.util.List;

public class ConvertThemeWithCommand {
    public static String convertThem() {
        List<Theme> themeList = new ThemeController().findAllThemes();
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < themeList.size(); i++) {
            stringBuilder.append("/").append(i + 1).append(". ").append(themeList.get(i).getContent()).append("\n");
        }
        return stringBuilder.toString();
    }
}
