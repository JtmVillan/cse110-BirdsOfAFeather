package com.example.team7birdsofafeather;


import com.example.team7birdsofafeather.models.db.AppDatabase;
import com.example.team7birdsofafeather.models.db.RandStudentWithCoursesFactory;
import com.example.team7birdsofafeather.models.db.Student;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class FakedMessageListener extends MessageListener implements Subject {
    private final MessageListener messageListener;
    //private final ScheduledExecutorService executor;
    private RandStudentWithCoursesFactory f;
    protected AppDatabase db;
    private Observer o;
    private int frequency;
    private String messageStr;
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> bluetoothHandle;


    private static final String TAG = "BirdsOfAFeather";

    public FakedMessageListener(MessageListener realMessageListener, int frequency,
                                String messageStr, AppDatabase db, Observer o) {

        this.db = db;
        this.messageListener = realMessageListener;
        new RandStudentWithCoursesFactory(this.db);
        this.o = o;
        this.messageStr = messageStr;
        this.frequency = frequency;

        bluetoothHandle = this.scheduler.scheduleAtFixedRate(() -> {
            Message message = new Message(messageStr.getBytes(StandardCharsets.UTF_8));
            messageListener.onFound(message);
            messageListener.onLost(message);
        }, 5, frequency, TimeUnit.SECONDS);
        scheduler.shutdown();
    }

    public void notifyObservers(Student ns, String[] courses) {
        o.update(ns, courses);
    }


    public void flip(boolean started) {
        if (started) {

            scheduler.shutdown();
        } else {
            scheduler = Executors.newScheduledThreadPool(1);
            bluetoothHandle = this.scheduler.scheduleAtFixedRate(() -> {
                Message message = new Message(messageStr.getBytes(StandardCharsets.UTF_8));
                messageListener.onFound(message);
                messageListener.onLost(message);
            }, 0, frequency, TimeUnit.SECONDS);
        }
    }


}
