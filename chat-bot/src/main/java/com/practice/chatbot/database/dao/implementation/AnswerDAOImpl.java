package com.practice.chatbot.database.dao.implementation;

import com.practice.chatbot.database.entity.Answer;
import com.practice.chatbot.database.dao.AnswerDAO;
import com.practice.chatbot.database.entity.Theme;
import com.practice.chatbot.database.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AnswerDAOImpl implements AnswerDAO {
    @Override
    public List<Answer> allAnswer() {
        return (List<Answer>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Answer").list();

    }

    @Override
    public void add(Answer answer) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(answer);
        transaction.commit();
    }

    @Override
    public void delete(Answer answer) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(answer);
        transaction.commit();
    }

    @Override
    public void edit(Answer answer) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(answer);
        transaction.commit();
    }

    @Override
    public Answer findByID(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Answer.class, id);
    }
}
