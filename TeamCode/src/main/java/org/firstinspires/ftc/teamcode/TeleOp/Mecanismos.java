package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.ClawSubsystem2;
import org.firstinspires.ftc.teamcode.Subsystems.ViperSubsystem2;

import kotlinx.coroutines.selects.WhileSelectKt;

@TeleOp
public class Mecanismos extends LinearOpMode {
    @Override
    public void runOpMode() {
        ViperSubsystem2 viper = new ViperSubsystem2(hardwareMap, 0.001, 0, 0, 0.0001, 0, 0);
        ClawSubsystem2 claw = new ClawSubsystem2(hardwareMap, gamepad2);

        waitForStart();
        while (opModeIsActive()) {
            //Canasta
                if (gamepad2.dpad_up && gamepad2.a) {
                    viper.moveToHigh(134, 58);
                }

                if (gamepad2.dpad_right && gamepad2.a) {
                    viper.moveToHigh(102, 46);
                }

                if (gamepad2.dpad_down && gamepad2.a) {
                    viper.moveToHigh(25, 0);
                }

                //Barra
                if (gamepad2.dpad_up && gamepad2.b) {
                    viper.moveToHigh(98, 44);
                } else if (gamepad2.dpad_right && gamepad2.b) {
                    viper.moveToHigh(79, 26);
                } else if (gamepad2.dpad_down && gamepad2.b) {
                    viper.moveToHigh(25, 0);
                }


            if (gamepad2.right_trigger > 0) {
                claw.moveArmToDegrees(gamepad2.right_trigger * 180);
            }else if(gamepad2.left_trigger > 0) {
                claw.moveArmToDegrees(180 + (gamepad2.left_trigger * 90));
            }

            if (gamepad2.right_bumper) {
                claw.moveWristToDegrees(0);
            }else if (gamepad2.left_bumper) {
                claw.moveWristToDegrees(90);
            }

            if (gamepad2.x){
                claw.openCLaw();
            } else if (gamepad2.y){
                claw.closeCLaw();
            }

            telemetry.addData("angle", viper.getCurrentAngle());
            telemetry.addData("angleTks", viper.angleML.getCurrentPosition());
        }
    }
}
