package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.ClawSubsystem2;
import org.firstinspires.ftc.teamcode.Subsystems.ViperSubsystem2;

@Autonomous
public class ClawTest1 extends LinearOpMode {
    @Override
    public void runOpMode(){
        ClawSubsystem2 claw = new ClawSubsystem2(hardwareMap, gamepad2);
        ViperSubsystem2 viper = new ViperSubsystem2(hardwareMap, 0.001, 0, 0.01, 0.01, 0 ,0);


        waitForStart();
        while (opModeIsActive()) {
                claw.stabilizeArm(90, viper.getCurrentAngle());
                claw.openCLaw();
                claw.moveWristToDegrees(claw.MIN_WRIST_DEGREES);
        }
    }
}
