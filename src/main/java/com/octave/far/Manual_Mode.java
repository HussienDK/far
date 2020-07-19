package com.octave.far;

public class Manual_Mode extends Thread {

    public void run(){

        Manual_control manual_control = new Manual_control();
        manual_control.start();

        //OpenKinectStreamTest openKinectStreamTest = new OpenKinectStreamTest();
        //openKinectStreamTest.start();

    }
}