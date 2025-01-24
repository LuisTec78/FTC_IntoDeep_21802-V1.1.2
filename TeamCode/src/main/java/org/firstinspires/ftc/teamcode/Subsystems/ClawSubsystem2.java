package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class ClawSubsystem2 {
    public Servo arm;
    public Servo claw;
    public Servo wrist;
    public RevColorSensorV3 color;

   public final int MAX_ARM_DEGREES = 300;
    public final int MIN_ARM_DEGREES = 300;
    public final int MAX_CLAW_DEGREES = 300;
    public final int MIN_ClAW_DEGREES = 300;
    public final int MAX_WRIST_DEGREES = 300;
    public final int MIN_WRIST_DEGREES = 300;
    public final double open = 7;
    public final double close = 0;



    public ClawSubsystem2(HardwareMap hardwareMap, Gamepad gamepad){
        arm = hardwareMap.get(Servo.class, "arm");
        claw = hardwareMap.get(Servo.class, "claw");
        wrist = hardwareMap.get(Servo.class, "wrist");
        color = hardwareMap.get(RevColorSensorV3.class, "color");

        claw.setDirection(Servo.Direction.REVERSE);

    }

    public double normalizeD(double degrees){
        return degrees/MAX_ARM_DEGREES;
    }

    public double toDegrees(double tick){
        return tick*MAX_ARM_DEGREES;
    }

    public void moveArmToDegrees(double degrees){
        arm.setPosition(normalizeD(degrees));
    }

    public void moveCLawToDegrees(double degrees){
        claw.setPosition(degrees);
    }

    public void moveWristToDegrees(double degrees){
        wrist.setPosition(degrees);
    }

    public void reestartArm(){
        arm.setPosition(0);
    }

    public void stabilizeArm(double degreesV, double degreesA){
        double CompA = 0;

    moveArmToDegrees(degreesA-degreesV);
    }

    public void openCLaw(){
        moveCLawToDegrees(open);
    }

    public void closeCLaw(){
        moveCLawToDegrees(close);
    }

    public void reestartWrist(){
        moveWristToDegrees(MIN_WRIST_DEGREES);
    }

    public void autoPosWrist(){
    for (int i = MIN_WRIST_DEGREES;color.getDistance(DistanceUnit.CM) != 4 && toDegrees(wrist.getPosition()) != MAX_WRIST_DEGREES; i += 5){
            moveWristToDegrees(i);
        }
    }

    public void PkUp( double Vdegrees){
        openCLaw();
        moveArmToDegrees(0);
        if (color.getDistance(DistanceUnit.CM) <= 4){
            closeCLaw();
        }
    }

    public void triggerArm(double Vdegrees, double target) {
        double lstarget = target;
        if (lstarget > 0) {
            if (target > lstarget) {
                stabilizeArm(Vdegrees, toDegrees(target));
            } else {
                lstarget = target;
            }
        }
    }

    public void periodic(Gamepad gamepad2){
        if (gamepad2.right_trigger > 0) {
            moveArmToDegrees(gamepad2.right_trigger * 180);
        }else if(gamepad2.left_trigger > 0) {
            moveArmToDegrees(180 + (gamepad2.left_trigger * 90));
        }

        if (gamepad2.right_bumper) {
            moveWristToDegrees(0);
        }else if (gamepad2.left_bumper) {
            moveWristToDegrees(90);
        }

        if (gamepad2.x){
            openCLaw();
        } else if (gamepad2.y){
            closeCLaw();
        }
    }

}

