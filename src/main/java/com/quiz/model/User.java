package com.quiz.model;
import java.util.Objects;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String userId;
    private String password;

    // Constructor
    public User(int id, String firstName, String lastName, String email, String userId, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userId = userId;
        this.password = password;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }    // hashCode, equals, and toString methods
    @Override
    public int hashCode() {
        return Objects.hash(id, userId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return id == user.id && userId.equals(user.userId);
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", userId='" + userId + "'}";
    }
}

