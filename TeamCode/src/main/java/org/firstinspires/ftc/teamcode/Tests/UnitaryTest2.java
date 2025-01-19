package org.firstinspires.ftc.teamcode.Tests;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.ViperGPT;

@Autonomous
public class UnitaryTest2 extends LinearOpMode {

    @Override
    public void runOpMode() {

        waitForStart();
        ViperGPT vipers = new ViperGPT(hardwareMap, 0.001, 0, 0.0001, 0.01, 0, 0);

        while (opModeIsActive()){

            vipers.moveToAngle(90);


            telemetry.addData("motorL", vipers.angleML.getCurrentPosition());
            telemetry.addData("motorR", vipers.angleMR.getCurrentPosition());

            telemetry.addData("angle", vipers.getCurrentAngle());

            telemetry.addData("targetA", vipers.targetTicks);

            telemetry.update();



        }
    }
}
