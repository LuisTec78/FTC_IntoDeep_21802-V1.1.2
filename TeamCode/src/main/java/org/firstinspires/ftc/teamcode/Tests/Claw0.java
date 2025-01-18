package org.firstinspires.ftc.teamcode.Tests;

import android.provider.SearchRecentSuggestions;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class Claw0 extends LinearOpMode {
    @Override
    public void runOpMode(){
        Servo arm = hardwareMap.get(Servo.class, "arm");


        waitForStart();
        while (opModeIsActive()) {
            arm.setPosition(0);
        }
    }
}