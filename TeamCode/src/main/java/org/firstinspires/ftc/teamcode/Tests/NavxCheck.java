package org.firstinspires.ftc.teamcode;

import static com.kauailabs.navx.ftc.AHRS.*;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.kauailabs.navx.ftc.AHRS;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class  NavxCheck extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        AHRS navx = new AHRS(hardwareMap.get(NavxMicroNavigationSensor.class, "navx"),
                AHRS.DeviceDataType.kProcessedData,
                50);

        while (opModeIsActive()) {
            telemetry.addData("Yaw", (int)navx.getYaw());
            telemetry.addData("Roll", (int)navx.getRoll());
            telemetry.addData("Pitch", (int)navx.getPitch());
            telemetry.update();
        }
    }
}
