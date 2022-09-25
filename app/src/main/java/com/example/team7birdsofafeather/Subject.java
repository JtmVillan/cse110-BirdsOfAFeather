package com.example.team7birdsofafeather;

import com.example.team7birdsofafeather.models.db.Student;

public interface Subject {
    void notifyObservers(Student ns, String[] courses);
}
