package com.example.team7birdsofafeather.models.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface CourseDao {

    @Transaction
    @Query("SELECT * FROM courses where student_id=:studentId")
    List<Course> getForPerson(int studentId);

    @Query("SELECT * FROM courses WHERE code = :courseCode")
    Course get(String courseCode);

    @Query("SELECT COUNT(*) from courses")
    int count();

    @Insert
    void insert(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT MAX(course_id) from courses")
    int maxId();

    /*
    @Transaction
    @Query("SELECT * FROM courses where student_name =:studentName")
    List<Course> getForPerson(String studentName);
    */

    @Query("DELETE FROM courses")
    public void deleteAll();
}
