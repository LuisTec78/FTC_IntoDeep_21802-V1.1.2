package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.VipersSubsystem;

@TeleOp
public class VipersControl extends LinearOpMode {

    @Override
    public void runOpMode(){

        //Altura de las canastas (cm)
        final int piso = 0;
        final int canasta1 = 66;
        final int canasta2 = 110;

        VipersSubsystem vipers = new VipersSubsystem(hardwareMap, 0.01, 0, 0);

        double highDivider = 110;
        double lenghtDivider = vipers.Max_catA;

        waitForStart();
        while (opModeIsActive()){
            vipers.restartMecanism();
            vipers.periodic();

            if (gamepad2.right_stick_y > 0.1) {
                vipers.moveVertical(gamepad2.right_stick_y * vipers.Max_catA);
            }else if(gamepad2.left_stick_y > 0.1){
                vipers.moveHorizontal(gamepad2.left_stick_y*vipers.Max_catA);
            } else if (gamepad2.a) {
                vipers.moveVertical(piso);
            }else if (gamepad2.b) {
                vipers.moveVertical(canasta1);
            }else if (gamepad2.y) {
                vipers.moveVertical(canasta2);
            }
        }
    }
}
