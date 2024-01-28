package com.example.example;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Assignment {
    private String className;
    private String assignmentTitle;
    private String dueDate;

    private String color;


    public Assignment(String className, String assignmentTitle, String dueDate, String color) {
        this.className = className;
        this.assignmentTitle = assignmentTitle;
        this.dueDate = dueDate;
        this.color = color;
    }

    public String getClassName() {
        return className;
    }

    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setAssignmentTitle(String assignmentTitle) {
        this.assignmentTitle = assignmentTitle;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date convertDueDate() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/dd/yyyy");
        Date date = simpleDateFormat.parse(dueDate);
        return date;
    }

}
