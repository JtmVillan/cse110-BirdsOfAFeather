package com.example.team7birdsofafeather.models.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "students")
public class Student {

    @PrimaryKey
    @ColumnInfo(name = "student_id")
    public int studentId;

    @ColumnInfo(name = "student_name")
    public String studentName;

    @ColumnInfo(name = "num_matched_courses")
    public int numMatchedCourses = 0;


    @ColumnInfo(name = "photo_url")
    public String photoUrl;


    public Student(int studentId, String studentName, String photoUrl) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.photoUrl = photoUrl;
    }
}
