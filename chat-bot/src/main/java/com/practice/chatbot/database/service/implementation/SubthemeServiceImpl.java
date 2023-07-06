package com.practice.chatbot.database.service.implementation;

import com.practice.chatbot.database.dao.SubthemeDAO;
import com.practice.chatbot.database.dao.implementation.SubthemeDAOImpl;
import com.practice.chatbot.database.entity.Subtheme;
import com.practice.chatbot.database.service.SubthemeService;

import java.util.List;

public class SubthemeServiceImpl implements SubthemeService {
    private final SubthemeDAO subthemeDAO = new SubthemeDAOImpl();
    @Override
    public Subtheme findSubtheme(int id) {return subthemeDAO.findByID(id);}

    @Override
    public void saveSubtheme(Subtheme subtheme) {subthemeDAO.add(subtheme);}

    @Override
    public void deleteSubtheme(Subtheme subtheme) {subthemeDAO.delete(subtheme);}

    @Override
    public void updateSubtheme(Subtheme subtheme) {subthemeDAO.edit(subtheme);}

    @Override
    public List<Subtheme> findAllSubtheme() {return subthemeDAO.allSubthemes();}
}
