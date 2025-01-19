package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.utils.calculateRotation;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.utils.MiniPID;

public class OdometryMecanumSubsystem {


    private static AdvancedMecanumSubsystem instance;
    private HardwareMap hardwareMap;
    public DcMotor FrontLeftMotor;
    public DcMotor FrontRightMotor;
    public DcMotor BackLeftMotor;
    public DcMotor BackRightMotor;

    public MiniPID pid = new MiniPID(0.1,0,0);;

    double frontRightPower=0;
    double backLeftPower=0;
    double frontLeftPower=0;
    double backRightPower=0;

    public double targetAngle=0;
    private double lastDir;

    private double radians=45;

    private Telemetry telemetry;

    private double timeOffset=0;

    int angularError = 0;

    public double gyr;

    public double speed=1;
    public double baseSpeed=1;

    public OdometrySubsystem gyroscope;

    public Gamepad gamepad;

    public String BLue = "Blue";
    public String Red = "Red";

    public OdometryMecanumSubsystem(HardwareMap hardwareMap, Telemetry telemetry, Gamepad gamepad){
        this.hardwareMap=hardwareMap;
        this.telemetry = telemetry;

        FrontLeftMotor = hardwareMap.get(DcMotor.class, "FL");
        FrontRightMotor = hardwareMap.get(DcMotor.class, "FR");
        BackLeftMotor = hardwareMap.get(DcMotor.class, "BL");
        BackRightMotor = hardwareMap.get(DcMotor.class, "BR");


        FrontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        BackRightMotor.setDirection(DcMotor.Direction.REVERSE);

        FrontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        gyroscope = new OdometrySubsystem(hardwareMap, telemetry);

        this.gamepad = gamepad;
    }


