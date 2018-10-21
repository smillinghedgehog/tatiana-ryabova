package com.example.tanya.tatianaryabova;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;

public class Ex4Activity extends AppCompatActivity {

    private boolean isRunning = true;
    private boolean isLeft = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex4);
    }

    @Override
    public void onStart() {
        super.onStart();
        Thread left = new Thread(new LeftLeg());
        Thread right = new Thread(new RightLeg());

        left.start();
        right.start();
    }

    @Override
    public void onStop(){
        super.onStop();
        isRunning = false;
    }

    private class LeftLeg implements Runnable {

        @Override
        public void run() {
            while (isRunning) {
                if (isLeft) {
                    leftStep();
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }

    private class RightLeg implements Runnable {
        @Override
        public void run() {
            while (isRunning){
                if (!isLeft) {
                    rightStep();
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }

    public synchronized void leftStep() {
        System.out.println("Left step");
        isLeft = false;
    }

    public synchronized void rightStep() {
        System.out.println("Right step");
        isLeft = true;
    }
}
