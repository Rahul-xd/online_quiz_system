package com.quiz.model;

import java.util.List;
import java.util.Objects;

public class Question {
    private int id;
    private String text;
    private List<String> options;
    private int correctAnswer;

    // Constructor
    public Question(int id, String text, List<String> options, int correctAnswer) {
        this.id = id;
        this.text = text;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    // hashCode, equals, and toString methods
    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Question question = (Question) obj;
        return id == question.id && text.equals(question.text);
    }

    @Override
    public String toString() {
        return "Question{id=" + id + ", text='" + text + "'}";
    }
}
