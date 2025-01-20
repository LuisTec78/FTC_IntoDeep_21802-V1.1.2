package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class SimpleMecanumDrive {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    double x = 0;
    double y = 0;
    double rotation = 0;

    // Inicializar los motores
    public SimpleMecanumDrive(HardwareMap hardwareMap) {

        frontLeft = hardwareMap.get(DcMotor.class, "FL");
        frontRight = hardwareMap.get(DcMotor.class, "FR");
        backLeft = hardwareMap.get(DcMotor.class, "BL");
        backRight = hardwareMap.get(DcMotor.class, "BR");

        // Establecer la dirección de los motores si es necesario
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }

        // Calcular el poder de los motores
    public void drive(double vx, double vy, double vr) {
        x = vx;  // Eje X del joystick izquierdo
        y = vy; // Eje Y del joystick izquierdo (invertido)
        rotation = vr * -1; // Eje X del joystick derecho

        double frontLeftPower = y + x - rotation;
        double frontRightPower = y - x + rotation;
        double backLeftPower = y - x - rotation;
        double backRightPower = y + x + rotation;

        // Normalizar los valores para que no excedan 1.0 o -1.0
        double max = Math.max(Math.abs(frontLeftPower), Math.max(Math.abs(frontRightPower),
                Math.max(Math.abs(backLeftPower), Math.abs(backRightPower))));
        if (max > 1.0) {
            frontLeftPower /= max;
            frontRightPower /= max;
            backLeftPower /= max;
            backRightPower /= max;
        }

        setMotorPower(frontLeftPower, frontRightPower, backLeftPower, backRightPower);
    }

        // Asignar potencias a los motores
    public void setMotorPower(double frontLeftPower, double frontRightPower, double backLeftPower, double backRightPower) {
        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);
    }

    public void stop() {
        setMotorPower(0,0,0,0);
    }

    public void test(Gamepad gamepad1) {
        if (gamepad1.a) {
                        frontLeft.setPower(0.5);
                    } else if (gamepad1.b) {
                        frontRight.setPower(0.5);
                    } else if (gamepad1.x) {
                        backLeft.setPower(0.5);
                    } else if (gamepad1.y) {
                        backRight.setPower(0.5);
                    } else {
                        frontRight.setPower(0);
                        frontLeft.setPower(0);
                        backRight.setPower(0);
                        backLeft.setPower(0);
                    }
    }

    public void periodicDrive(double x,double y,double r, boolean stop,boolean left,boolean right){
         if (stop){
             stop();
         } else if (left){
             setMotorPower(0.3, -0.3, -0.3, 0.3);
         }else if (right){
             setMotorPower(-0.3, 0.3, 0.3, -0.3);
         } else {
             drive(x,y,r);
         }
    }

    /*public double reduce(double v,double reduce){
        if (v > 0.1){
            return v - reduce;
        }else if (v < -0.1){
            return v + reduce;
        }else {
            return 0;
        }
    }*/

}
