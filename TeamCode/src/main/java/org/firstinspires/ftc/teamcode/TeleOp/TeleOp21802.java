package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.ClawSubsystem2;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.SimpleMecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.ViperSubsystem2;

@TeleOp
public class TeleOp21802 extends LinearOpMode {
    @Override
    public void runOpMode(){
        //SimpleMecanumDrive drive = new SimpleMecanumDrive(hardwareMap);
        ViperSubsystem2 viper = new ViperSubsystem2(hardwareMap,  0.003, 0, 0, 0.01, 0, 0);
        ClawSubsystem2 claw = new ClawSubsystem2(hardwareMap, gamepad2);
        MecanumDriveSubsystem drive = new MecanumDriveSubsystem(hardwareMap, gamepad1, telemetry);

        waitForStart();
        while (opModeIsActive()){
            //drive.periodicDrive(gamepad1.right_stick_x, -gamepad1.right_stick_y, gamepad1.left_stick_x,gamepad1.a, gamepad1.left_bumper, gamepad1.right_bumper);

            drive.periodic();

            viper.periodic(gamepad2);
            claw.periodic(gamepad2);

            telemetry.addData("angleL", viper.angleML.getCurrentPosition());
            telemetry.addData("angleR", viper.angleMR.getCurrentPosition());

            telemetry.addData("viperL", viper.viperL.getCurrentPosition());
            telemetry.addData("viperR", viper.viperR.getCurrentPosition());

            telemetry.addData("angle", viper.getCurrentAngle());

            telemetry.addData("length", viper.getExtension());

            telemetry.addData("targetA", viper.targetTicks);


            telemetry.update();


        }
    }
}
