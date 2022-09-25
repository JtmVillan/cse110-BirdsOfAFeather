package com.example.team7birdsofafeather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team7birdsofafeather.models.IStudent;
import com.example.team7birdsofafeather.models.db.AppDatabase;
import com.example.team7birdsofafeather.models.db.Course;
import com.example.team7birdsofafeather.models.db.RandStudentWithCoursesFactory;
import com.example.team7birdsofafeather.models.db.Student;
import com.example.team7birdsofafeather.models.db.StudentWithCourses;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements Observer {
    protected RecyclerView studentsRecyclerView;
    protected RecyclerView.LayoutManager studentsLayoutManager;
    protected BoFListViewAdapter bofListViewAdapter;
    protected AppDatabase db;
    protected boolean started = false;
    protected Button startButton;

    //protected Course cse12_0 = new Course(0, 0, "CSE 12", "Fall",2020);
    //protected Course cse12_1 = new Course(1, 1, "CSE 12", "Fall",2020);
    //protected Course cse12_2 = new Course(2, 2,"CSE 12", "Winter",2020);

    //protected IStudent[] data = {new DummyStudent(0, "John", new String[] {cse12_0.toString()}),
    //        new DummyStudent(1, "Mary",  new String[] {cse12_1.toString()}),
    //        new DummyStudent(2, "Sam ",   new String[] {cse12_2.toString()})};


    private static final String TAG = "MainActivity";
    private MessageListener messageListener;
    private FakedMessageListener ml;

    private RandStudentWithCoursesFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences preferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        if (!preferences.contains("numCourses")) {
            Intent intent = new Intent(getApplicationContext(), EnterPastCoursesActivity.class);
            startActivity(intent);
        } else {
            Map<String, ?> allEntries = preferences.getAll();
            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
            }


        }
        setContentView(R.layout.activity_main);
        setTitle("Birds of a Feather");
        startButton = findViewById(R.id.bluetooth_button);

        db = AppDatabase.singleton(getApplicationContext());
        db.studentDao().deleteAll();
        db.courseDao().deleteAll();
        List<? extends IStudent> students = db.studentWithCoursesDao().getAll();

        factory = new RandStudentWithCoursesFactory(db);


        //System.out.println("\t\t\t DB STUDENTS: " + Integer.toString(db.studentDao().count()));
        //System.out.println("\t\t\t DB COURSES: " + Integer.toString(db.courseDao().count()));


        // set up the recycler view to show our database contents
        studentsRecyclerView = findViewById(R.id.students_view);

        studentsLayoutManager = new LinearLayoutManager(this);
        studentsRecyclerView.setLayoutManager(studentsLayoutManager);

        bofListViewAdapter = new BoFListViewAdapter((List<IStudent>) students);
        studentsRecyclerView.setAdapter(bofListViewAdapter);


        MessageListener realListener = new MessageListener() {

            @Override
            public void onFound(@NonNull Message message) {
                Log.d(TAG, "Found message: " + new String(message.getContent()));

                List<Student> newStudents = factory.generateRandomStudents();

                for (int i = 0; i < newStudents.size(); i++) {
                    if (factory.exists(newStudents.get(i))) {
                        Log.d(TAG, newStudents.get(i).studentName + " connection already exists!.");
                    } else {
                        Log.d(TAG, newStudents.get(i).studentName + " connection was found!");

                        int finalI = i;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,
                                        "New Student Found: " + newStudents.get(finalI).studentName,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });


                        update(newStudents.get(i), factory.getCourses(newStudents.get(i)));

                    }
                }
            }

            @Override
            public void onLost(@NonNull Message message) {
                Log.d(TAG, "Lost sight of message: " + new String(message.getContent()));
            }
        };

        this.ml = new FakedMessageListener(realListener, 5,
                "Found Student!", db, this);
        this.messageListener = ml;


    }

    public void resetInfo(View view) {
        SharedPreferences preferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.clear();
        editor.apply();

        Intent intent = new Intent(getApplicationContext(), EnterPastCoursesActivity.class);
        startActivity(intent);

    }


    /*
    @Override
    protected void onStart() {
        super.onStart();
        Nearby.getMessagesClient(this).subscribe(messageListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Nearby.getMessagesClient(this).unsubscribe(messageListener);
    }

     */


    public void update(Student s, String[] courses) {
        //Update db -- insert new student and their courses
        //insert student into db
        Set<String> userCourses = getUserCourses();
        int numMatchedCourses = 0;
        for (String course : courses) {
            if (userCourses.contains(course)) numMatchedCourses++;
        }

        s.numMatchedCourses = numMatchedCourses;
        db.studentDao().insert(s);
        //insert courses into db
        for (int i = 0; i < courses.length; ++i) {
            Course c = new Course(db.courseDao().maxId() + 1,
                    s.studentId, Course.fromString(courses[i])[0],
                    Course.fromString(courses[i])[1], Integer.parseInt(Course.fromString(courses[i])[2]));

            db.courseDao().insert(c);
        }

        //List<StudentWithCourses> newStudents= db.studentWithCoursesDao().getAll();
        //bofListViewAdapter.addStudent(newStudents);

    }

    public void onRefreshClicked(View view) {
        List<StudentWithCourses> newStudents = db.studentWithCoursesDao().getAll();
        bofListViewAdapter.addStudent(newStudents);
        /*
        List<Student> studentSequence = factory.generateRandomStudents();



        for(int i = 0; i < studentSequence.size(); ++i) {
            //check if exists in db
            if(factory.exists(studentSequence.get(i))) {
                Log.d(TAG, studentSequence.get(i).studentName + " connection already exists!.");
            } else {
                Log.d(TAG, studentSequence.get(i).studentName + " connection was found!");

                //insert into db
                update(studentSequence.get(i), factory.getCourses(studentSequence.get(i)));
            }
        }
         */
    }

    public Set<String> getUserCourses() {
        SharedPreferences preferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        Map<String, ?> allEntries = preferences.getAll();
        Set<String> userCourses = new HashSet<>();

        for (int i = 0; i < Integer.parseInt(preferences.getString("numCourses", "0")); i++) {
            userCourses.add(allEntries.get(String.valueOf(i)).toString());
        }
        return userCourses;
    }


    public void startStop(View view) {
        if (started) {
            startButton.setText("START");
            Toast.makeText(this, "Search stopped", Toast.LENGTH_SHORT).show();
            ml.flip(started);
            started = false;

        } else {
            startButton.setText("STOP");
            Toast.makeText(this, "Search started", Toast.LENGTH_SHORT).show();
            ml.flip(started);
            started = true;

        }
    }
}