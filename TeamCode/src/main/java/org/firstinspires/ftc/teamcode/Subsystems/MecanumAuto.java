package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.utils.MiniPID;

public class MecanumAuto {
    public DcMotor FrontLeftMotor;
    public DcMotor FrontRightMotor;
    public DcMotor BackLeftMotor;
    public DcMotor BackRightMotor;

    private HardwareMap hardwareMap;

    private double tckCM = 15.7;

    private double cmTck = 384.5;

    private Telemetry telemetry;

    public double speed=0.5;

    double frontRightPower=0;
    double backLeftPower=0;
    double frontLeftPower=0;
    double backRightPower=0;

    MiniPID FLpid;
    MiniPID BLpid;
    MiniPID FRpid;
    MiniPID BRpid;

    OdometrySubsystem odo;

    public MecanumAuto(HardwareMap hardwareMap, Telemetry telemetry, Gamepad gamepad){
        this.hardwareMap=hardwareMap;
        this.telemetry = telemetry;

        FrontLeftMotor = hardwareMap.get(DcMotor.class, "FL");
        FrontRightMotor = hardwareMap.get(DcMotor.class, "FR");
        BackLeftMotor = hardwareMap.get(DcMotor.class, "BL");
        BackRightMotor = hardwareMap.get(DcMotor.class, "BR");

        FrontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        BackRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        FrontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        odo = new OdometrySubsystem(hardwareMap, telemetry);

    }

    public void setMotors(double frontLeftPower, double frontRightPower, double backLeftPower, double backRightPower){
        telemetry.addData("frontLeftPower", frontLeftPower);
        telemetry.addData("frontRightPower", frontRightPower);
        telemetry.addData("backLeftPower", backLeftPower);
        telemetry.addData("backRightPower", backRightPower);

        FrontLeftMotor.setPower(frontLeftPower);
        FrontRightMotor.setPower(frontRightPower);
        BackLeftMotor.setPower(backLeftPower);
        BackRightMotor.setPower(backRightPower);
    }

    public void moveStrafe(){
        frontLeftPower = speed*(1);
        backLeftPower = speed*(1);
        backRightPower = speed*(1);
        frontRightPower = speed*(1);

        setMotors(frontLeftPower, frontRightPower, backLeftPower, backRightPower);

    }

    /*public double toCM(double tks){
       return tks * cmTck / tckCM;
    }

    public double toTck(double tks){
        return tks * tckCM / cmTck;
    }

    public double forwardCM (double cm){

    }*/

    public void rotate(double degrees){
    while (odo.getOrientation() >= degrees){
        setMotors(0.5, 0.5, -0.5, -0.5);
    }
        while (odo.getOrientation() <= degrees){
            setMotors(-0.5, -0.5, 0.5, 0.5);
        }
    }
}
