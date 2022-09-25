package com.example.team7birdsofafeather;

import com.example.team7birdsofafeather.models.db.Student;

public interface Observer {
    void update(Student s, String[] courses);
}
