package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.utils.MiniPID;

public class ViperGPT {
    public DcMotor angleML;
    public DcMotor angleMR;
    public DcMotor viperL;
    public DcMotor viperR;
    private MiniPID pidAL;
    private MiniPID pidAR;
    private MiniPID pidVL;
    private MiniPID pidVR;

    // Constants
    public final int TICKS_PER_DEGREE = 1176; // 560 ticks per degree
    public final int DEGREES = 360; // Motor gear / Bar gear
    public final double HighRev = 9.5;
    public final double TICKS_PER_HIGH = 384.5;
    public final double MAX_LENGTH = 30;
    public double targetLght = 0;

    public double targetTicks = 0;

    public ViperGPT(HardwareMap hardwareMap,double PA, double IA, double DA, double P, double I, double D) {
        // Initialize the motor
        angleML = hardwareMap.get(DcMotor.class, "angleML");
        angleMR = hardwareMap.get(DcMotor.class, "angleMR");

        viperL = hardwareMap.get(DcMotor.class, "viperL");
        viperR = hardwareMap.get(DcMotor.class, "viperR");


        // Reset the encoder
        angleML.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        angleMR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set the motor to run using encoder
        angleML.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        angleMR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        viperL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viperR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set the motor to run using encoder
        viperL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        viperR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        angleMR.setDirection(DcMotorSimple.Direction.REVERSE);
        viperL.setDirection(DcMotorSimple.Direction.REVERSE);


        // Initialize PID controller
        pidAL = new MiniPID(PA, IA, DA);
        pidAL.setOutputLimits(-0.8, 0.8); // Motor power from -1 to 1

        pidAR = new MiniPID(PA, IA, DA);
        pidAR.setOutputLimits(-0.8, 0.8); // Motor power from -1 to 1

        pidVL = new MiniPID(P, I, D);
        pidVL.setOutputLimits(-0.8, 0.8); // Motor power from -1 to 1

        pidVR = new MiniPID(P, I, D);
        pidVR.setOutputLimits(-0.8, 0.8); // Motor power from -1 to 1
    }

    /**
     * Convert bar angle to motor ticks
     */
    private double angleToTicks(double angle) {

        return angle * TICKS_PER_DEGREE / DEGREES;

    }

    /**
     * Convert motor ticks to bar angle
     */
    private double ticksToAngle(int ticks) {

        return ticks * DEGREES / TICKS_PER_DEGREE;

    }

    /**
     * Move the bar to a specific angle using PID control
     * @param targetAngle The desired angle in degrees
     */
    public void moveToAngle(double targetAngle) {
        targetTicks = angleToTicks(targetAngle);
        pidAR.setSetpoint(targetTicks);
        pidAL.setSetpoint(targetTicks);

        // Calculate PID output
        double powerL = pidAL.getOutput(angleML.getCurrentPosition());
        double powerR = pidAR.getOutput(angleML.getCurrentPosition());

        // Apply the power to the motor
        angleML.setPower(powerL);
        angleMR.setPower(powerR);

    }

    public double toCM(double ticks){
        return ticks/HighRev;
    }

    public double toTks(double CM){
        return CM/TICKS_PER_HIGH;
    }

    public double getExtension(){
        return toCM(viperL.getCurrentPosition());
    }

    public double getCurCA(){
        return Math.cos(getCurrentAngle()) / getExtension();
    }

    public double getCA(double hip){
        return Math.cos(getCurrentAngle()) / hip;
    }

    public void autoExtendV(){
        extendV(toTks(targetLght/ Math.cos(getCurrentAngle())));
    }

    public void extendV(double tks) {
        pidVL.setSetpoint(tks);
        viperL.setPower(pidVL.getOutput(viperL.getCurrentPosition()));

        pidVR.setSetpoint(tks);
        viperR.setPower(pidVR.getOutput(viperR.getCurrentPosition()));
    }

    public void changeTLgth(double length){
    if((length+targetLght) <= MAX_LENGTH){
            targetLght += length;
        }
    }

    /**
     * Get the current angle of the bar
     * @return Current angle in degrees
     */
    public double getCurrentAngle() {
        return ticksToAngle(angleML.getCurrentPosition());
    }

    /**
     * Check if the bar is at the target position
     * @param tolerance Angle tolerance in degrees
     * @return true if the bar is within tolerance of the target
     */
    public boolean isAtTarget(double tolerance) {
        double currentAngle = getCurrentAngle();
        double targetAngle = ticksToAngle((int) pidAL.getSetpoint());
        return Math.abs(currentAngle - targetAngle) <= tolerance;
    }

    /**
     * Stop the motor and reset the PID controller
     */
    public void stop() {
        angleML.setPower(0);
        angleMR.setPower(0);
        pidAL.reset();
        pidAR.reset();
    }
}
