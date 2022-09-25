package com.example.team7birdsofafeather.models.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.team7birdsofafeather.models.IStudent;

import java.util.List;


public class StudentWithCourses implements IStudent {
    @Embedded
    public Student student;

    public StudentWithCourses(Student student, List<String> courses) {
        this.student = student;
        this.courses = courses;
    }

    /*
    @Relation(parentColumn = "student_id",
            entityColumn = "course_id",
            associateBy = @Junction(value = StudentWithCourses.class),
            entity = Course.class)
    public List<Course> courses;
     */

    @Relation(parentColumn = "student_id",
            entityColumn = "student_id",
            entity = Course.class,
            projection = {"string"})
    public List<String> courses;

    //@Override
    //public int getId() {
    //    return student.studentId;
    //}

    @Override
    public String getName() {
        return student.studentName;
    }

    @Override
    public List<String> getCourses() {
        return this.courses;
    }

    @Override
    public int getId() {
        return this.student.studentId;
    }

    /*@Override
    public void setNumMatchedCourses(int n) {student.numMatchedCourses = n; }*/

    @Override
    public int getNumMatchedCourses() {
        return student.numMatchedCourses;
    }
}
