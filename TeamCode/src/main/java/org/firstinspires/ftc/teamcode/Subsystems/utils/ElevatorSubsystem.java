package org.firstinspires.ftc.teamcode.Subsystems.utils;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.utils.MiniPID;

public class ElevatorSubsystem {
    //Motores
    public DcMotor viperL;
    public DcMotor viperR;

    //PID
    public MiniPID pid;
    public double outputVL;
    public double outputVR;

    //Encouders
    public final double tickRev = 384.5; //Ticks X Revolución (ambos vipers)
    public final int HighRev = 12; //ALtura X Revolución
    public double currentHigh = 0;
    public final int Start_lenght = 33;
    public double viperLenghtL = 0;
    public double viperLenghtR = 0;

    public ElevatorSubsystem(HardwareMap hardwareMap, double p, double i, double d) {
        viperL = hardwareMap.get(DcMotor.class, "viperL");
        viperR = hardwareMap.get(DcMotor.class, "viperR");

        viperL.setDirection(DcMotorSimple.Direction.REVERSE);

        pid = new MiniPID(p,i,d);
        pid.setOutputLimits(-0.5, 0.5);
    }

    public void restartVipers(){
        pid.setSetpoint(0);

        outputVL = pid.getOutput(viperL.getCurrentPosition());
        viperL.setPower(outputVL);

        outputVR = pid.getOutput(viperR.getCurrentPosition());
        viperR.setPower(outputVR);
    }

    public double calculedHigh(double enco){
        currentHigh = enco * HighRev;
        return currentHigh;
    }

    public void elevateVipers(double high){
        pid.setSetpoint(high);

        viperLenghtL = calculedHigh(viperL.getCurrentPosition());
        viperLenghtR = calculedHigh(viperR.getCurrentPosition());

        viperL.setPower(pid.getOutput(viperLenghtL));
        viperR.setPower(pid.getOutput(viperLenghtR));

        telemetry.addData("HighL",viperLenghtL);
        telemetry.addData("HighR",viperLenghtR);
        telemetry.update();
    }



}
