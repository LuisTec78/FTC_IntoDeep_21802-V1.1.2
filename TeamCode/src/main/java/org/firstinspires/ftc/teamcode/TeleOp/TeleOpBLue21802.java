package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.AdvancedMecanumSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ClawSubsystem2;
import org.firstinspires.ftc.teamcode.Subsystems.OdometryMecanumSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.SimpleMecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.ViperSubsystem2;

@TeleOp
public class TeleOpBLue21802 extends LinearOpMode {
    @Override
    public void runOpMode(){
        SimpleMecanumDrive drive = new SimpleMecanumDrive(hardwareMap);
        ViperSubsystem2 viper = new ViperSubsystem2(hardwareMap,0.001,0,0.03,0.01,0,0);
        ClawSubsystem2 claw = new ClawSubsystem2(hardwareMap, gamepad2);

        waitForStart();
        while (opModeIsActive()){
            drive.periodicDrive(gamepad1.right_stick_x, -gamepad1.right_stick_y, gamepad1.left_stick_x,gamepad1.a, gamepad1.left_bumper, gamepad1.right_bumper);

            viper.periodic(gamepad2);
            claw.periodic(gamepad2);
        }
    }
}
