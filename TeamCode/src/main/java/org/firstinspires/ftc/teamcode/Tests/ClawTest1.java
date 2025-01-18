package org.firstinspires.ftc.teamcode.Tests;

import android.provider.SearchRecentSuggestions;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ClawSubsystem2;

@Autonomous
public class ClawTest1 extends LinearOpMode {
    @Override
    public void runOpMode(){
        ClawSubsystem2 claw = new ClawSubsystem2(hardwareMap, gamepad2);



        waitForStart();
        while (opModeIsActive()) {
                arm.setDirection(Servo.Direction.FORWARD);
                telemetry.addData("Grados", arm.getPosition());
                telemetry.update();
        }
    }
}
