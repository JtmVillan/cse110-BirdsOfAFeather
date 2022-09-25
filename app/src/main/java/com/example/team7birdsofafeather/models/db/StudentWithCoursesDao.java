package com.example.team7birdsofafeather.models.db;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface StudentWithCoursesDao {
    //WHERE num_matched_courses > 0 ORDER BY num_matched_courses DESC"
    @Transaction
    @Query("SELECT * FROM students WHERE num_matched_courses IS NOT 0 ORDER BY num_matched_courses DESC")
    List<StudentWithCourses> getAll();

    /*
    @Query("SELECT * FROM students WHERE id =: id")
    StudentWithCourses get(int id);
    */

    @Query("SELECT * FROM students WHERE student_id=:id")
    StudentWithCourses get(int id);

    @Query("SELECT COUNT(*) from courses")
    int count();

    @Query("SELECT MAX(course_id) from courses")
    int maxId();


    @Query("DELETE FROM students")
    void deleteAll();
}
