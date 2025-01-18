/*package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomus.Subsystems.AutonomusActions;
import org.firstinspires.ftc.teamcode.Autonomus.Subsystems.AutonomusPaths;
import org.firstinspires.ftc.teamcode.Autonomus.Subsystems.AutonomusUpdate;

@Autonomous
public class BM_Y1 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        AutonomusUpdate updater = new AutonomusUpdate(hardwareMap);
        AutonomusActions act = new AutonomusActions(hardwareMap);
        AutonomusPaths paths =  new AutonomusPaths();

        waitForStart();
        while (opModeIsActive()){
            act.reestart();

            updater.blueUpdate(paths.blueM_B_UpDw, true);
            act.periodic();
        }
    }
}
*/