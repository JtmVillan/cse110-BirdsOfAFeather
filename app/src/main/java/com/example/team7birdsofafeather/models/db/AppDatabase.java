package com.example.team7birdsofafeather.models.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Student.class, Course.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase singletonInstance;

    public static AppDatabase singleton(Context context) {
        if (singletonInstance == null) {
            singletonInstance = Room.databaseBuilder(context, AppDatabase.class, "persons.db")
                    .allowMainThreadQueries()
                    .build();
        }

        return singletonInstance;
    }

    public static void useTestSingleton(Context context) {
        singletonInstance = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    public abstract StudentWithCoursesDao studentWithCoursesDao();

    public abstract CourseDao courseDao();

    public abstract StudentDao studentDao();

    //public abstract StudentCourseEnrollmentDao studentCourseEnrollmentDao();
}
