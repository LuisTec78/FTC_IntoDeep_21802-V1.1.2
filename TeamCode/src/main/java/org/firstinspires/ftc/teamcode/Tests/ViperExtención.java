package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.ViperGPT;

@Autonomous
public class ViperExtención extends LinearOpMode {
    @Override
    public void runOpMode(){
        ViperGPT viper = new ViperGPT(hardwareMap,0.001,0,0, 0.1, 0, 0);

        waitForStart();
        while (opModeIsActive()){

            viper.extendV(viper.TICKS_PER_HIGH);

            telemetry.addData("viperL", viper.viperL.getCurrentPosition());
            telemetry.addData("viperR", viper.viperR.getCurrentPosition());
            telemetry.update();
        }
    }
}
