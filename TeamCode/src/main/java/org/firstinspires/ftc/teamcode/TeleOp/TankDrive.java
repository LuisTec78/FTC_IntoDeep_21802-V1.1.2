package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp //dice que el código se puede correr en el modo manual
public class TankDrive extends LinearOpMode {
    @Override
    public void runOpMode() {
        //asigna el motor izquierdo
        DcMotor motorL = hardwareMap.get(DcMotor.class,"leftMotor");
        //asigna el motor derecho
        DcMotor motorR = hardwareMap.get(DcMotor.class,"rightMotor");

        //invierte el motor derecho
        motorR.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart(); //espera a que le vuelvas a dar play
        while (opModeIsActive()){ //corre mientras el programa este activo
            if (gamepad1.a) { //corre si presionas el boton a (X en el control de play)
                //asigna las potencias a los motores para avanzar
                motorL.setPower(0.5);
                motorR.setPower(0.5);
            }
            else if (gamepad1.b) { //corre si no se está presionando a pero se presiona b
                //asigna las potencias a los motores para retroceder
                motorL.setPower(-0.5);
                motorR.setPower(-0.5);
            } else { //corre si no estás presionando a ni b
                motorL.setPower(0);
                motorR.setPower(0);
            }
        }
    }

}
