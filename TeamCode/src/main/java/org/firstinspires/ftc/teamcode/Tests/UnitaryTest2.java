package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.ViperGPT;

@Autonomous
public class UnitaryTest2 extends LinearOpMode {

    @Override
    public void runOpMode() {

        waitForStart();
        ViperGPT vipers = new ViperGPT(hardwareMap);

        while (opModeIsActive()){

            vipers.moveToAngle(90);

            telemetry.addData("motorL", vipers.angleML.getCurrentPosition());
            telemetry.addData("motorR", vipers.angleMR.getCurrentPosition());

            telemetry.addData("angle", vipers.getCurrentAngle());

            telemetry.update();



        }
    }
}
