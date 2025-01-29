package org.firstinspires.ftc.teamcode.Subsystems;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.GoBildaPinpointDriver;

public class OdometrySubsystem {
    private GoBildaPinpointDriver odo;
    private int offset;
    private Telemetry telemetry;
    private double lastYaw = 0;
    private long lastUpdateTime = System.nanoTime();
    private double yawRate = 0;  // degrees per second

    // Calibration and drift compensation
    private double driftRate = 0;  // degrees per second
    private boolean isCalibrating = false;
    private long calibrationStartTime = 0;
    private static final long CALIBRATION_DURATION_MS = 2000;
    private double calibrationStartYaw = 0;

    /*public static GyroscopeSubsystem getInstance(HardwareMap hardwareMap, Telemetry telemetry) {
        if (instance == null) {
            instance = new GyroscopeSubsystem(hardwareMap, telemetry);
        }
        return instance;
    }*///getInstance

    public  OdometrySubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        try {
            odo = hardwareMap.get(GoBildaPinpointDriver.class, "odo");
            odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
            odo.setOffsets(-84.0, -168.0);
            odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED, GoBildaPinpointDriver.EncoderDirection.REVERSED);
            odo.resetPosAndIMU();
            odo.recalibrateIMU();

            reset();
        } catch (Exception e) {
            telemetry.addData("Gyro Error", "Failed to initialize NavX: " + e.getMessage());
            telemetry.update();
        }
    }

    public double getRotation() {
        if (odo == null) return 0;
        updateYawRate();
        telemetry.addData("Raw Heading", odo.getHeading());
        return normalizeAngle(odo.getHeading() + offset);
    }

    public double getOrientation() {
        if (odo == null) return 0;
        double yaw = odo.getHeading()+ offset;
        return normalizeAngle(yaw);
    }

    /*public double getYaw() {
        return navx == null ? 0 : navx.getYaw();
    }*/

    /*public double getPitch() {
        return navx == null ? 0 : navx.getPitch();
    }*/

    /*public double getRoll() {
        return navx == null ? 0 : navx.getRoll();
    }*/

    public void reset() {
        if (odo != null) {
            odo.resetPosAndIMU();
            offset = 0;
            lastYaw = 0;
            yawRate = 0;
        }
    }

    public void setOffset(int degrees) {
        offset = degrees;
    }

    public double getYawRate() {
        return yawRate;
    }

    private void updateYawRate() {
        double currentYaw = odo.getHeading();
        long currentTime = System.nanoTime();
        double deltaTime = (currentTime - lastUpdateTime) / 1e9; // Convert to seconds

        if (deltaTime > 0) {
            double deltaYaw = normalizeAngleDifference(currentYaw - lastYaw);
            yawRate = deltaYaw / deltaTime;
        }

        lastYaw = currentYaw;
        lastUpdateTime = currentTime;
    }

    public void startCalibration() {
        if (!isCalibrating) {
            isCalibrating = true;
            calibrationStartTime = System.currentTimeMillis();
            calibrationStartYaw = odo.getHeading();
        }
    }

    public void updateCalibration() {
        if (isCalibrating) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - calibrationStartTime >= CALIBRATION_DURATION_MS) {
                double endYaw = odo.getHeading();
                double totalDrift = normalizeAngleDifference(endYaw - calibrationStartYaw);
                driftRate = totalDrift / (CALIBRATION_DURATION_MS / 1000.0);
                isCalibrating = false;
                telemetry.addData("Calibration", "Complete. Drift rate: %.2f deg/s", driftRate);
            }
        }
    }

    /*public boolean isConnected() {
        return odo != null && navx.isConnected();
    }*/

    /*public double getFusedHeading() {
        return navx == null ? 0 : navx.getFusedHeading();
    }*/

    public boolean isMoving() {
        return odo != null && odo.getVelX() != 0 || odo.getVelY() != 0;
    }

    public boolean isRotating() {
        return Math.abs(yawRate) > 1.0; // Consider rotating if yaw rate > 1 degree/second
    }

    // Utility methods for angle normalization
    private double normalizeAngle(double angle) {
        while (angle < 0) angle += 360;
        while (angle >= 360) angle -= 360;
        return angle;
    }

    private double normalizeAngleDifference(double difference) {
        while (difference > 180) difference -= 360;
        while (difference < -180) difference += 360;
        return difference;
    }

    public void periodic() {
        if (odo != null) {
            updateYawRate();
            updateCalibration();

            // Update telemetry
            telemetry.addData("Yaw", "%.2f°", getRotation());
            telemetry.addData("Yaw Rate", "%.2f°/s", yawRate);
            telemetry.addData("Is Moving", isMoving());
            telemetry.addData("Is Rotating", isRotating());
        }
    }


    public void update(){
        odo.update();
    }



}
