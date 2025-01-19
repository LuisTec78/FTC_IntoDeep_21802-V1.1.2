package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import android.telephony.ims.RcsUceAdapter;
import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.utils.MiniPID;

public class VipersSubsystem {

    // Subsystems
    public MiniPID angleLPid;
    public MiniPID angleRPid;
    public MiniPID LviperPid;
    public MiniPID RviperPid;

    // Dimensiones
    public final double Start_catA = 40; // Cateto adyacente inicial / longitud Horizontal Inicial
    public double Final_catA = Start_catA; // Cateto adyacente modificado / longitud Horizontal Modificada
    public final double Max_catA = 73; // Extención maxima de vipers
    public final double Max_catO = 110;
    public final double Max_hip = 150;
    public double lastHigh = 0;
    public double lastCatA = 0;
    public double catA = 0;
    public double catO = 150; // Altura maxima
    public double hip = 0; // Hipotenusa / Extención de los vipers
    public double alfa = 0; // Ángulo opuesto a theta
    public double beta = 0; // Ángulo correspondiente a theta
    public final double theta = 90; // Ángulo de 90 grados

    // Otros
    public final double catMul = 110;

    // PID
    public double outputVA = 0;

    // Motores
    public DcMotor angleML;
    public DcMotor angleMR;

    // Engranes
    public final float Gear_relation = (float) 12 / 38;
    public final int Big_gear_max_degrees = 100;
    public final double Viper_relation_degrees = 360 / Gear_relation;

    // Encoders
    public final int revTicksA = 560; // Ticks x Revolución del motor de ángulo de vipers
    public final float angleTck = 360 / revTicksA;
    public final double divTck = 492 / 100;
    public double numRevL = 0;
    public double numRevR = 0;

    // Grados
    public double vipers_degrees = 0; // PID ángulo
    public double Current_angleML_Angle = 0;
    public double Current_angleMR_Angle = 0;
    public double targetViperAngle = 0;
    public double curViperAngle = 0;

    /*----------Elevadores----------*/
    // Motores
    public DcMotor viperL;
    public DcMotor viperR;

    // PID
    public double outputVL;
    public double outputVR;

    // Encoders
    public final double tickRevV = 384.5; // Ticks X Revolución (ambos vipers)
    public final int HighRev = 12; // Altura X Revolución
    public double currentHigh = 0;
    public int Start_lenght = 33;
    public double viperLenghtL = 0;
    public double viperLenghtR = 0;
    public double targetVipersLeght = 0;

    // Touch Sensors
    public RevTouchSensor angleTch;
    public RevTouchSensor leftTch;
    public RevTouchSensor rigthTch;

    public VipersSubsystem(HardwareMap hardwareMap, double p, double i, double d) {
        angleML = hardwareMap.get(DcMotor.class, "angleML");
        angleMR = hardwareMap.get(DcMotor.class, "angleMR");

        viperL = hardwareMap.get(DcMotor.class, "viperL");
        viperR = hardwareMap.get(DcMotor.class, "viperR");

        viperL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viperR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        angleML.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        angleMR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        viperL.setDirection(DcMotorSimple.Direction.REVERSE);

        angleLPid = new MiniPID(p, i, d);
        angleLPid.setOutputLimits(-0.5, 0.5);

        angleRPid = new MiniPID(p, i, d);
        angleRPid.setOutputLimits(-0.5, 0.5);

        LviperPid = new MiniPID(p, i, d);
        LviperPid.setOutputLimits(-0.5, 0.5);

        RviperPid = new MiniPID(p, i, d);
        RviperPid.setOutputLimits(-0.5, 0.5);
    }

    public void restartMecanism() {
        // Ensure that both touch sensors are pressed before proceeding
        // Code omitted for simplicity

        extendVipers(Start_catA);
        movetoAngle(0);

        // Reset encoders to track movement properly
        viperL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viperL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        viperR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viperR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        angleML.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        angleML.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        angleMR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        angleMR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void viperCurrentAngle() {
        numRevL = (double) angleML.getCurrentPosition() * angleTck;
        numRevR = (double) angleMR.getCurrentPosition() * angleTck;

        // Calculate angle for both left and right motors
        Current_angleML_Angle = numRevL * Viper_relation_degrees;
        Current_angleMR_Angle = numRevR * Viper_relation_degrees;

        // Average the two angles
        curViperAngle = (Current_angleML_Angle + Current_angleMR_Angle) / 2;
    }

    public void movetoAngle(double angle) {
        if (angle > Big_gear_max_degrees) {
            angle = Big_gear_max_degrees;
        }
        double Fangle = angle * divTck;
        angleLPid.setSetpoint(Fangle);
        vipers_degrees = angleLPid.getOutput(Current_angleML_Angle);
        angleML.setPower(vipers_degrees);

        angleRPid.setSetpoint(Fangle);
        vipers_degrees = angleRPid.getOutput(Current_angleMR_Angle);
        angleMR.setPower(vipers_degrees);
    }

    public void moveVL(double lengh) {
        double ComLengh = (lengh / HighRev) / tickRevV;
        LviperPid.setSetpoint(ComLengh);
        outputVL = LviperPid.getOutput(viperL.getCurrentPosition());
        viperL.setPower(outputVL);
    }

    public void moveVR(double lengh) {
        double ComLengh = (lengh / HighRev) / tickRevV;
        RviperPid.setSetpoint(ComLengh);
        outputVR = RviperPid.getOutput(viperR.getCurrentPosition());
        viperR.setPower(outputVR);
    }

    public void extendVipers(double lengh) {
        if (lengh > Max_hip) {
            lengh = Max_hip;
        }
        if (lengh < 0) {
            lengh = 0;
        }

        moveVL(lengh);
        moveVR(lengh);
    }

    public void moveVertical(double high) {
        lastHigh = high;

        targetViperAngle = Math.toDegrees(Math.atan(high / Final_catA));
        targetVipersLeght = Math.sqrt(Math.pow(Final_catA, 2) + Math.pow(high, 2));

        movetoAngle(targetViperAngle);
        extendVipers(targetVipersLeght);
    }

    public void moveHorizontal(double length) {
        Final_catA = catA + length;
        if (catO != lastHigh || Final_catA > Max_catA || curViperAngle >= Max_hip) {
            Final_catA = catA;
        }
        if (Final_catA < 0) {
            Final_catA = 0;
            catA = 0;
        }
    }

    public void getDimencions() {
        viperLenghtL = (viperL.getCurrentPosition() * HighRev) + Start_catA;
        viperLenghtR = (viperR.getCurrentPosition() * HighRev) + Start_catA;

        if (viperLenghtL > viperLenghtR) {
            hip = viperLenghtL;
        } else {
            hip = viperLenghtR;
        }

        alfa = curViperAngle;
        beta = 180 - (theta + alfa);

        catO = hip * Math.sin(alfa);
        catA = hip * Math.cos(alfa);
    }

    public void periodic() {
        viperCurrentAngle();
        getDimencions();
    }
}