    public void teleopPeriodic(String aliance){
        //IMUMethods();
        gyr = gyroscope.getRotation();
        gyroscope.update();
        //gyr=0;
        telemetry.addData("gyr",gyr);
        if(gamepad.back){
            if(gamepad.a){
                gyroscope.reset();
                updateTargetAngle();
            }
        }
        if(gamepad.y&&false){
            FrontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            FrontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            BackLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            BackRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        }else{
            FrontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            FrontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            BackLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            BackRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        //ajustar el offset del giroscopio por si el robot empieza girado
        if(gamepad.start){
            if (gamepad.dpad_up){
                gyroscope.setOffset(180);
                updateTargetAngle();
            }
            if (gamepad.dpad_right){
                gyroscope.setOffset(270);
                updateTargetAngle();
            }
            if (gamepad.dpad_down){
                gyroscope.setOffset(0);
                updateTargetAngle();
            }
            if (gamepad.dpad_left){
                gyroscope.setOffset(90);
                updateTargetAngle();
            }
        }
        //movimiento main
        if (aliance == BLue){
            arcadeDrive(-gamepad.left_stick_x,gamepad.left_stick_y,gamepad.right_stick_x,speed,gyr);
        }else if (aliance == Red){
            arcadeDrive(gamepad.left_stick_x,-gamepad.left_stick_y,-gamepad.right_stick_x,speed,gyr);
        }

        //baja la velocidad depende de qué tan presionado está el right trigger
        if(gamepad.right_trigger>0.1){
            speed=baseSpeed*(1.4-gamepad.right_trigger);
        }else{
            speed=baseSpeed;
        }

    }

    public void rotateAngle(int angle) {

    }

    public void updateTargetAngle(){
        targetAngle = lastDir;
    }

    //Ignorar por ahora, es para no crear 2 subsistemas accidentalmente
    public static AdvancedMecanumSubsystem getInstance(HardwareMap hardwareMap, Telemetry telemetry, Gamepad gamepad) {
        if (instance == null) {
            instance = new AdvancedMecanumSubsystem(hardwareMap,telemetry, gamepad);
        }
        return instance;
    }
    //para autónomo
    public void moveY(double speed) {

        frontLeftPower= -speed*(1);
        backLeftPower = -speed*(1);
        backRightPower = -speed*(1);
        frontRightPower =  -speed*(1);

        setMotors(frontLeftPower, frontRightPower, backLeftPower, backRightPower);
    }
    //para autónomo
    public void moveX(double speed) {
        frontLeftPower= speed*(1);
        backLeftPower = -speed*(1);
        backRightPower = -speed*(1);
        frontRightPower =  speed*(1);

        setMotors(frontLeftPower, frontRightPower, backLeftPower, backRightPower);
    }
    //para autónomo
    public void moveR(double speed) {
        frontLeftPower= -speed*(1);
        backLeftPower = -speed*(1);
        backRightPower = speed*(1);
        frontRightPower = speed*(1);

        setMotors(frontLeftPower, frontRightPower, backLeftPower, backRightPower);
    }

    //movimiento main
    private void arcadeDrive(double x, double y, double r, double speed, double degrees) {
        //x: movimiento lateral
        //y: movimiento vertical
        //r: rotación -1 a 1
        //speed: velocidad
        //degrees: ángulo actual del giroscopio

        telemetry.addData("targetAngle",targetAngle);

        //si el joystick está en el centro, no se mueve
        if(Math.abs(x)<0.15){
            x=0;
        }
        if(Math.abs(y)<0.15){
            y=0;
        }
        //si no se mueve el joystick derecho por medio segundo, cambia r para que el robot apunte al targetAngle, si se mueve, rota deacuerdo al joystick y actualiza el targetAngle al ángulo actual
        if(true) {
            lastDir = degrees;
            if (Math.abs(r)>0.1||(System.currentTimeMillis() - timeOffset < 500&&true)) {

                telemetry.addData("target",targetAngle);
                telemetry.addData("gyrro",degrees);

                targetAngle = degrees;
                if(Math.abs(r)>0.1){
                    timeOffset = System.currentTimeMillis();
                }

            }else{
                angularError = calculateRotation((int) degrees, (int) targetAngle);
                telemetry.addData("angularError",angularError);
                double angularThreshold = 10;
                //no rota automáticamente si el error es menor a 10 grados
                if(Math.abs(angularError)>angularThreshold){
                    //debería de ser un pid
                    r=angularError*0.5;
                }
            }

        }

        //cambia el ángulo de -180 180, a 0 360
        if(degrees<0){
            degrees=(180+(180-Math.abs(degrees)));
        }

        telemetry.addData("degrees", degrees);
        telemetry.addData("x", x);
        telemetry.addData("y", y);
        double xmod;
        double ymod;

        //convierte movimiento X y Y del robot calculado deacuerdo al ángulo del girosocpio(para que el frente del driver sea el frente del robot
        double cosine=Math.cos(Math.toRadians(degrees));
        double sine=Math.sin(Math.toRadians(degrees));

        xmod = cosine * x + sine * y; // cos(°) sin(°)
        ymod = cosine * y - sine * x;

        telemetry.addData("radians", Math.toRadians(degrees));
        telemetry.addData("cosine", cosine);
        telemetry.addData("sine", sine);
        x = xmod;
        y = ymod;
        telemetry.addData("xmod", xmod);
        telemetry.addData("ymod", ymod);
        telemetry.addData("pi", Math.PI);

        frontRightPower=0;
        backLeftPower=0;
        frontLeftPower=0;
        backRightPower=0;

        //movimiento de los motores deacuerdo a posición y el targetAngle calculado(r)
        //la formula del movimiento sin giroscopio utiliza solo estas 5 líneas
        backLeftPower = speed*(x - y - r);
        backRightPower = speed*(y + x - r);
        frontRightPower =  speed*(y - x - r);
        frontLeftPower= -(y + x + r)*speed;

        setMotors(frontLeftPower, frontRightPower, backLeftPower, backRightPower);


    }
    public void setMotors(double frontLeftPower, double frontRightPower, double backLeftPower, double backRightPower){
        telemetry.addData("frontLeftPower", frontLeftPower);
        telemetry.addData("frontRightPower", frontRightPower);
        telemetry.addData("backLeftPower", backLeftPower);
        telemetry.addData("backRightPower", backRightPower);

        FrontLeftMotor.setPower(frontLeftPower);
        FrontRightMotor.setPower(frontRightPower);
        BackLeftMotor.setPower(backLeftPower);
        BackRightMotor.setPower(backRightPower);
    }

    //actualiza el ángulo forzado
    public void setTargetAngle(double deg){
        targetAngle=deg;
        if (targetAngle<0){
            targetAngle=360+targetAngle;
        }
        if (targetAngle>360){
            targetAngle=targetAngle-360;
        }
    }

    //rota el ángulo forzado, se aseguna de que el ángulo esté entre 0 y 360. se puede mejorar con rotation2d
    public void rotateDeg(double deg){
        targetAngle+=deg;
        if (targetAngle<0){
            targetAngle=360+targetAngle;
        }
        if (targetAngle>360){
            targetAngle=targetAngle-360;
        }
    }

}
