package com.practice.chatbot.utils;

import com.practice.chatbot.database.controller.SubthemeController;
import com.practice.chatbot.database.entity.Subtheme;

import java.util.List;

public class SubthemeFinder {
    public static List<Subtheme> findSubthemes(String uMessage){
        SubthemeController subthemeController= new SubthemeController();
        List<Subtheme> subthemes = subthemeController.query(uMessage);
        return subthemes;
    }
}
