package com.practice.chatbot.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
    private String content;
    @Column
    private int theme_id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "theme_id",referencedColumnName = "id",insertable=false, updatable=false)
    private Theme theme;
    @OneToMany(mappedBy = "subtheme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;

    public Subtheme(String content, int themeId) {
        this.content = content;
        theme_id = themeId;
    }

    public Subtheme(int id, String content, int theme_id) {
        this.id = id;
        this.content = content;
        this.theme_id = theme_id;
    }
}
