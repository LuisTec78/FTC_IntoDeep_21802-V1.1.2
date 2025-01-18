package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class ClawGPT_R extends LinearOpMode {
    @Override
    public void runOpMode() {
        // Map the servo
        Servo arm = hardwareMap.get(Servo.class, "arm");

        // Wait for the start signal
        waitForStart();

        arm.setDirection(Servo.Direction.REVERSE);
        // Move the servo to 90 degrees
        arm.setPosition(0.333);

        // Allow time for the servo to move
        sleep(1000); // Adjust based on servo speed (e.g., 1 second)

        // Read and display the servo position
        while (opModeIsActive()) {
            telemetry.addData("Servo Position", arm.getPosition());
            telemetry.update();
        }
    }
}
