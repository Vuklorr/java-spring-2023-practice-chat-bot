package com.practice.chatbot.database.dao.implementation;

import com.practice.chatbot.database.dao.SubthemeDAO;
import com.practice.chatbot.database.entity.Question;
import com.practice.chatbot.database.entity.Subtheme;
import com.practice.chatbot.database.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
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
}
