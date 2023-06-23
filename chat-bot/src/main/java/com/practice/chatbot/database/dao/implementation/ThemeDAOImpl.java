package com.practice.chatbot.database.dao.implementation;

import com.practice.chatbot.database.entity.Theme;
import com.practice.chatbot.database.dao.ThemeDAO;
import com.practice.chatbot.database.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ThemeDAOImpl implements ThemeDAO {
    @Override
    public List<Theme> allThemes() {
        return (List<Theme>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Theme").list();
    }

    @Override
    public void add(Theme theme) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(theme);
        transaction.commit();
    }

    @Override
    public void delete(Theme theme) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(theme);
        transaction.commit();
    }

    @Override
    public void edit(Theme theme) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(theme);
        transaction.commit();
    }

    @Override
    public Theme findByID(int id) {
        Theme theme = HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Theme.class, id);
        return theme;
    }
}
