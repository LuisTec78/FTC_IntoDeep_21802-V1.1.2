package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Subsystems.utils.MiniPID;

@Autonomous
public class UnitaryTest1 extends LinearOpMode {
    @Override
    public void runOpMode() {

        MiniPID LApid = new MiniPID(0.007,0,0);
        MiniPID RApid = new MiniPID(0.007,0,0);
        double LAOutput;
        double RAOutput;

        DcMotor angleML = hardwareMap.get(DcMotor.class,"angleML");
        DcMotor angleMR = hardwareMap.get(DcMotor.class,"angleMR");

        waitForStart();
        while (opModeIsActive()){
            LApid.setOutputLimits(-0.6, 0.6);
            RApid.setOutputLimits(-0.6, 0.6);

            LApid.setSetpoint(5);
            LAOutput = LApid.getOutput(angleML.getCurrentPosition());
            RApid.setSetpoint(-5);
            RAOutput = RApid.getOutput(angleMR.getCurrentPosition());

            angleML.setPower(LAOutput);
            angleMR.setPower(RAOutput);

            telemetry.addData("motor L", angleML.getCurrentPosition());
            telemetry.addData("motor R", angleMR.getCurrentPosition());
        }

    }
}
