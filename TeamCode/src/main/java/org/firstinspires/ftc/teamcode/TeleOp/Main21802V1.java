package org.firstinspires.ftc.teamcode.TeleOp;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Autonomus.Subsystems.AutonomusActions;
import org.firstinspires.ftc.teamcode.Subsystems.AdvancedMecanumSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.GyroscopeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.VipersSubsystem;

@TeleOp
public class Main21802V1 extends LinearOpMode {
    @Override
    public void runOpMode() {
        /*----------Chasis----------*/
        AdvancedMecanumSubsystem mecanumDrive;
        GyroscopeSubsystem gyroscope;

        boolean debounce = true;

        // Initialize subsystems
        gyroscope = new GyroscopeSubsystem(hardwareMap, telemetry);
        mecanumDrive = new AdvancedMecanumSubsystem(hardwareMap, telemetry, gamepad1);

        // Reset gyroscope
        gyroscope.reset();

        /*----------Vipers----------*/
        //Altura de las canastas (cm)
        final int piso = 0;
        final int canasta1 = 66;
        final int canasta2 = 110;

        VipersSubsystem vipers = new VipersSubsystem(hardwareMap,0.01,0,0);

        /*----------Claw----------*/
        ClawSubsystem claw = new ClawSubsystem(hardwareMap,gamepad2,0.01,0,0);

        final double angleV = 90;
        final double angleH = 180;

        waitForStart();

        while (opModeIsActive()) {

            /*----------Gamepad1----------
            /*----------Chasis-Control----------
                    // Precise rotation controls
                    if (gamepad1.b) {
                        mecanumDrive.rotateDeg(180); // Rotate 90 degrees left
                    }

                    // Main driving method
                    mecanumDrive.teleopPeriodic();

                    debounce = false;

                    debounce = true;
                    mecanumDrive.setMotors(0, 0, 0, 0);



            ----------Gamepad2----------*/
            /*----------Vipers-Control----------*/
            //vipers.restartMecanism();
            vipers.periodic();

            if (gamepad2.right_stick_y > 0.1) {
                vipers.moveVertical(gamepad2.right_stick_y / vipers.Max_catA);
            } else if (gamepad2.left_stick_y > 0.1) {
                vipers.moveHorizontal(gamepad2.left_stick_y);
            } else if (gamepad2.a) {
                vipers.moveVertical(piso);
            } else if (gamepad2.b) {
                vipers.moveVertical(canasta1);
            } else if (gamepad2.y) {
                vipers.moveVertical(canasta2);
            }

            /*----------Claw-Control----------*/
            if (gamepad2.left_trigger > 0){
                //claw.armTriget();
            }else {
                if(gamepad2.dpad_up){
                    claw.moveArmto(angleV);
                }else if(gamepad2.dpad_down){
                    claw.moveArmto(angleH);
                }
            }

            if (gamepad2.a){
                claw.pickUp();
            } else if (gamepad2.b){
                claw.closeClaw();
            }


            // Update telemetry
            telemetry.addData("Angle", vipers.curViperAngle);
            telemetry.addData("Viopers", vipers.hip);
            telemetry.update();
        }
    }
}
