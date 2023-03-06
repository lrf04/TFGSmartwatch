package com.example.tfgsmartwatch.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Pivot {

    @SerializedName("course_id")
    @Expose
    private Integer courseId;
    @SerializedName("student_id")
    @Expose
    private Integer studentId;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

}