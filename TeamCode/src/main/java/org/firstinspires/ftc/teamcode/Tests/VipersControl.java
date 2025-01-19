package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.ViperSubsystem2;

@TeleOp
public class VipersControl extends LinearOpMode {

    private double smoothTargetExtension = 0;
    private double smoothTargetAngle = 0;
    private double currentExtension = 0;
    private double currentAngle = 0;

    // Constantes de suavización y límites
    private final double SMOOTHING_FACTOR = 0.2; // Suavizado para ángulo y extensión
    private final double MAX_EXTENSION_RATE = 100; // Velocidad máxima de extensión (ticks/s)
    private final double MAX_ANGULAR_RATE = 30; // Velocidad máxima de cambio angular (grados/s)

    @Override
    public void runOpMode() {
        ViperSubsystem2 viper = new ViperSubsystem2(hardwareMap, 0.001, 0, 0.01, 0.01, 0, 0);

        waitForStart();

        while (opModeIsActive()) {
            double deltaTime = getRuntime(); // Tiempo desde la última iteración

            // Lectura del joystick para extensión (eje Y del stick derecho)
            double targetExtension = viper.toTks(gamepad1.right_stick_y * 100);

            // Suavizar la entrada de extensión
            smoothTargetExtension = (SMOOTHING_FACTOR * targetExtension) + ((1 - SMOOTHING_FACTOR) * smoothTargetExtension);

            // Limitar la velocidad de extensión
            double maxExtensionChange = MAX_EXTENSION_RATE * deltaTime;
            if (Math.abs(smoothTargetExtension - currentExtension) > maxExtensionChange) {
                if (smoothTargetExtension > currentExtension) {
                    currentExtension += maxExtensionChange;
                } else {
                    currentExtension -= maxExtensionChange;
                }
            } else {
                currentExtension = smoothTargetExtension;
            }

            // Extender el viper
            viper.extendV(currentExtension);

            // Lectura del joystick para el ángulo (eje Y del stick izquierdo)
            double joystickInputAngle = gamepad1.left_stick_y * 80;

            // Suavizar la entrada del ángulo
            smoothTargetAngle = (SMOOTHING_FACTOR * joystickInputAngle) + ((1 - SMOOTHING_FACTOR) * smoothTargetAngle);

            // Limitar la velocidad de cambio angular
            double maxAngularChange = MAX_ANGULAR_RATE * deltaTime;
            if (Math.abs(smoothTargetAngle - currentAngle) > maxAngularChange) {
                if (smoothTargetAngle > currentAngle) {
                    currentAngle += maxAngularChange;
                } else {
                    currentAngle -= maxAngularChange;
                }
            } else {
                currentAngle = smoothTargetAngle;
            }

            // Mover al ángulo calculado
            viper.moveToAngle(Math.abs(currentAngle));

            // Telemetría para monitorear
            telemetry.addData("Target Extension (Ticks):", smoothTargetExtension);
            telemetry.addData("Current Extension (Ticks):", currentExtension);
            telemetry.addData("Target Angle (Degrees):", smoothTargetAngle);
            telemetry.addData("Current Angle (Degrees):", currentAngle);
            telemetry.update();
        }
    }
}
