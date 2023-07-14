package com.practice.chatbot.utils;

import com.practice.chatbot.database.controller.SubthemeController;

public class SubthemeIDFinder {
    public static int getID(String uMessage){
        SubthemeController subthemeController = new SubthemeController();
        int subthemeID = subthemeController.findID(uMessage);
        return subthemeID;
    }
}
