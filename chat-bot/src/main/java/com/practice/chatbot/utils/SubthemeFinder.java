package com.practice.chatbot.utils;

import com.practice.chatbot.database.controller.SubthemeController;
import com.practice.chatbot.database.entity.Subtheme;

import java.util.List;

public class SubthemeFinder {
    public static String findSubthemes(String uMessage){
        SubthemeController subthemeController= new SubthemeController();
        List<Subtheme> subthemes = subthemeController.query(uMessage);
        StringBuilder sb = new StringBuilder();
        sb.append("Выберите одну из предложенных подтем: \n");
        for(int i = 0;i<subthemes.size();i++){
            sb.append("/s").append(i+1).append(". ").append(subthemes.get(i)).append("\n");
        }
        return sb.toString();
    }
}
