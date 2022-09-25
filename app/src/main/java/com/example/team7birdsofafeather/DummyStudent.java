package com.example.team7birdsofafeather;

import com.example.team7birdsofafeather.models.IStudent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyStudent implements IStudent {
    private final int id;
    private final String first;
    private final List<String> classes;
    private int numMatchedCourses;

    public DummyStudent(int id, String first, String[] classes) {
        this.id = id;
        this.first = first;

        this.classes = Arrays.asList(classes);
    }

    // public DummyStudent(String name) {
    //   this.name = name;
    //}

        /*public DummyStudent() {
                this.name = "Billy G";
                //this.classes =
        }*/

    //public void addClasses(ArrayList<String> classes) { this.classes.addAll(); }

    @Override
    public String getName() {
        return this.first;
    }

    @Override
    public List<String> getCourses() {
        return classes;
    }

    public static DummyStudent fromString(String s) {
        String[] details = s.split(",");
        String name = details[0];

        ArrayList<String> courses = new ArrayList<String>();
        courses.addAll(Arrays.asList(details).subList(1, details.length));


        //
        //FIX ID HERE
        //
        return new DummyStudent(0, name, courses.toArray(new String[0]));
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getNumMatchedCourses() {
        return numMatchedCourses;
    }



        /*@Override
        public void setNumMatchedCourses(int n) {numMatchedCourses = n;}*/
}
