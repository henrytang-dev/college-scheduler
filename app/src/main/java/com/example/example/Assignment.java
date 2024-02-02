package com.example.example;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents an Assignment entity with attributes such as className, assignmentTitle, dueDate, and color.
 * It provides methods to get and set these attributes, as well as a method to convert the due date to a Date object.
 */
public class Assignment {
    private String className;
    private String assignmentTitle;
    private String dueDate;

    private String color;

    /**
     * Constructor for the Assignment class.
     *
     * @param className       The name of the class for which the assignment is assigned.
     * @param assignmentTitle The title of the assignment.
     * @param dueDate         The due date of the assignment in the format "MM/dd/yyyy".
     * @param color           The color associated with the assignment for display purposes.
     */
    public Assignment(String className, String assignmentTitle, String dueDate, String color) {
        this.className = className;
        this.assignmentTitle = assignmentTitle;
        this.dueDate = dueDate;
        this.color = color;
    }

    /**
     * Get the name of the class associated with the assignment.
     *
     * @return The name of the class.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Get the title of the assignment.
     *
     * @return The title of the assignment.
     */
    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    /**
     * Get the due date of the assignment in the format "MM/dd/yyyy".
     *
     * @return The due date of the assignment.
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * Set the name of the class associated with the assignment.
     *
     * @param className The new name of the class.
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Set the title of the assignment.
     *
     * @param assignmentTitle The new title of the assignment.
     */
    public void setAssignmentTitle(String assignmentTitle) {
        this.assignmentTitle = assignmentTitle;
    }

    /**
     * Set the due date of the assignment.
     *
     * @param dueDate The new due date of the assignment in the format "MM/dd/yyyy".
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Get the color associated with the assignment.
     *
     * @return The color of the assignment.
     */
    public String getColor() {
        return color;
    }

    /**
     * Set the color associated with the assignment.
     *
     * @param color The new color for the assignment.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Convert the due date to a Date object.
     *
     * @return The Date object representing the due date of the assignment.
     * @throws ParseException If there is an error parsing the due date string.
     */
    public Date convertDueDate() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/dd/yyyy");
        Date date = simpleDateFormat.parse(dueDate);
        return date;
    }

}
