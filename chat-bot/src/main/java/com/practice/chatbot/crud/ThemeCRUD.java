package com.practice.chatbot.crud;

import com.practice.chatbot.database.controller.ThemeController;
import com.practice.chatbot.database.entity.Theme;

import java.util.List;

public class ThemeCRUD {
    private final static ThemeController themeController = new ThemeController();

    public static String themeCreate(String uMessage) {
        try {
            String themeContent = uMessage.substring(4);
            themeController.saveTheme(new Theme(themeContent));
        } catch (StringIndexOutOfBoundsException e) {
            return "Неверно введены данные!";
        }

        return "Данные успешно сохранены";
    }


    public static String themeRead() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Таблица Темы.\n\n");
        stringBuilder.append(" | ID |  ТЕМЫ  |\n").append(" |--------------|\n");

        List<Theme> themeList = themeController.findAllThemes();

        for (Theme theme : themeList) {
            stringBuilder.append(" | ").append(theme.getId())
                    .append(" | ").append(theme.getContent()).append(" |\n");
        }

        return stringBuilder.toString();
    }

}
