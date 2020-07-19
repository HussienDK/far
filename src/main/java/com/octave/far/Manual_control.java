package com.octave.far;

import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.SoftPwm;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Manual_control extends Thread {

    private static int l1 = 0;
    private static int l2 = 1;
    private static int r1 = 2;
    private static int r2 = 7;
    private static int a1 = 15;
    private static int c1 = 16;
    private static int level  = 2;

    private void navigation() throws InterruptedException, IOException {

        Gpio.wiringPiSetup();
        SoftPwm.softPwmCreate(l1, 0, 100);
        SoftPwm.softPwmCreate(l2, 0, 100);
        SoftPwm.softPwmCreate(r1, 0, 100);
        SoftPwm.softPwmCreate(r2, 0, 100);
        SoftPwm.softPwmCreate(a1, 0, 100);
        SoftPwm.softPwmCreate(c1, 0, 100);

        System.out.println("waiting for commands...");

            //create a socket to listen at port 1234
            DatagramSocket ds = new DatagramSocket(1234);
            byte[] receive = new byte[65535];

            DatagramPacket DpReceive = null;
            while (true)
            {
                //create a DatgramPacket to receive the data.
                DpReceive = new DatagramPacket(receive, receive.length);
                ds.receive(DpReceive);

                String Command = data(receive);
                System.out.println("Command: " + Command);

                if(Command.equals("forward")){

                    SoftPwm.softPwmWrite(l1, 20);
                    SoftPwm.softPwmWrite(l2, 20);
                    SoftPwm.softPwmWrite(r1, 10);
                    SoftPwm.softPwmWrite(r2, 10);
                }

                if(Command.equals("backward")){

                    SoftPwm.softPwmWrite(l1, 10);
                    SoftPwm.softPwmWrite(l2, 10);
                    SoftPwm.softPwmWrite(r1, 20);
                    SoftPwm.softPwmWrite(r2, 20);
                }

                if(Command.equals("left")){

                    SoftPwm.softPwmWrite(l1, 10);
                    SoftPwm.softPwmWrite(l2, 10);
                    SoftPwm.softPwmWrite(r1, 10);
                    SoftPwm.softPwmWrite(r2, 10);
                }

                if(Command.equals("right")){

                    SoftPwm.softPwmWrite(l1, 20);
                    SoftPwm.softPwmWrite(l2, 20);
                    SoftPwm.softPwmWrite(r1, 20);
                    SoftPwm.softPwmWrite(r2, 20);
                }

                if(Command.equals("clampon")){

                    SoftPwm.softPwmWrite(c1, 10);
                }

                if(Command.equals("clampoff")){

                    SoftPwm.softPwmWrite(c1,0);
                    SoftPwm.softPwmWrite(c1, 20);
                    sleep(2000);
                    SoftPwm.softPwmWrite(c1,0);
                }

                if(Command.equals("level1")){

                    if(level==2){
                        armDown();
                    }

                    level = 1;
                }

                if(Command.equals("level2")){

                    if(level==1){
                        armUp();
                    }

                    level = 2;

                }

                if(Command.equals("stop")){

                    SoftPwm.softPwmWrite(l1,0);
                    SoftPwm.softPwmWrite(l2,0);
                    SoftPwm.softPwmWrite(r1,0);
                    SoftPwm.softPwmWrite(r2,0);
                }

                // Clear the buffer after every message.
                receive = new byte[65535];
            }
    }

    private void armUp() throws InterruptedException {

        SoftPwm.softPwmWrite(a1, 10);
        Thread.sleep(750);
        SoftPwm.softPwmWrite(a1,0);
    }

    private void armDown() throws InterruptedException {

        SoftPwm.softPwmWrite(a1, 20);
        Thread.sleep(750);
        SoftPwm.softPwmWrite(a1,0);
    }

    private static String data(byte[] a)
    {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0)
        {
            ret.append((char) a[i]);
            i++;
        }
        return ret.toString();
    }

    public void run(){

        try {
            navigation();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }
}