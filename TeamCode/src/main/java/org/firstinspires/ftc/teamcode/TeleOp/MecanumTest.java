package org.firstinspires.ftc.teamcode.TeleOp;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.SimpleMecanumDrive;

@TeleOp
public class MecanumTest extends LinearOpMode {
    @Override
    public void runOpMode(){
        SimpleMecanumDrive drive = new SimpleMecanumDrive(hardwareMap);

        waitForStart();
        while (opModeIsActive()){
            drive.test(gamepad1);
        }

    }
}
