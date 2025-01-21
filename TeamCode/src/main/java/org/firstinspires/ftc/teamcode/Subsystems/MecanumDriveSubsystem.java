package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.GoBildaPinpointDriver;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

public class MecanumDriveSubsystem {
    //private GoBildaPinpointDriver H_ORIENTATION;
    private Gamepad gamepad;
    private DcMotor FL;
    //asigna el motor derecho superior
    private DcMotor FR;
    //asigna el motor izquierdo inferior
    private DcMotor BL;
    //asigna el motor derecho inferior
    private DcMotor BR;
    private Telemetry telemetry;
    double speed = 1;
    //private DcMotor y;
    //String mode = "Alan";
    double r = 0;
    //private GyroscopeSubsystem navx;


    public MecanumDriveSubsystem(HardwareMap hardwareMap, Gamepad gamepad, Telemetry telemetry) {
        this.telemetry = telemetry;
        this.gamepad = gamepad;

        //asigna el motor izquierdo superior
        FL = hardwareMap.get(DcMotor.class, "FL");
        //asigna el motor derecho superior
        FR = hardwareMap.get(DcMotor.class, "FR");
        //asigna el motor izquierdo inferior
        BL = hardwareMap.get(DcMotor.class, "BL");
        //asigna el motor derecho inferior
        BR = hardwareMap.get(DcMotor.class, "BR");
        //y = hardwareMap.get(DcMotor.class, "y");

        //navx = GyroscopeSubsystem.getInstance(hardwareMap);
        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        //BL.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);


        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }




    public void periodic() {


        double x = (gamepad.right_bumper ? 1 : 0) - (gamepad.left_bumper ? 1 : 0);
        double y = gamepad.right_trigger - gamepad.left_trigger;
        double r = gamepad.right_stick_x;



        if (gamepad.a) {
            speed = 0.3;
        } else if (gamepad.b) {
            speed = 1;
        }
        BL.setPower(speed * (y - x + r));
        BR.setPower(speed * (y + x - r));
        FR.setPower(speed * (y - x - r));
        FL.setPower((y + x + r) * speed);



        telemetry.addData("y",y);
        telemetry.addData("Speed",speed);
        telemetry.addData("Trigger",gamepad.left_trigger);
        //telemetry.addData("GPS", H_ORIENTATION);

        /*if (gamepad.left_trigger > 0) { //corre si presionas el boton a (X en el control de play)
            //asigna las potencias a los motores para avanzar
            moveDown();
        } else if(gamepad.right_bumper) {
            moveRight();
        } else if (gamepad.left_bumper) {
            moveLeft();
        } else if (gamepad.right_trigger > 0) { //corre si no se está presionando a pero se presiona b
            //asigna las potencias a los motores para retroceder
            moveUp();
        } else if (gamepad.left_stick_x < 0) {
            rotateRight();
        } else if (gamepad.left_stick_x > 0) {
            rotateLeft();
        } else {
            stop();
        }*/


    }

    public void moveDown(){
        setMotors(-1,-1,-1,-1);
    }
    public void moveUp(){
        setMotors(1,1,1,1);
    }
    public void moveLeft(){
        setMotors(-1,1,1,-1);
    }
    public void moveRight(){
        setMotors(1,-1,-1,1);
    }

    public void stop(){
        setMotors(0,0,0,0);
    }

    public void rotateLeft () {
        setMotors(1, -1, 1, -1);
    }

    public void rotateRight () {
        setMotors(-1, 1, -1, 1);
    }

    public void setMotors(double powerFrontLeft, double powerFrontRight, double powerBackLeft, double powerBackRight){
        FL.setPower(powerFrontLeft);
        FR.setPower(powerFrontRight);
        BL.setPower(powerBackLeft);
        BR.setPower(powerBackRight);
    }








}




















/*
    public void goToDetection(AprilTagDetection detection){
        while (detection.rawPose.x > 5){
            moveRight();
        }
        stop();
    }
 */