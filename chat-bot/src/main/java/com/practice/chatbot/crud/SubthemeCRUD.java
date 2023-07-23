package com.practice.chatbot.crud;

import com.practice.chatbot.crud.utils.SearchIdAndIndex;
import com.practice.chatbot.database.controller.SubthemeController;
import com.practice.chatbot.database.entity.Subtheme;
import com.practice.chatbot.database.entity.Theme;
import org.hibernate.JDBCException;

import javax.persistence.PersistenceException;
import java.util.List;

public class SubthemeCRUD {
    private static final SubthemeController subthemeController = new SubthemeController();

    public static String subthemeCreate(String uMessage) {
        try {
            String subthemContent = uMessage.substring(4);
            int[] subthemeIdAndIndex = SearchIdAndIndex.searchIdAndIndex(subthemContent, subthemContent.length() - 1);

            subthemeController.saveSubtheme(new Subtheme(subthemContent.substring(0, subthemeIdAndIndex[1]), subthemeIdAndIndex[0]));
        } catch (JDBCException | StringIndexOutOfBoundsException e) {
            return "Неверно введены данные!";
        }

        return "Данные успешно сохранены";
    }

    public static String subthemeRead() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Таблица Подтемы.\n\n");
        stringBuilder.append(" | ID |  ПОДТЕМЫ  | ID ТЕМЫ |\n |-----------------------------|\n");

        List<Subtheme> subthemesList = subthemeController.findAllSubthemes();

        for(Subtheme subtheme : subthemesList) {
            stringBuilder.append(" | ").append(subtheme.getId())
                    .append(" | ").append(subtheme.getContent())
                    .append("\t|").append(subtheme.getTheme_id()).append("\n");
        }

        return stringBuilder.toString();
    }

    public static String subthemeUpdate(String uMessage) {
        try {
            String subthemeContent = uMessage.substring(4);

            int[] idAndIndexSubtheme = SearchIdAndIndex.searchIdAndIndex(subthemeContent, 0);
            int[] idAndIndexTheme = SearchIdAndIndex.searchIdAndIndex(subthemeContent, subthemeContent.length() - 1);

            subthemeController.updateTheme(new Subtheme(idAndIndexSubtheme[0]
                    , subthemeContent.substring(idAndIndexSubtheme[1], idAndIndexTheme[1])
                    , idAndIndexTheme[0]));

        } catch (StringIndexOutOfBoundsException | PersistenceException e) {
            return "Неверно введены данные!";
        }

        return "Данные успешно обновлены";
    }
}
