package com.example.team7birdsofafeather.models.db;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RandStudentWithCoursesFactory {
    AppDatabase db;

    private StudentWithCoursesDao studentWithCoursesDao;


    String[] names = {"Nicholas", "Saarthak", "Eric",
            "Jared", "Anukul", "John",
            "George", "Jerry", "Elaine", "Kramer", "Patrick"};

    String[] courseStrings = {"CSE110 - Winter 2022", "CSE100 - Fall 2021",
            "CSE12 - Winter 2021", "CHEM6A - Summer Session 1 2021",
            "CSE21 - Summer Session 2 2021", "DSC10 - Special Summer Session 2021",
            "CSE30 - Spring 2021", "ECON1 - Fall 2021"};

    String[] photos = {"https://images.pexels.com/photos/1108099/pexels-photo-1108099.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"};

    HashMap<String, String[]> studentCourseDict;

    public RandStudentWithCoursesFactory(AppDatabase db) {
        studentCourseDict = new HashMap<>();
        studentCourseDict.put(names[0], new String[]{courseStrings[0], courseStrings[1], courseStrings[3]});
        studentCourseDict.put(names[1], new String[]{courseStrings[0], courseStrings[1], courseStrings[2]});
        studentCourseDict.put(names[2], new String[]{courseStrings[0], courseStrings[3]});
        studentCourseDict.put(names[3], new String[]{courseStrings[0]});
        studentCourseDict.put(names[4], new String[]{courseStrings[0]});
        studentCourseDict.put(names[5], new String[]{courseStrings[0], courseStrings[5], courseStrings[6]});
        studentCourseDict.put(names[6], new String[]{courseStrings[7]});
        studentCourseDict.put(names[7], new String[]{courseStrings[7]});
        studentCourseDict.put(names[8], new String[]{courseStrings[7]});
        studentCourseDict.put(names[9], new String[]{courseStrings[7]});
        studentCourseDict.put(names[10], new String[]{courseStrings[7]});

        this.db = db;
    }

    public List<Student> generateRandomStudents() {
        int randStudentIndex;
        Random r = new Random();
        int upperBound = names.length;
        int randNumStudents = r.nextInt(names.length);

        List<Student> randStudents = new ArrayList<>();

        int idCount = 1;

        for (int i = 0; i < randNumStudents; ++i) {
            randStudentIndex = r.nextInt(upperBound);
            randStudents.add(new Student(db.studentDao().maxId() + idCount, names[randStudentIndex], photos[0]));
            idCount++;
        }

        return randStudents;
    }

    public boolean exists(Student s) {
        Log.d("RandStudentWithCoursesFactory", s.studentName + ": "
                + String.valueOf(db.studentDao().checkExists(s.studentName) == 1));
        return db.studentDao().checkExists(s.studentName) == 1;
    }

    public String[] getCourses(Student s) {
        return studentCourseDict.get(s.studentName);
    }
}
