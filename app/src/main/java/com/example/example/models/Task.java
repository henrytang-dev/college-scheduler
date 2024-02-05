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

    public Task(Course course, String date) {
        this.date = date;
        this.course = course;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

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
    private long daysBetween(Date date, Date today) {
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(date);

        Calendar calendarToday = Calendar.getInstance();
        calendarToday.setTime(today);

        long millisecondsPerDay = 24 * 60 * 60 * 1000; // 24 hours in a day, 60 minutes in an hour, 60 seconds in a minute, 1000 milliseconds in a second

        long diffInMillis = Math.abs(calendarToday.getTimeInMillis() - calendarDate.getTimeInMillis());
        return diffInMillis / millisecondsPerDay;
    }



    public Date convertDate() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/dd/yyyy");
        Date dateObject = simpleDateFormat.parse(date);
        return dateObject;
    }
}