package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.kauailabs.navx.ftc.AHRS;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class GyroscopeSubsystem {

    private final byte NAVX_DEVICE_UPDATE_RATE_HZ = 50;
    private AHRS navx = null;
    private static GyroscopeSubsystem instance;
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

    public GyroscopeSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        try {
            navx = new AHRS(hardwareMap.get(NavxMicroNavigationSensor.class, "navx"),
                    AHRS.DeviceDataType.kProcessedData,
                    NAVX_DEVICE_UPDATE_RATE_HZ);

            // Wait for gyro to initialize
            while (!navx.isCalibrating()) {
                Thread.sleep(50);
            }
            while (navx.isCalibrating()) {
                Thread.sleep(50);
            }

            reset();
        } catch (Exception e) {
            telemetry.addData("Gyro Error", "Failed to initialize NavX: " + e.getMessage());
            telemetry.update();
        }
    }

    public double getRotation() {
        if (navx == null) return 0;
        updateYawRate();
        telemetry.addData("Raw Yaw", navx.getYaw());
        return normalizeAngle(navx.getYaw() + offset);
    }

    public double getOrientation() {
        if (navx == null) return 0;
        double yaw = navx.getYaw() + offset;
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
        if (navx != null) {
            navx.zeroYaw();
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
        double currentYaw = navx.getYaw();
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
            calibrationStartYaw = navx.getYaw();
        }
    }

    public void updateCalibration() {
        if (isCalibrating) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - calibrationStartTime >= CALIBRATION_DURATION_MS) {
                double endYaw = navx.getYaw();
                double totalDrift = normalizeAngleDifference(endYaw - calibrationStartYaw);
                driftRate = totalDrift / (CALIBRATION_DURATION_MS / 1000.0);
                isCalibrating = false;
                telemetry.addData("Calibration", "Complete. Drift rate: %.2f deg/s", driftRate);
            }
        }
    }

    public boolean isConnected() {
        return navx != null && navx.isConnected();
    }

    public double getFusedHeading() {
        return navx == null ? 0 : navx.getFusedHeading();
    }

    public boolean isMoving() {
        return navx != null && navx.isMoving();
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
        if (navx != null) {
            updateYawRate();
            updateCalibration();

            // Update telemetry
            telemetry.addData("Gyro Connected", isConnected());
            telemetry.addData("Yaw", "%.2f°", getRotation());
            telemetry.addData("Pitch", "%.2f°", navx.getPitch());
            telemetry.addData("Roll", "%.2f°", navx.getRoll());
            telemetry.addData("Yaw Rate", "%.2f°/s", yawRate);
            telemetry.addData("Is Moving", isMoving());
            telemetry.addData("Is Rotating", isRotating());
        }
    }


}