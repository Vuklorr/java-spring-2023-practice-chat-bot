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
        return answers;
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
        Answer answer = HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Answer.class, id);
        return answer;
    }
}
