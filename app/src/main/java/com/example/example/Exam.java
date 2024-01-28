package com.example.example;

public class Exam {
    private String examName;
    private String location;
    private String date;
    private String time;
    private String color;

    public Exam(String examName, String location, String date, String time, String color) {
        this.examName = examName;
        this.location = location;
        this.date = date;
        this.time = time;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
