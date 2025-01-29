package org.firstinspires.ftc.teamcode.Tests;

import com.pedropathing.localization.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Subsystems.AdvancedMecanumSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ClawSubsystem2;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumAuto;
import org.firstinspires.ftc.teamcode.Subsystems.ViperSubsystem2;

@Autonomous
public class Auto21802 extends LinearOpMode {

    @Override
    public void runOpMode() {
        GoBildaPinpointDriver gps = hardwareMap.get(GoBildaPinpointDriver.class,"odo");
        gps.initialize();
        gps.resetPosAndIMU();
        gps.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        MecanumAuto drive = new MecanumAuto(hardwareMap,telemetry,gamepad1);

        ViperSubsystem2 viper = new ViperSubsystem2(hardwareMap, 0,0,0,0.01,0,0);
        ClawSubsystem2 claw = new ClawSubsystem2(hardwareMap, gamepad2);

        boolean switc = true;

        while(opModeInInit()){
            gps.getPosition().getX(DistanceUnit.INCH);
            gps.update();

        } waitForStart();

        ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        double timeBegin = timer.time();

        while (timer.time()-timeBegin<300){
            drive.moveStrafe();

        }

        while (opModeIsActive()){
            claw.closeCLaw();
            claw.moveArmToDegrees(30);
            if (switc) {
                viper.extendV(30);
                if (viper.getExtension() >= 29){
                    switc = false;
                }
            }
            if (!switc) {
                    claw.openCLaw();
                    viper.extendV(0);
            }

            telemetry.addData("extension", viper.getExtension());
            telemetry.update();
        }
    }


}

