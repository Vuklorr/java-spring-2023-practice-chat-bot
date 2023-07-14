package com.practice.chatbot.database.dao.implementation;

import com.practice.chatbot.database.controller.ThemeController;
import com.practice.chatbot.database.dao.SubthemeDAO;
import com.practice.chatbot.database.entity.Question;
import com.practice.chatbot.database.entity.Subtheme;
import com.practice.chatbot.database.entity.Theme;
import com.practice.chatbot.database.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class SubthemeDAOImpl implements SubthemeDAO {
    @Override
    public List<Subtheme> allSubthemes() {
        return (List<Subtheme>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Subtheme").list();
    }

    @Override
    public void add(Subtheme subtheme) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(subtheme);
        transaction.commit();
    }

    @Override
    public void delete(Subtheme subtheme) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(subtheme);
        transaction.commit();
    }

    @Override
    public void edit(Subtheme subtheme) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(subtheme);
        transaction.commit();
    }

    @Override
    public Subtheme findByID(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Subtheme.class, id);
    }
    @Override
    public List<Subtheme> query(String uMessage){
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        int themeNum = Integer.parseInt(new StringBuilder(uMessage).deleteCharAt(0).toString())-1;
        List<Theme> themeList = new ThemeController().findAllThemes();
        String content = "%"+themeList.get(themeNum).getContent()+"%";
        session.beginTransaction();
        String sqlQuery = String.format("SELECT s.content from Subtheme s inner join Theme t on t.id = s.theme_id where t.content like '%s'",content);
        List subthemes = session.createQuery(sqlQuery).list();
        return subthemes;
    }

    public int findID(String content){
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String sqlQuery = String.format("SELECT s.id from Subtheme s where s.content like '%s'",content);
        return Integer.parseInt(session.createQuery(sqlQuery).list().get(0).toString());
    }
}
