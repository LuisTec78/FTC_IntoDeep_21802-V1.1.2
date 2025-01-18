/*package org.firstinspires.ftc.teamcode.Autonomus.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.VipersSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;
import org.firstinspires.ftc.teamcode.pedroPathing.util.Timer;

import java.util.Queue;

public class  AutonomusUpdate {
    private AutonomusPositions pos = new AutonomusPositions();
    private AutonomusFunctions functions = new AutonomusFunctions();
    private AutonomusPaths paths = new AutonomusPaths();

    private VipersSubsystem viper;
    private ClawSubsystem claw;
    private AutonomusActions act;

    public AutonomusUpdate(HardwareMap hardwareMap){
        viper  = new VipersSubsystem(hardwareMap, 0.01, 0, 0);
        claw  = new ClawSubsystem(hardwareMap,0.01,0,0);
        act = new AutonomusActions(hardwareMap);
    }

    private int state = 0;
    private Timer pathTimer;
    private Follower follower;

    //Altura de las canastas (cm)
    final int piso = 0;
    final int canasta1 = 66;
    final int canasta2 = 110;

    //ALtura de las barras (cm)
    final int highP = 100;
    final int lowP = 60;

    PathChain lastPath;

    public void quickUpdate(){
        switch (state){
            case 0:
                follower.followPath(paths.blueL_Y_DwUp.get("pk1"));
                setPathState(1);
                break;
            case 1:
                if(follower.getPose().getX() > (pos.yPickUpPos.get("blueDw").getX() -1) &&
                        follower.getPose().getY() > (pos.yPickUpPos.get("blueDw").getY() -1)){

                    viper.restartMecanism();

                    claw.autoPosWrist();
                    claw.closeClaw();
                    follower.followPath(paths.blueL_Y_DwUp.get("score1"));

                    setPathState(2);
                }
                break;
            case 2:
                if(follower.getPose().getX() > (pos.Scorepos.get("blueB").getX() -1) &&
                        follower.getPose().getY() > (pos.Scorepos.get("blueB").getY() -1)){

                    viper.moveVertical(canasta2);

                    claw.openClaw();
                    follower.followPath(paths.blueL_Y_DwUp.get("pk2"));

                    setPathState(3);
                }
                break;
            case 3:
                if(follower.getPose().getX() > (pos.yPickUpPos.get("blueMid").getX() -1) &&
                        follower.getPose().getY() > (pos.yPickUpPos.get("blueMid").getY() -1)){

                    viper.restartMecanism();

                    claw.autoPosWrist();
                    claw.closeClaw();
                    follower.followPath(paths.blueL_Y_DwUp.get("score2"));

                    setPathState(4);
                }
                break;
            case 4:
                if(follower.getPose().getX() > (pos.Scorepos.get("blueB").getX() -1) &&
                        follower.getPose().getY() > (pos.Scorepos.get("blueB").getY() -1)){

                    viper.moveVertical(canasta2);

                    claw.openClaw();
                    follower.followPath(paths.blueL_Y_DwUp.get("pk3"));

                    setPathState(5);
                }
                break;
            case 5:
                if(follower.getPose().getX() > (pos.yPickUpPos.get("blueUp").getX() -1) &&
                        follower.getPose().getY() > (pos.yPickUpPos.get("blueUp").getY() -1)){

                    viper.restartMecanism();

                    claw.autoPosWrist();
                    claw.closeClaw();
                    follower.followPath(paths.blueL_Y_DwUp.get("score3"));

                    setPathState(6);
                }
                break;
            case 6:
                if(follower.getPose().getX() > (pos.Scorepos.get("blueB").getX() -1) &&
                        follower.getPose().getY() > (pos.Scorepos.get("blueB").getY() -1)){

                    viper.moveVertical(canasta2);

                    claw.openClaw();
                    follower.followPath(paths.blueL_Y_DwUp.get("pk3"));

                    setPathState(-1);
                }
                break;
        }
    }

    public void setPathState(int pState) {
        state = pState;
        pathTimer.resetTimer();
    }

    public void blueUpdate(Queue<PathChain> secuence, boolean isY){
        if (isY){
            setPathState(6);
            switch (state){
                case 6://Start
                    lastPath = secuence.peek();
                    follower.followPath(secuence.poll());
                    setPathState(5);
                    break;
                case 5://Pk1
                    if (follower.getPose().getX() > (functions.getX(lastPath) -1)
                            && follower.getPose().getY() > (functions.getY(lastPath) -1)){

                        act.pickUp();
                        lastPath = secuence.peek();
                        follower.followPath(secuence.poll());
                        setPathState(4);
                    }
                    break;
                case 4://Score1
                    if (follower.getPose().getX() > (functions.getX(lastPath) -1)
                            && follower.getPose().getY() > (functions.getY(lastPath) -1)){

                        act.scoreB(canasta2);
                        lastPath = secuence.peek();
                        follower.followPath(secuence.poll());
                        setPathState(3);
                    }
                    break;
                case 3://Pk2
                    if (follower.getPose().getX() > (functions.getX(lastPath) -1)
                            && follower.getPose().getY() > (functions.getY(lastPath) -1)){

                        act.pickUp();
                        lastPath = secuence.peek();
                        follower.followPath(secuence.poll());
                        setPathState(2);
                    }
                    break;
                case 2://Score2
                    if (follower.getPose().getX() > (functions.getX(lastPath) -1)
                            && follower.getPose().getY() > (functions.getY(lastPath) -1)){

                        act.scoreB(canasta2);
                        lastPath = secuence.peek();
                        follower.followPath(secuence.poll());
                        setPathState(1);
                    }
                    break;
                case 1://Pk3
                    if (follower.getPose().getX() > (functions.getX(lastPath) -1)
                            && follower.getPose().getY() > (functions.getY(lastPath) -1)){

                        act.pickUp();
                        lastPath = secuence.peek();
                        follower.followPath(secuence.poll());
                        setPathState(0);
                    }
                    break;
                case 0://Score3
                    if (follower.getPose().getX() > (functions.getX(lastPath) -1)
                            && follower.getPose().getY() > (functions.getY(lastPath) -1)){

                        act.scoreB(canasta2);
                        lastPath = secuence.peek();
                        follower.followPath(secuence.poll());
                        setPathState(-1);
                    }
                    break;
            }
        } //Ejecuta cada path en secuence, el num de acciones son 6
        else {
            setPathState(12);
            switch (state) {
                case 12:
                    setPathState(11);
                    lastPath = secuence.peek();
                    follower.followPath(secuence.poll());
                    break;
                case 11:
                    if ( follower.getPose().getX() > (functions.getX(lastPath) -1)
                            && follower.getPose().getY() > (functions.getY(lastPath) -1)){

                        act.pickUp();
                        lastPath = secuence.peek();
                        follower.followPath(secuence.poll());
                        setPathState(10);
                    }
                    break;
                case 10:
                    if (follower.getPose().getX() > (functions.getX(lastPath) -1)
                            && follower.getPose().getY() > (functions.getY(lastPath) -1)){

                        act.putDown();
                        lastPath = secuence.peek();
                        follower.followPath(secuence.poll());
                        setPathState(9);
                    }
                    break;
                case 9:
                    if (follower.getPose().getX() > (functions.getX(lastPath) -1)
                            && follower.getPose().getY() > (functions.getY(lastPath) -1)){

                        act.pkHuman();
                        lastPath = secuence.peek();
                        follower.followPath(secuence.poll());
                        setPathState(8);
                    }
                    break;
                case 8:
                    if (follower.getPose().getX() > (functions.getX(lastPath) -1)
                            && follower.getPose().getY() > (functions.getY(lastPath) -1)){

                        act.scoreP(highP);
                        lastPath = secuence.peek();
                        follower.followPath(secuence.poll());
                        setPathState(7);
                    }
                    break;
                case 7:
                    if (follower.getPose().getX() > (functions.getX(lastPath) -1)
                            && follower.getPose().getY() > (functions.getY(lastPath) -1)){

                        act.pickUp();
                        lastPath = secuence.peek();
                        follower.followPath(secuence.poll());
                        setPathState(6);
                    }
                    break;
                case 6:
                    if (follower.getPose().getX() > (functions.getX(lastPath) -1)
                            && follower.getPose().getY() > (functions.getY(lastPath) -1)){

                        act.putDown();
                        lastPath = secuence.peek();
                        follower.followPath(secuence.poll());
                        setPathState(5);
                    }
                    break;
                case 5:
                    if (follower.getPose().getX() > (functions.getX(lastPath) -1)
                            && follower.getPose().getY() > (functions.getY(lastPath) -1)){

                        act.pkHuman();
                        lastPath = secuence.peek();
                        follower.followPath(secuence.poll());
                        setPathState(4);
                    }
                    break;
                case 4:
                    if (follower.getPose().getX() > (functions.getX(lastPath) -1)
                            && follower.getPose().getY() > (functions.getY(lastPath) -1)){

                        act.scoreP(highP);
                        lastPath = secuence.peek();
                        follower.followPath(secuence.poll());
                        setPathState(3);
                    }
                    break;
                case 3:
                    if (follower.getPose().getX() > (functions.getX(lastPath) -1)
                            && follower.getPose().getY() > (functions.getY(lastPath) -1)){

                        act.pickUp();
                        lastPath = secuence.peek();
                        follower.followPath(secuence.poll());
                        setPathState(2);
                    }
                    break;
                case 2:
                    if (follower.getPose().getX() > (functions.getX(lastPath) -1)
                            && follower.getPose().getY() > (functions.getY(lastPath) -1)){

                        act.putDown();
                        lastPath = secuence.peek();
                        follower.followPath(secuence.poll());
                        setPathState(1);
                    }
                    break;
                case 1:
                    if (follower.getPose().getX() > (functions.getX(lastPath) -1)
                            && follower.getPose().getY() > (functions.getY(lastPath) -1)){

                        act.pkHuman();
                        lastPath = secuence.peek();
                        follower.followPath(secuence.poll());
                        setPathState(0);
                    }
                    break;
                case 0:
                    if (follower.getPose().getX() > (functions.getX(lastPath) -1)
                            && follower.getPose().getY() > (functions.getY(lastPath) -1)){

                        act.scoreP(highP);
                        lastPath = secuence.peek();
                        follower.followPath(secuence.poll());
                        setPathState(-1);
                    }
                    break;
            }
        }//Ejecuta cada path en secuencia, el num de acciones es 12
    }

    public void periodic(){
        telemetry.addData("State", state);
        telemetry.addData("Time", pathTimer);
        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());

    }
}*/
