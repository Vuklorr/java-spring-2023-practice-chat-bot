package com.practice.chatbot.database.controller;

import com.practice.chatbot.database.entity.Subtheme;
import com.practice.chatbot.database.service.SubthemeService;
import com.practice.chatbot.database.service.implementation.SubthemeServiceImpl;

import java.util.List;

public class SubthemeController {
    private final SubthemeService subthemeService = new SubthemeServiceImpl();

    public Subtheme findSubtheme(int id){return subthemeService.findSubtheme(id);}
    public Subtheme findSubtheme(int id){
        return subthemeService.findSubtheme(id);
    }

    public void saveSubtheme(Subtheme subtheme){
        subthemeService.saveSubtheme(subtheme);
    }

    public void deleteSubtheme(Subtheme subtheme){
        subthemeService.deleteSubtheme(subtheme);
    }

    public void updateTheme(Subtheme subtheme){
        subthemeService.updateSubtheme(subtheme);
    }
    public List<Subtheme> findAllSubthemes(){
        return subthemeService.findAllSubthemes();
    }
    public List<Subtheme> query(String uMessage){return subthemeService.query(uMessage);}
    public void updateSubtheme(Subtheme subtheme){
        subthemeService.updateSubtheme(subtheme);
    }

    public List<Subtheme> findAllSubtheme(){
        return subthemeService.findAllSubtheme();
    }

}
