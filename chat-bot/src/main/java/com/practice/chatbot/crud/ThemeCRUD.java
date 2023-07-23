package com.practice.chatbot.crud;

import com.practice.chatbot.crud.utils.SearchIdAndIndex;
import com.practice.chatbot.database.controller.ThemeController;
import com.practice.chatbot.database.entity.Theme;

import javax.persistence.PersistenceException;
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

    public static String themeUpdate(String uMessage) {
        try {
            String themeContent = uMessage.substring(4);

            int[] idAndIndexTheme = SearchIdAndIndex.searchIdAndIndex(themeContent, 0);
            themeController.updateTheme(new Theme(idAndIndexTheme[0], themeContent.substring(idAndIndexTheme[1] + 1)));
        } catch (StringIndexOutOfBoundsException | PersistenceException e) {
            return "Неверно введены данные!";
        }

        return "Данные успешно обновлены";
    }
}
