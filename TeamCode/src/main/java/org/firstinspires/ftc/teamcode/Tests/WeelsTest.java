package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.TeleOp.DriverOdometry;

@TeleOp
public class WeelsTest extends LinearOpMode {
    @Override
    
    public void runOpMode(){
        DcMotor FL = hardwareMap.get(DcMotor.class, "FL");
        DcMotor FR = hardwareMap.get(DcMotor.class, "FR");
        DcMotor BL = hardwareMap.get(DcMotor.class, "BL");
        DcMotor BR = hardwareMap.get(DcMotor.class, "BR");

        GoBildaPinpointDriver odo = hardwareMap.get(GoBildaPinpointDriver.class, "odo" );

        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);

        waitForStart();
        while (opModeIsActive()){
            FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            if (gamepad1.a){
                FL.setPower(0.5);
            } else if (gamepad1.b){
                FR.setPower(0.5);
            } else if (gamepad1.x){
                BL.setPower(0.5);
            } else if (gamepad1.y){
                BR.setPower(0.5);
            } else {
                FL.setPower(0);
                FR.setPower(0);
                BL.setPower(0);
                BR.setPower(0);
            }

            odo.update();

            telemetry.addData("y", odo.getEncoderY());
            telemetry.addData("x", odo.getEncoderX());
            telemetry.addData("heading", odo.getHeading());
            telemetry.update();

        }


    }
}
