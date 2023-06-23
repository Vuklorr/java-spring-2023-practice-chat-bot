package com.practice.chatbot.database.dao.implementation;

import com.practice.chatbot.database.entity.Answer;
import com.practice.chatbot.database.entity.Question;
import com.practice.chatbot.database.dao.QuestionDAO;
import com.practice.chatbot.database.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

// This is implementation of QuestionDAO interface
public class QuestionDAOImpl implements QuestionDAO {
    @Override
    // List of all question in DB
    public List allQuestion() {
        List questions = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Answer").list();
        HibernateSessionFactoryUtil.getSessionFactory().close();
        return questions;
    }

    @Override
    // Method to add a new question to DB
    public void add(Question question) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(question);
        transaction.commit();
        session.close();
    }

    @Override
    // Method to remove question entity from DB
    public void delete(Question question) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(question);
        transaction.commit();
        session.close();
    }

    @Override
    // Method to edit question entity in DB
    public void edit(Question question) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(question);
        transaction.commit();
        session.close();
    }

    @Override
    // Method that finds a question by his ID
    public Question findByID(int id) {
        Question question = HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Question.class, id);
        HibernateSessionFactoryUtil.getSessionFactory().close();
        return question;
    }
    @Override
    // Method that helps to find connected to question answers
    public Answer findAnswerByID(int id) {
        Answer answer = HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Answer.class, id);
        HibernateSessionFactoryUtil.getSessionFactory().close();
        return answer;
    }
}
