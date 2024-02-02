package com.example.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents an Exam entity with attributes such as examName, location, date, time, and color.
 * It provides methods to get and set these attributes, as well as a method to convert the date and time to a Date object.
 */
public class Exam {
    private String examName;
    private String location;
    private String date;
    private String time;
    private String color;

    /**
     * Constructor for the Exam class.
     *
     * @param examName  The name of the exam.
     * @param location  The location where the exam takes place.
     * @param date      The date of the exam in the format "MM/dd/yyyy".
     * @param time      The time of the exam in the format "HH:mm".
     * @param color     The color associated with the exam for display purposes.
     */
    public Exam(String examName, String location, String date, String time, String color) {
        this.examName = examName;
        this.location = location;
        this.date = date;
        this.time = time;
        this.color = color;
    }

    /**
     * Get the color associated with the exam.
     *
     * @return The color of the exam.
     */
    public String getColor() {
        return color;
    }

    /**
     * Set the color associated with the exam.
     *
     * @param color The new color for the exam.
     */
    public void setColor(String color) {
        this.color = color;
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
    public Date convertDate() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/dd/yyyy HH:mm");
        String dateTimeString = date + " " + time;
        Date dateObject = simpleDateFormat.parse(dateTimeString);
        return dateObject;
    }
}
