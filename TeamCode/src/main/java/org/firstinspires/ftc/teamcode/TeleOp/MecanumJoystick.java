package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp //dice que el código se puede correr en el modo manual
public class MecanumJoystick extends LinearOpMode {
    @Override
    public void runOpMode() {
        //asigna el motor izquierdo superior
        DcMotor motorLu = hardwareMap.get(DcMotor.class,"FL");
        //asigna el motor derecho superior
        DcMotor motorRu = hardwareMap.get(DcMotor.class,"FR");
        //asigna el motor izquierdo inferior
        DcMotor motorLd = hardwareMap.get(DcMotor.class,"BL");
        //asigna el motor derecho inferior
        DcMotor motorRd = hardwareMap.get(DcMotor.class,"BR");

        //invierte el motor derecho
        motorRu.setDirection(DcMotorSimple.Direction.REVERSE);
        motorRd.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart(); //espera a que le vuelvas a dar play
        while (opModeIsActive()){ //corre mientras el programa este activo
            if (gamepad1.left_stick_x > 0) { //corre si presionas el boton a (X en el control de play)
                //asigna las potencias a los motores para avanzar
                motorLu.setPower(0.5);
                motorRu.setPower(0.5);
                motorLd.setPower(0.5);
                motorRd.setPower(0.5);

            }
            else if (gamepad1.left_stick_x < 0) { //corre si no se está presionando a pero se presiona b
                //asigna las potencias a los motores para retroceder
                motorLu.setPower(-0.5);
                motorRu.setPower(-0.5);
                motorLd.setPower(-0.5);
                motorRd.setPower(-0.5);

            }else if (gamepad1.left_stick_y < 0) { //corre si no se está presionando a pero se presiona b
                //asigna las potencias a los motores para retroceder
                motorLu.setPower(-0.5);
                motorRu.setPower(0.5);
                motorLd.setPower(0.5);
                motorRd.setPower(-0.5);

            } else if (gamepad1.left_stick_y > 0) { //corre si no se está presionando a pero se presiona b
                //asigna las potencias a los motores para retroceder
                motorLu.setPower(0.5);
                motorRu.setPower(-0.5);
                motorLd.setPower(-0.5);
                motorRd.setPower(0.5);

            } else { //corre si no estás presionando a ni b
                motorLu.setPower(0);
                motorRu.setPower(0);
                motorLd.setPower(0);
                motorRd.setPower(0);
            }
        }
    }

}
