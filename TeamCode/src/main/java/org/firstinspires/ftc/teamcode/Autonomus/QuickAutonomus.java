/*package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomus.Subsystems.AutonomusUpdate;
import org.firstinspires.ftc.teamcode.Subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.VipersSubsystem;

@Autonomous
public class QuickAutonomus extends LinearOpMode {
    @Override
    public void runOpMode() {
        AutonomusUpdate update = new AutonomusUpdate(hardwareMap);

        ClawSubsystem claw = new ClawSubsystem(hardwareMap,0.01,0,0);
        VipersSubsystem viper = new VipersSubsystem(hardwareMap,0.01,0,0);

        waitForStart();
        while (opModeIsActive()) {
            viper.periodic();

            claw.reestar();
            viper.restartMecanism();

            update.quickUpdate();
            update.periodic();

        }
    }
}
*/