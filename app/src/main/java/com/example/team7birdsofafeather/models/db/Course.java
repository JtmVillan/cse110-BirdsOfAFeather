package com.example.team7birdsofafeather.models.db;


import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class Course {

    private static final String TAG = "Course";

    @PrimaryKey
    @ColumnInfo(name = "course_id")
    public int courseId;

    @ColumnInfo(name = "student_id")
    public int studentId;

    @ColumnInfo(name = "code")
    public String courseCode;

    @ColumnInfo(name = "quarter")
    public String quarter;

    @ColumnInfo(name = "year")
    public int year;

    @ColumnInfo(name = "string")
    public String courseString;

    public Course(int courseId, int studentId, String courseCode, String quarter, int year) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.quarter = quarter;
        this.year = year;
        // todo: setup here
        this.courseString = this.courseCode + " - " + this.quarter + " " + this.year;
    }

    @Override
    public String toString() {
        Log.d(TAG, this.courseCode + " - " + this.quarter + " " + this.year);
        return this.courseCode + " - " + this.quarter + " " + this.year;
    }

    public static String[] fromString(String courseString) {
        String[] strs = courseString.split(" - ");
        String code = strs[0];
        String[] term = strs[1].split(" ");


        String year;
        String quarter;
        if (term.length > 2) {
            year = term[3];
            quarter = term[0] + " " + term[1] + " " + term[2];
        } else {
            quarter = term[0];
            year = term[1];
        }

        Log.d(TAG, code + " - " + quarter + " " + year);

        return new String[]{code, quarter, year};
    }
}
