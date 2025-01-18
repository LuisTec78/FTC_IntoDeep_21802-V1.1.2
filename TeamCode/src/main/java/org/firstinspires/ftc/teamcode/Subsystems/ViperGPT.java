package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.Subsystems.utils.MiniPID;

public class ViperGPT {
    public DcMotor angleML;
    public DcMotor angleMR;
    private MiniPID pidL;
    private MiniPID pidR;

    // Constants
    private final int TICKS_PER_DEGREE = 560; // 560 ticks per degree
    private final double GEAR_RATIO = 12.0/38.0; // Motor gear / Bar gear

    // PID Constants - you'll need to tune these values
    private final double P = 0;
    private final double I = 0.001;
    private final double D = 0;

    public ViperGPT(HardwareMap hardwareMap) {
        // Initialize the motor
        angleML = hardwareMap.get(DcMotor.class, "angleML");
        angleMR = hardwareMap.get(DcMotor.class, "angleMR");


        // Reset the encoder
        angleML.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        angleMR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set the motor to run using encoder
        angleML.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        angleMR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        // Initialize PID controller
        pidL = new MiniPID(P, I, D);
        pidL.setOutputLimits(-0.8, 0.8); // Motor power from -1 to 1
        pidL.setSetpointRange(10); // Helps smooth transitions

        pidR = new MiniPID(P, I, D);
        pidR.setOutputLimits(-0.8, 0.8); // Motor power from -1 to 1
        pidR.setSetpointRange(10); // Helps smooth transitions
    }

    /**
     * Convert bar angle to motor ticks
     */
    private int angleToTicks(double angle) {

        return (int) Math.round(angle * TICKS_PER_DEGREE / GEAR_RATIO);

    }

    /**
     * Convert motor ticks to bar angle
     */
    private double ticksToAngle(int ticks) {

        return ticks * GEAR_RATIO / TICKS_PER_DEGREE;

    }

    /**
     * Calculate angle (alpha) from adjacent and opposite sides
     * @param adjacent The adjacent side length
     * @param opposite The opposite side length (height)
     * @return The angle in degrees
     */
    public double calculateAngle(double adjacent, double opposite) {
        return Math.toDegrees(Math.atan2(opposite, adjacent));
    }

    /**
     * Move bar to calculated angle based on adjacent and opposite sides
     * @param adjacent The adjacent side length
     * @param opposite The opposite side length (height)
     */
    public void moveToCalculatedAngle(double adjacent, double opposite) {
        double angle = calculateAngle(adjacent, opposite);
        moveToAngle(angle);
    }

    /**
     * Move the bar to a specific angle using PID control
     * @param targetAngle The desired angle in degrees
     */
    public void moveToAngle(double targetAngle) {
        int targetTicks = angleToTicks(targetAngle);
        pidR.setSetpoint(targetTicks);
        pidL.setSetpoint(targetTicks);

        // Calculate PID output
        double powerL = pidL.getOutput(angleML.getCurrentPosition());
        double powerR = pidR.getOutput(angleML.getCurrentPosition());

        // Apply the power to the motor
        angleML.setPower(powerL);
        angleMR.setPower(powerR);

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
        double targetAngle = ticksToAngle((int) pidL.getSetpoint());
        return Math.abs(currentAngle - targetAngle) <= tolerance;
    }

    /**
     * Stop the motor and reset the PID controller
     */
    public void stop() {
        angleML.setPower(0);
        angleMR.setPower(0);
        pidL.reset();
        pidR.reset();
    }
}
