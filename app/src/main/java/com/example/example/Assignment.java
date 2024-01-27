package com.example.example;

import com.example.example.schedule_fragments.AssignmentsFragment;

public class Assignment {
    private String className;
    private String assignmentTitle;
    private String dueDate;

    public Assignment(String className, String assignmentTitle, String dueDate) {
        this.className = className;
        this.assignmentTitle = assignmentTitle;
        this.dueDate = dueDate;
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
}
