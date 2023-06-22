package com.practice.chatbot.database.implementation;

import com.practice.chatbot.database.entity.Theme;
import com.practice.chatbot.database.interfaces.ThemeDAO;
import com.practice.chatbot.database.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ThemeDAOImpl implements ThemeDAO {
    @Override
    public List<Theme> allThemes() {
        List<Theme> themes = (List<Theme>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Theme").list();
        HibernateSessionFactoryUtil.getSessionFactory().close();
        return themes;
    }

    @Override
    public void add(Theme theme) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(theme);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Theme theme) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(theme);
        transaction.commit();
        session.close();
    }

    @Override
    public void edit(Theme theme) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(theme);
        transaction.commit();
        session.close();
    }

    @Override
    public Theme findByID(int id) {
        Theme theme = HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Theme.class, id);
        HibernateSessionFactoryUtil.getSessionFactory().close();
        return theme;
    }
}
