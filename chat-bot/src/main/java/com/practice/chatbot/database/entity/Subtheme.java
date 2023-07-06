package com.practice.chatbot.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Table(name ="subtheme")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subtheme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int content;
    @Column
    private int themeID;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private Theme theme;
}
