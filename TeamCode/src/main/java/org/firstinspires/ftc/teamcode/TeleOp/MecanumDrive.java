package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Subsystems.SimpleMecanumDrive;

@TeleOp(name="Simple Drive")
public class MecanumDrive extends LinearOpMode {
    @Override
    public void runOpMode() {
        SimpleMecanumDrive drive = new SimpleMecanumDrive(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            drive.periodicDrive(gamepad1);
        }
    }
}