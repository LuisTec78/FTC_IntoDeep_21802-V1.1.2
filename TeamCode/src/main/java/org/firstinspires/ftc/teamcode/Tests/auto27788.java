package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.OdometrySubsystem;

@Autonomous
public class auto27788 extends LinearOpMode {
    @Override
    public void runOpMode() {

        OdometrySubsystem odo = new OdometrySubsystem(hardwareMap, telemetry);

        waitForStart();
        while (opModeInInit()){
            odo.reset();
            telemetry.addData("Heading", odo.getOrientation());
        }

        while (opModeIsActive()){

        }

    }
}
