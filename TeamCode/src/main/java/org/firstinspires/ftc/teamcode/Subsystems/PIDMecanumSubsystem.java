package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.utils.calculateRotation;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.lang.Math;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class PIDMecanumSubsystem {

    private static PIDMecanumSubsystem instance;
    private HardwareMap hardwareMap;
    public DcMotor FrontLeftMotor;
    public DcMotor FrontRightMotor;
    public DcMotor BackLeftMotor;
    public DcMotor BackRightMotor;

    //public MiniPID

    double frontRightPower = 0;
    double backLeftPower = 0;
    double frontLeftPower = 0;
    double backRightPower = 0;

    public double targetAngle = 0;
    private double lastDir;

    private double radians = 45;

    private Telemetry telemetry;

    private double timeOffset = 0;

    int angularError = 0;

    public double gyr;

    public double speed = 1;
    public double baseSpeed = 1;

    GyroscopeSubsystem gyroscope;

    public Gamepad gamepad;

    public PIDMecanumSubsystem(HardwareMap hardwareMap, Telemetry telemetry, Gamepad gamepad) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        FrontLeftMotor = hardwareMap.get(DcMotor.class, "FL");//
        FrontRightMotor = hardwareMap.get(DcMotor.class, "FR");
        BackLeftMotor = hardwareMap.get(DcMotor.class, "BL");
        BackRightMotor = hardwareMap.get(DcMotor.class, "BR");

        FrontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        gyroscope = new GyroscopeSubsystem(hardwareMap, telemetry);

        this.gamepad = gamepad;
    }


}