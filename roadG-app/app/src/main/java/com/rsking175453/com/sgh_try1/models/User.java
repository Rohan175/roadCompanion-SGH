package com.rsking175453.com.sgh_try1.models;

public class User {

    private int id;
    private String username, email, type;

    public User(int id, String username, String email, String type) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String gettype() {
        return type;
    }
}