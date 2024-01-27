package com.example.example;

import com.example.example.schedule_fragments.AssignmentsFragment;

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
}
