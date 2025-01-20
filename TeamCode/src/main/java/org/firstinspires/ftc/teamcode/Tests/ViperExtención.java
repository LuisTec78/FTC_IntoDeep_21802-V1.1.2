package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.ClawSubsystem2;
import org.firstinspires.ftc.teamcode.Subsystems.ViperSubsystem2;

@Autonomous
public class ViperExtención extends LinearOpMode {
    @Override
    public void runOpMode(){
        ViperSubsystem2 viper = new ViperSubsystem2(hardwareMap,0.001,0,0, 0.01, 0, 0);
        ClawSubsystem2 claw = new ClawSubsystem2(hardwareMap, gamepad2);

        waitForStart();

        while (opModeIsActive()){
            claw.moveArmToDegrees(10);
            viper.extendV(viper.TICKS_PER_HIGH);

            telemetry.addData("viperL", viper.viperL.getCurrentPosition());
            telemetry.addData("viperR", viper.viperR.getCurrentPosition());
            telemetry.update();;
        }
    }
}
