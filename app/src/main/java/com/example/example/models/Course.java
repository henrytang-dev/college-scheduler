package com.example.example.models;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseName;
    private String courseInstructor;
    private String courseLocation;
    private String courseStartTime;
    private String courseEndTime;
    private boolean[] courseDays;
    private String courseColor;

    /**
        * Constructor for the Course class.
     */
    public Course(String courseName, String courseInstructor, String courseLocation, boolean[] courseDays, String courseStartTime, String courseEndTime, String courseColor) {
        this.courseName = courseName;
        this.courseInstructor = courseInstructor;
        this.courseLocation = courseLocation;
        this.courseStartTime = courseStartTime;
        this.courseEndTime = courseEndTime;
        this.courseDays = courseDays;
        this.courseColor = courseColor;
    }

    /**
        * Default constructor for the Course class.
     */
    public Course() {
        this.courseName = "";
        this.courseInstructor = "";
        this.courseLocation = "";
        this.courseStartTime = "";
        this.courseEndTime = "";
        this.courseDays = new boolean[]{};
        this.courseColor = "";
    }

    /**
     * @return the courseName
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * @return the courseInstructor
     */
    public String getCourseInstructor() {
        return courseInstructor;
    }

    /**
     * @return the courseLocation
     */
    public String getCourseLocation() {
        return courseLocation;
    }

    /**
     * @return the courseStartTime
     */
    public String getCourseStartTime() {
        return courseStartTime;
    }

    /**
     * @return the courseEndTime
     */
    public String getCourseEndTime() {
        return courseEndTime;
    }

    /**
     * @return the courseDays
     */
    public boolean[] getCourseDays() {
        return courseDays;
    }

    /**
     * @return the courseColor
     */
    public String getCourseColor() {
        return courseColor;
    }

    /**
     * @param courseName the courseName to set
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * @param courseInstructor the courseInstructor to set
     */
    public void setCourseInstructor(String courseInstructor) {
        this.courseInstructor = courseInstructor;
    }

    /**
     * @param courseLocation the courseLocation to set
     */
    public void setCourseLocation(String courseLocation) {
        this.courseLocation = courseLocation;
    }

    /**
     * @param courseStartTime the courseStartTime to set
     */
    public void setCourseStartTime(String courseStartTime) {
        this.courseStartTime = courseStartTime;
    }

    /**
     * @param courseEndTime the courseEndTime to set
     */
    public void setCourseEndTime(String courseEndTime) {
        this.courseEndTime = courseEndTime;
    }

    /**
     * @param courseDays the courseDays to set
     */
    public void setCourseDays(boolean[] courseDays) {
        this.courseDays = courseDays;
    }

    /**
     * @param courseColor the courseColor to set
     */
    public void setCourseColor(String courseColor) {
        this.courseColor = courseColor;
    }


    /**
        * Load the course list data from the shared preferences.
        * @param context The context of the application.
     */
    public static ArrayList<Course> loadCourseListData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences courses", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("course list", null);
        Type type = new TypeToken<ArrayList<Course>>() {}.getType();
        ArrayList<Course> courseList = gson.fromJson(json, type);

        if(courseList == null) {
            courseList = new ArrayList<>();
        }
        return courseList;
    }

    /**
     * Set up the classNameArray
     * @param courseList
     * @return The course list data.
     */
    public static ArrayList<String> setupClassArray(List<Course> courseList) {
        ArrayList<String> classNameArray =  new ArrayList<>();
        classNameArray.add("Pick a class");
        for(Course course : courseList) {
            classNameArray.add(course.getCourseName());
        }
        return classNameArray;
    }

    /**
     * Get the course from the course name.
     * @param courseName The course name.
     * @param courseList The course list data.
     */
    public static Course getCourseFromName(String courseName, List<Course> courseList) {
        for(Course course : courseList) {
            if(course.getCourseName().equals(courseName)) {
                return course;
            }
        }
        return null;
    }

    /**
     * String representation of the course.
     * @return The string representation of the course.
     */
    public String toString() {
        return "Course Name: " + courseName + "\n" +
                "Course Instructor: " + courseInstructor + "\n" +
                "Course Location: " + courseLocation + "\n" +
                "Course Start Time: " + courseStartTime + "\n" +
                "Course End Time: " + courseEndTime + "\n" +
                "Course Days: " + courseDays + "\n" +
                "Course Color: " + courseColor + "\n";
    }
}
