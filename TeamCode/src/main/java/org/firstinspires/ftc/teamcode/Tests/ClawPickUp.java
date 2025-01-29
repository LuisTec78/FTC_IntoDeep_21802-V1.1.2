package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.ClawSubsystem2;

@TeleOp
public class ClawPickUp extends LinearOpMode {
    @Override
    public void runOpMode(){
        ClawSubsystem2 claw = new ClawSubsystem2(hardwareMap, gamepad2);

        waitForStart();
        while (opModeIsActive()){
            claw.PkUp(180);
        }
    }
}
