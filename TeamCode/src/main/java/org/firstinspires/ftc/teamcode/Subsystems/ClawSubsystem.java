package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Subsystems.utils.MiniPID;

public class ClawSubsystem {
    private Servo clawS;
    public Servo armS;
    private Servo wristS;
    public Servo wristUDS;
    private RevColorSensorV3 colorSensor;

    public final double Max_claw_degrees = 180;
    public final double Min_claw_degrees = 0;
    public final double Max_arm_degrees = 90;
    public final double Min_arm_degrees = 0;
    public final double Max_wrist_degrees = 180;
    public final double Min_wrist_degrees = 0;
    public final double Max_wristUD_degrees = 180;
    public final double Min_wristUD_degrees = 0;
    public final double Horizontal = 0;
    public final double Vertical = 90;

    public double angleCom = Min_arm_degrees;
    public double angleComW = Min_wristUD_degrees;
    public boolean clawClosed = true;

    //public float actual_trigetVal = gamepad2.left_trigger;
    public double last_trigetVal = 0;
    public double angle_trigetVal = 0;

    private MiniPID armpid;
    public double armOutput = 0;
    private MiniPID clawpid;
    public double clawOutput = 0;
    private MiniPID wristpid;
    public double wristOutput = 0;
    private MiniPID wristUDpid;
    public double wristUDOutput = 0;

    private VipersSubsystem vipers;

    public ClawSubsystem(HardwareMap hardwareMap, Gamepad gamepad, double p, double i, double d) {
        clawS = hardwareMap.get(Servo.class, "claw");
        armS = hardwareMap.get(Servo.class, "arm");
        wristS = hardwareMap.get(Servo.class, "wrist");
        wristUDS = hardwareMap.get(Servo.class, "wristUD");


        colorSensor = hardwareMap.get(RevColorSensorV3.class, "color");

        vipers = new VipersSubsystem(hardwareMap, 0.01, 0, 0);

        armpid = new MiniPID(p, i, d);
        armpid.setOutputLimits(-0.5, 0.5);

        clawpid = new MiniPID(p, i, d);
        clawpid.setOutputLimits(-0.5, 0.5);

        wristpid = new MiniPID(p, i, d);
        wristpid.setOutputLimits(-0.5, 0.5);

        wristUDpid = new MiniPID(p, i, d);
        wristUDpid.setOutputLimits(-0.5, 0.5);
    }

    /*public void periodic() {

    }*/

    /*----------Arm----------*/

    public void moveArmto(double angle) {
        if (angle > Max_arm_degrees) {
            angle = Max_arm_degrees;
        } else if (angle < Min_arm_degrees) {
            angle = Min_arm_degrees;
        }

        angleCom = 90 - vipers.alfa + angle;

        armpid.setSetpoint(angleCom);
        armOutput = armpid.getOutput(armS.getPosition());
        armS.setPosition(armOutput);
    }//Mueve y estabiliza el brazo al ángulo indicado

   /* public void armTriget() {
         if (actual_trigetVal > last_trigetVal) {last_triggerVal = actual_trigetVal;}

        angle_trigetVal = last_trigetVal * Max_arm_degrees;

        angleCom = 90 - vipers.alfa + angle_trigetVal;

        moveArmto(angleCom);
    }//Mueve el brazo conforme el último valor más alto del triget

    /*----------Claw----------*/

    public void closeClaw() {
        clawpid.setSetpoint(Min_claw_degrees);
        clawOutput = clawpid.getOutput(clawS.getPosition());
        armS.setPosition(clawOutput);

        clawClosed = true;
    }//Mueve la garra al mínimo

    public void openClaw() {
        clawpid.setSetpoint(Max_claw_degrees);
        clawOutput = clawpid.getOutput(clawS.getPosition());
        armS.setPosition(clawOutput);

        clawClosed = false;
    }//Mueve la garra al máximo

    public void pickUp() {
        openClaw();
        autoPosWrist();
        if (colorSensor.getDistance(DistanceUnit.CM) > 0.5) {
            closeClaw();
        }
    }

    /*----------Wrist----------*/

    public void reestartWrist() {
        wristpid.setSetpoint(0);
        wristOutput = wristpid.getOutput(wristS.getPosition());
        wristS.setPosition(wristOutput);
    }

    public void autoPosWrist() {
        setWristPos(Min_wrist_degrees);
        for (double i = Min_wrist_degrees; i <= Max_wrist_degrees && colorSensor.getDistance(DistanceUnit.CM) > 0.3; i += 3) {
            setWristPos(i);
        }
    }

    public void setWristPos(double pos) {
        wristpid.setSetpoint(pos);
        wristOutput = wristpid.getOutput(wristS.getPosition());
        wristS.setPosition(wristOutput);
    }

    /*----------WristUD----------*/

    public void reestartWristUD(){
        wristUDpid.setSetpoint(Min_wristUD_degrees);
        wristUDOutput = wristUDpid.getOutput(wristS.getPosition());
        wristUDS.setPosition(wristOutput);
    }

    public void setWristUDPos(double pos) {
        wristUDpid.setSetpoint(pos);
        wristUDOutput = wristUDpid.getOutput(wristUDS.getPosition());
        wristUDS.setPosition(wristUDOutput);
    }

    public void moveWristUDto(double angle) {
        if (angle > Max_wristUD_degrees) {
            angle = Max_wristUD_degrees;
        } else if (angle < Min_wristUD_degrees) {
            angle = Min_wristUD_degrees;
        }

        angleComW = 90 - vipers.alfa + angle;

        setWristUDPos(angleComW);
    }//Mueve y estabiliza la muñeca(Arriva/Abajo) al ángulo indicado

    /*----------Reestart----------*/

    public void reestar(){
        reestartWrist();
        openClaw();
        moveArmto(0);
    }

}
