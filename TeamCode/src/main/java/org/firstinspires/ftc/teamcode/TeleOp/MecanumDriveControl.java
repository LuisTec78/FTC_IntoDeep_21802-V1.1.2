package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Subsystems.AdvancedMecanumSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.GyroscopeSubsystem;

@TeleOp(name="Advanced Mecanum Drive")
public class MecanumDriveControl extends LinearOpMode {
    @Override
    public void runOpMode(){
     AdvancedMecanumSubsystem mecanumDrive;
     GyroscopeSubsystem gyroscope;

     boolean fieldOrientedMode = true;
     double speedMultiplier = 0.5;

     // Initialize subsystems
        gyroscope = new GyroscopeSubsystem(hardwareMap, telemetry);
        mecanumDrive = new AdvancedMecanumSubsystem(hardwareMap, telemetry, gamepad1);

        // Reset gyroscope
        gyroscope.reset();

        waitForStart();

    while (opModeIsActive()) {

        if (gamepad1.x) { //Quick stop
                mecanumDrive.setMotors(0, 0, 0, 0);
        }

                // Speed adjustments
                if (gamepad1.right_bumper) {
                    speedMultiplier = 1.0; // Slow mode
                }

                // Precise rotation controls
                if (gamepad1.x) {
                    mecanumDrive.rotateDeg(180); // Rotate 90 degrees left
                }

                // Main driving method
                mecanumDrive.teleopPeriodic(mecanumDrive.BLue);


                // Update telemetry
                telemetry.addData("Field Oriented Mode", fieldOrientedMode);
                telemetry.addData("Speed", speedMultiplier);
                telemetry.addData("Gyro Angle", gyroscope.getRotation());
                telemetry.update();
        }
    }
}