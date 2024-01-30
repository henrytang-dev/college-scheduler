package com.example.example;

public class Course {
    private String courseName;
    private String courseInstructor;
    private String courseLocation;
    private String courseStartTime;
    private String courseEndTime;
    private boolean[] courseDays;
    private String courseColor;

    public Course(String courseName, String courseInstructor, String courseLocation, boolean[] courseDays, String courseStartTime, String courseEndTime, String courseColor) {
        this.courseName = courseName;
        this.courseInstructor = courseInstructor;
        this.courseLocation = courseLocation;
        this.courseStartTime = courseStartTime;
        this.courseEndTime = courseEndTime;
        this.courseDays = courseDays;
        this.courseColor = courseColor;
    }

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
