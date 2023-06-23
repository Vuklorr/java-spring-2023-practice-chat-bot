package com.practice.chatbot.database.dao.implementation;

import com.practice.chatbot.database.entity.Answer;
import com.practice.chatbot.database.dao.AnswerDAO;
import com.practice.chatbot.database.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AnswerDAOImpl implements AnswerDAO {
    @Override
    public List<Answer> allAnswer() {
        List answers = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Answer").list();
        HibernateSessionFactoryUtil.getSessionFactory().close();
        return answers;
    }

    @Override
    public void add(Answer answer) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(answer);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Answer answer) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(answer);
        transaction.commit();
        session.close();
    }

    @Override
    public void edit(Answer answer) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(answer);
        transaction.commit();
        session.close();
    }

    @Override
    public Answer findByID(int id) {
        Answer answer = HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Answer.class, id);
        HibernateSessionFactoryUtil.getSessionFactory().close();
        return answer;
    }
}
