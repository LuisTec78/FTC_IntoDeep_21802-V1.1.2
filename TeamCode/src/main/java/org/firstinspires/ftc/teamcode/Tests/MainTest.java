package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.VipersSubsystem;

@TeleOp
public class MainTest extends LinearOpMode {
    @Override
    public void runOpMode(){
        VipersSubsystem viper = new VipersSubsystem(hardwareMap, 0.01, 0, 0);
        ClawSubsystem claw = new ClawSubsystem(hardwareMap,gamepad2, 0.01,0,0);

        waitForStart();
        while (opModeIsActive()){

        }
    }
}
