package com.example.team7birdsofafeather;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.team7birdsofafeather.models.db.AppDatabase;
import com.example.team7birdsofafeather.models.db.Course;
import com.example.team7birdsofafeather.models.db.RandStudentWithCoursesFactory;
import com.example.team7birdsofafeather.models.db.Student;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class AppDBTest {

    Student s1 = new Student(0, "John", "");
    Student s2 = new Student(1, "Billy", "");
    Course c1 = new Course(0, 0, "CSE12", "Fall", 2021);
    Course c2 = new Course(1, 1, "CSE11", "Summer Session I", 2020);
    Context context;
    AppDatabase db;

    @Before
    public void init() {
        context = ApplicationProvider.getApplicationContext();
        AppDatabase.useTestSingleton(context);
        db = AppDatabase.singleton(context);
    }

    @After
    public void teardown() {
        db.studentDao().deleteAll();
        db.courseDao().deleteAll();
    }

    @Test
    public void testCourseDao() {
        //test insert and getForPerson
        assertEquals(0, db.courseDao().count());
        db.courseDao().insert(c1);
        assertEquals(1, db.courseDao().count());
        db.courseDao().insert(c2);
        assertEquals(c1.toString(), db.courseDao().getForPerson(s1.studentId).get(0).toString());
        assertEquals(c2.toString(), db.courseDao().getForPerson(s2.studentId).get(0).toString());
        assertEquals(2, db.courseDao().count());


        //test delete all
        db.courseDao().deleteAll();
        assertEquals(0, db.courseDao().count());
    }

    @Test
    public void testCourse() {
        //test toString
        assertEquals("CSE12 - Fall 2021", c1.toString());
        assertEquals("CSE11 - Summer Session I 2020", c2.toString());

        //test fromString
        assertArrayEquals(new String[]{"CSE12", "Fall", "2021"}, Course.fromString(c1.toString()));
        assertArrayEquals(new String[]{"CSE11", "Summer Session I", "2020"}, Course.fromString(c2.toString()));
    }

    @Test
    public void testStudentDao() {

        //test insert
        assertEquals(0, db.studentDao().count());
        db.studentDao().insert(s1);
        assertEquals(1, db.studentDao().count());
        db.studentDao().insert(s2);
        assertEquals(2, db.studentDao().count());

        //checkExists
        assertEquals(1, db.studentDao().checkExists("John"));
        assertEquals(1, db.studentDao().checkExists("Billy"));
        assertEquals(0, db.studentDao().checkExists("Nick"));

        //test delete all
        db.studentDao().deleteAll();
        assertEquals(0, db.studentDao().count());
    }

    @Test
    public void randStudentWithCoursesFactoryTest() {
        RandStudentWithCoursesFactory f = new RandStudentWithCoursesFactory(db);
        assertEquals(false, f.exists(new Student(3, "Sara", "")));
        db.studentDao().insert(s1);
        assertEquals(true, f.exists(s1));
    }
}
