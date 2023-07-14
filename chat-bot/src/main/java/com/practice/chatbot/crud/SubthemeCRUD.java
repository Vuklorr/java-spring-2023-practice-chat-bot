package com.practice.chatbot.crud;

import com.practice.chatbot.database.controller.SubthemeController;
import com.practice.chatbot.database.entity.Subtheme;

import java.util.List;

public class SubthemeCRUD {
    private static final SubthemeController subthemController = new SubthemeController();
    public static String subthemeRead() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("| ID |  ПОДТЕМЫ  | ID ТЕМЫ |\n |-----------------|\n");

        List<Subtheme> subthemesList = subthemController.findAllSubthemes();

        for(Subtheme subtheme : subthemesList) {
            stringBuilder.append(" | ").append(subtheme.getId())
                    .append(" | ").append(subtheme.getContent())
                    .append(" |").append(subtheme.getTheme_id()).append("\n");
        }

        return stringBuilder.toString();
    }
}
