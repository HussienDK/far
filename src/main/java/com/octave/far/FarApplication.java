package com.octave.far;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FarApplication {

    public static void main(String[] args) {
        SpringApplication.run(FarApplication.class, args);

        System.out.println("Initializing far...");
        Manual_Mode manual_mode = new Manual_Mode();
        manual_mode.start();

        //Autonomous_mode autonomous_mode = new Autonomous_mode();
        //autonomous_mode.start();
    }

}
