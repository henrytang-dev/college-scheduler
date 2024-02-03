package com.example.example.models;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class represents an Exam entity with attributes such as examName, location, date, time, and color.
 * It provides methods to get and set these attributes, as well as a method to convert the date and time to a Date object.
 */
public class Exam extends Task {
    private String examName;
    private String location;
    private String time;

    /**
     * Constructor for the Exam class.
     *
     * @param examName  The name of the exam.
     * @param location  The location where the exam takes place.
     * @param date      The date of the exam in the format "MM/dd/yyyy".
     * @param time      The time of the exam in the format "HH:mm".
     * @param course     The course associated with the exam for display purposes.
     */
    public Exam(String examName, String location, String date, String time, Course course) {
        super(course, date);
        this.examName = examName;
        this.location = location;
        this.time = time;
    }

    /**
     * Get the color associated with the exam.
     *
     * @return The color of the exam.
     */
    public String getColor() {
        return course.getCourseColor();
    }

    /**
     * Get the name of the exam.
     *
     * @return The name of the exam.
     */
    public String getExamName() {
        return examName;
    }

    /**
     * Set the name of the exam.
     *
     * @param examName The new name for the exam.
     */
    public void setExamName(String examName) {
        this.examName = examName;
    }

    /**
     * Get the location where the exam takes place.
     *
     * @return The location of the exam.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Set the location where the exam takes place.
     *
     * @param location The new location for the exam.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Get the date of the exam in the format "MM/dd/yyyy".
     *
     * @return The date of the exam.
     */
    public String getDate() {
        return date;
    }

    /**
     * Set the date of the exam.
     *
     * @param date The new date for the exam in the format "MM/dd/yyyy".
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Get the time of the exam in the format "HH:mm".
     *
     * @return The time of the exam.
     */
    public String getTime() {
        return time;
    }

    /**
     * Set the time of the exam.
     *
     * @param time The new time for the exam in the format "HH:mm".
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Convert the date and time to a Date object.
     *
     * @return The Date object representing the combined date and time of the exam.
     * @throws ParseException If there is an error parsing the date and time strings.
     */

    @Override
    public Date convertDate() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/dd/yyyy HH:mm");
        String dateTimeString = date + " " + time;
        Date dateObject = simpleDateFormat.parse(dateTimeString);
        return dateObject;
    }

    /**
     * Save the list of exams to SharedPreferences.
     *
     * @param list The list of exams to be saved.
     */
    public static void saveData(List<Exam> list, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("exam list", json);
        editor.apply();
    }

    /**
     * Load the list of exams from SharedPreferences.
     */
    public static List<Exam> loadData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("exam list", null);
        Type type = new TypeToken<ArrayList<Exam>>() {}.getType();
        List<Exam> examList = gson.fromJson(json, type);

        if(examList == null) {
            examList = new ArrayList<>();
        }
        return examList;
    }
}
