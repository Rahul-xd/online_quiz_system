package com.quiz.model;

import java.util.List;
import java.util.Objects;

public class Quiz {
    private int id;
    private String name;
    private List<Question> questions;
    private String creatorId;

    // Constructor
    public Quiz(int id, String name, List<Question> questions, String creatorId) {
        this.id = id;
        this.name = name;
        this.questions = questions;
        this.creatorId = creatorId;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }
    // hashCode, equals, and toString methods
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Quiz quiz = (Quiz) obj;
        return id == quiz.id && name.equals(quiz.name);
    }

    @Override
    public String toString() {
        return "Quiz{id=" + id + ", name='" + name + "'}";
    }
}
