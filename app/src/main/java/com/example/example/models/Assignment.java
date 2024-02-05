package com.example.example.models;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class represents an Assignment entity with attributes such as className, assignmentTitle, dueDate, and color.
 * It provides methods to get and set these attributes, as well as a method to convert the due date to a Date object.
 */
public class Assignment extends Task {
    private String className;
    private String assignmentTitle;

    /**
     * Constructor for the Assignment class.
     *
     * @param className       The name of the class for which the assignment is assigned.
     * @param assignmentTitle The title of the assignment.
     * @param dueDate         The due date of the assignment in the format "MM/dd/yyyy".
     * @param course           The course associated with the assignment.
     */
    public Assignment(String className, String assignmentTitle, String dueDate, Course course) {
        super(course, dueDate);
        this.className = className;
        this.assignmentTitle = assignmentTitle;
        this.course = course;
    }

    /**
     * Get the name of the class associated with the assignment.
     *
     * @return The name of the class.
     */
    public String getClassName() {
        if (course != null) {
            return course.getCourseName();
        }
        return this.className;
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
        return date;
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
        this.date = dueDate;
    }

    /**
     * Get the color associated with the assignment.
     *
     * @return The color of the assignment.
     */
    public String getColor() {
        if (course != null) {
            return course.getCourseColor();
        }
        return "Red";
    }



    @Override
    public String toString() {
        return "Assignment{" +
                "className='" + className + '\'' +
                ", assignmentTitle='" + assignmentTitle + '\'' +
                ", dueDate='" + date + '\'' +
                ", course=" + course +
                '}';
    }
    /**
     * Save the list of assignments to SharedPreferences.
     *
     * @param list The list of assignments to be saved.
     */
    public static void saveData(List<Assignment> list, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("assignment list", json);
        editor.apply();
    }

    /**
     * Load the list of assignments from SharedPreferences.
     */
    public static List<Assignment> loadData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("assignment list", null);
        Type type = new TypeToken<ArrayList<Assignment>>() {}.getType();
        List<Assignment> assignmentList = gson.fromJson(json, type);

        if(assignmentList == null) {
            assignmentList = new ArrayList<>();
        }
        return assignmentList;
    }

}
