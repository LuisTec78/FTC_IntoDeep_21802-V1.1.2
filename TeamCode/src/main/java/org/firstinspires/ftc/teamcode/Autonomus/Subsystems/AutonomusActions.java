package org.firstinspires.ftc.teamcode.Autonomus.Subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.VipersSubsystem;

public class  AutonomusActions {
    private ClawSubsystem claw;
    private VipersSubsystem vipers;

    //Altura de las canastas (cm)
    final int piso = 0;
    final int canasta1 = 66;
    final int canasta2 = 110;

    //Claw degrees
    public final double Max_claw_degrees = 180;
    public final double Min_claw_degrees = 0;
    public final double Max_arm_degrees = 180;
    public final double Min_arm_degrees = 0;
    public final double Max_wrist_degrees = 180;
    public final double Min_wrist_degrees = 0;

    public final double Horizontal = 180;
    public final double Vertical = 90;

    public Gamepad gamepad;

    public AutonomusActions(HardwareMap hardwareMap){
        claw = new ClawSubsystem(hardwareMap, gamepad, 0.01,0,0);
        vipers = new VipersSubsystem(hardwareMap,0.01,0,0);
    }

    public void reestart(){
        vipers.restartMecanism();
        claw.reestar();
    }

    public void pickUp(){
        vipers.moveVertical(piso);
        vipers.moveHorizontal(0);
        claw.moveArmto(Horizontal);
        claw.pickUp();
        claw.moveArmto(Vertical);
    }

    public void putDown(){
        vipers.moveVertical(piso);
        vipers.moveHorizontal(0);
        claw.moveArmto(Horizontal);
        claw.openClaw();

    }

    public void scoreP(double high){
        vipers.moveVertical(high);
        if (vipers.currentHigh == high){
            vipers.moveVertical(high-2);
            if (vipers.currentHigh == high-2){
                claw.openClaw();
            }
        }
    }

    public void scoreB(double high){
        vipers.moveVertical(high);
        if (vipers.currentHigh == high){
            claw.openClaw();
        }
    }

    public void pkHuman(){
        vipers.moveVertical(piso);
        vipers.moveHorizontal(0);
        claw.moveArmto(Vertical);
        claw.pickUp();
    }

    public void periodic(){
        vipers.periodic();
    }

}
