package com.example.example.models;

/**
 * Created by enyason on 5/28/18.
 */

public class Task {

    private int id;
    private String name;
    private int priority;
    private String updatedAt;



    public Task(String name, int priority, String updatedAt) {
        this.name = name;
        this.priority = priority;
        this.updatedAt = updatedAt;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}