package com.practice.chatbot.database.dao.implementation;

import com.practice.chatbot.database.entity.Answer;
import com.practice.chatbot.database.entity.Question;
import com.practice.chatbot.database.dao.QuestionDAO;
import com.practice.chatbot.database.entity.Theme;
import com.practice.chatbot.database.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

// This is implementation of QuestionDAO interface
public class QuestionDAOImpl implements QuestionDAO {
    @Override
    // List of all question in DB
    public List<Question> allQuestion() {
        return (List<Question>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Question").list();
    }

    @Override
    // Method to add a new question to DB
    public void add(Question question) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(question);
        transaction.commit();
    }

    @Override
    // Method to remove question entity from DB
    public void delete(Question question) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(question);
        transaction.commit();
    }

    @Override
    // Method to edit question entity in DB
    public void edit(Question question) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(question);
        transaction.commit();
    }

    @Override
    // Method that finds a question by his ID
    public Question findByID(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Question.class, id);
    }
    @Override
    // Method that helps to find connected to question answers
    public Answer findAnswerByID(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Answer.class, id);
    }

    @Override
    public String getAnswer(int subthemeID, String content) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        content = content.trim();
        session.beginTransaction();
        String sqlQuery = String.format("select a.content from Question q inner join Answer a on q.answerID = a.id where q.subthemeID=%d and q.question like '%s'",subthemeID,content);
        List list = session.createQuery(sqlQuery).list();
        if (list.isEmpty()){
            return null;
        }
        else return list.get(0).toString();
    }
}
