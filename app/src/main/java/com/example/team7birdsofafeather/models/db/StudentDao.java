package com.example.team7birdsofafeather.models.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface StudentDao {
    @Insert
    void insert(Student student);

    @Query("SELECT COUNT(*) from students")
    int count();

    @Query("DELETE FROM students")
    public void deleteAll();

    @Query("SELECT COUNT() FROM students WHERE student_name=:name")
    int checkExists(String name);

    @Query("SELECT MAX(student_id) from students")
    int maxId();
}
