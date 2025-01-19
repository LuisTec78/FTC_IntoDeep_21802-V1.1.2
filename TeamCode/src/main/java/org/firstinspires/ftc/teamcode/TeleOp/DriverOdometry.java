package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.AdvancedMecanumSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ClawSubsystem2;
import org.firstinspires.ftc.teamcode.Subsystems.OdometryMecanumSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ViperSubsystem2;

@TeleOp
public class DriverOdometry extends LinearOpMode {
    @Override
    public void runOpMode() {
        OdometryMecanumSubsystem drive = new OdometryMecanumSubsystem(hardwareMap, telemetry, gamepad1);

        waitForStart();
        while (opModeIsActive()) {
            drive.teleopPeriodic(drive.BLue);
            if (gamepad1.a) {
                drive.rotateDeg(180);
            } else if (gamepad1.b) {
                drive.rotateAngle(45);
            }
        }
    }
}
