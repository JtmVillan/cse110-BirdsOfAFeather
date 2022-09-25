package com.example.team7birdsofafeather.models;


import java.util.List;

public interface IStudent {
    String getName();

    List<String> getCourses();

    int getId();

    int getNumMatchedCourses();
    //void setNumMatchedCourses(int n);
}
