package org.firstinspires.ftc.teamcode.Tests;

import android.provider.SearchRecentSuggestions;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Subsystems.ClawSubsystem2;

@Autonomous
public class Claw0 extends LinearOpMode {
    @Override
    public void runOpMode(){
        ClawSubsystem2 claw = new ClawSubsystem2(hardwareMap, gamepad2);


        waitForStart();
        while (opModeIsActive()) {
            claw.moveWristToDegrees(0);
            claw.moveArmToDegrees(0);
            claw.moveCLawToDegrees(0);
        }
    }
}