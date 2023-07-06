package com.practice.chatbot.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "question")
@Data
@NoArgsConstructor
@AllArgsConstructor
//
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int subthemeID;
    @Column
    private String question;
    @Column
    private int answerID;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="subthemeID",referencedColumnName = "id",insertable=false, updatable=false)
    private Subtheme subtheme;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="answerID",referencedColumnName = "id",insertable=false, updatable=false)
    private Answer answer;
}
