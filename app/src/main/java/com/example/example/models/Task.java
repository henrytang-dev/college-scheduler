package com.example.example.models;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by enyason on 5/28/18.
 */

public class Task {

    private int priority;
    protected Course course;
    private String name;
    protected String date;

    /**
     * Constructor for the Task class.
     * @param course The course associated with the task.
     * @param date The date of the task in the format "MM/dd/yyyy".
     */
    public Task(Course course, String date) {
        this.date = date;
        this.course = course;
    }

    /**
     * Get the date of the task.
     * @return The date of the task.
     */
    public String getDate() {
        return date;
    }

    /**
     * Set the date of the task.
     * @param date The date of the task.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Get the name of the task.
     * @return The name of the task.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the task.
     * @param name The name of the task.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the course associated with the task.
     * @return the course
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Set the course associated with the task.
     * @param course the course to set
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * Get the priority of the task.
     * @return the priority
     * @throws ParseException If the date cannot be parsed.
     */
    public int getPriority() throws ParseException {
        Date dateObject = convertDate();
        Date today = new Date();

        long daysDifference = daysBetween(dateObject, today);
        if(daysDifference < 2) {
            return 1;
        } else if(daysDifference < 5) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * @param date The date of the task.
     * @param today The current date.
     * @return The number of days between the date and today.
     */
    private long daysBetween(Date date, Date today) {
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(date);

        Calendar calendarToday = Calendar.getInstance();
        calendarToday.setTime(today);

        long millisecondsPerDay = 24 * 60 * 60 * 1000; // 24 hours in a day, 60 minutes in an hour, 60 seconds in a minute, 1000 milliseconds in a second

        long diffInMillis = Math.abs(calendarToday.getTimeInMillis() - calendarDate.getTimeInMillis());
        return diffInMillis / millisecondsPerDay;
    }


    /**
     * Convert the date to a Date object.
     * @return The Date object representing the date of the task.
     * @throws ParseException If there is an error parsing the date string.
     */
    public Date convertDate() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/dd/yyyy");
        Date dateObject = simpleDateFormat.parse(date);
        return dateObject;
    }
}