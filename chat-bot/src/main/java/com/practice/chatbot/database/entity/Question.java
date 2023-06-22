package com.practice.chatbot.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
//
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int themeID;
    @Column
    private String question;
    @Column
    private int answerID;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "themeID")
    private Theme theme;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answerID")
    private Answer answer;
}
