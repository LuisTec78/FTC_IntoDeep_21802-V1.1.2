package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Subsystems.utils.MiniPID;

@Autonomous
public class UnitaryTest1 extends LinearOpMode {
    @Override
    public void runOpMode() {

        MiniPID LApid = new MiniPID(0.01,0,0);
        MiniPID RApid = new MiniPID(0.01,0,0);
        double LAOutput;
        double RAOutput;

        DcMotor angleML = hardwareMap.get(DcMotor.class,"angleML");
        DcMotor angleMR = hardwareMap.get(DcMotor.class,"angleMR");

        angleML.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        angleMR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        angleML.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        angleMR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

         angleML.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        while (opModeIsActive()){

            /*LApid.setOutputLimits(-0.7, 0.7);
            RApid.setOutputLimits(-0.7, 0.7);

            LApid.setSetpoint(294);
            LAOutput = LApid.getOutput(angleML.getCurrentPosition());
            RApid.setSetpoint(294);
            RAOutput = RApid.getOutput(-angleML.getCurrentPosition());

            angleML.setPower(LAOutput);
            angleMR.setPower(RAOutput);*/

            telemetry.addData("motor L", angleML.getCurrentPosition());
            telemetry.addData("motor R", angleMR.getCurrentPosition());
            telemetry.update();
        }

    }
}
