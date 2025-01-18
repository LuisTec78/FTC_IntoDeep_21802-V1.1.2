/*package org.firstinspires.ftc.teamcode.Autonomus.Subsystems;

import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierCurve;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;

public class PoseUtils {
    /*----------Poses----------
    //private final Pose Pose = new Pose(, , Math.toRadians());

    //Start Blue
    private final Pose blueStartL = new Pose(10, 88, Math.toRadians(0));
    //private final Pose blueStartIz = new Pose(10, 84, Math.toRadians(0));
    //private final Pose blueStartIz = new Pose(10, 81, Math.toRadians(0));

    private final Pose blueStartM = new Pose(10, 72, Math.toRadians(0));

    //private final Pose blueStartDer = new Pose(10, 63, Math.toRadians(0));
    //private final Pose blueStartDer = new Pose(10, 60, Math.toRadians(0));
    private final Pose blueStartR = new Pose(10, 57, Math.toRadians(0));


    //Start Red
    private final Pose redStartL = new Pose(134, 88, Math.toRadians(180));
    //private final Pose redStartIzMid = new Pose(134, 84, Math.toRadians(180));
    //private final Pose redStartIzDw = new Pose(134, 81, Math.toRadians(180));

    private final Pose redStartM = new Pose(134, 72, Math.toRadians(180));

    //private final Pose redStartDerUp = new Pose(134, 63, Math.toRadians(180));
    //private final Pose redStartDerMid = new Pose(134, 60, Math.toRadians(180));
    private final Pose redStartR = new Pose(134, 57, Math.toRadians(180));

    //PickUp Blue
    private final Pose bluePickUpUpY = new Pose(45.25, 130, Math.toRadians(90));
    private final Pose bluePickUpMidY = new Pose(34, 132, Math.toRadians(0));
    private final Pose bluePickUpDwY = new Pose(34, 121, Math.toRadians(0));

    private final Pose bluePickUpUp = new Pose(33, 23, Math.toRadians(0));
    private final Pose bluePickUpMid = new Pose(33, 12, Math.toRadians(0));
    private final Pose bluePickUpDw = new Pose(45.25, 15, Math.toRadians(270));


    //PickUp Red
    private final Pose redPickUpUpY = new Pose(111, 23, Math.toRadians(180));
    private final Pose redPickUpMidY = new Pose(111, 12, Math.toRadians(180));
    private final Pose redPickUpDwY = new Pose(98.4, 15, Math.toRadians(270));

    private final Pose redPickUpUp = new Pose(98.4, 130, Math.toRadians(90));
    private final Pose redPickUpMid = new Pose(111, 132, Math.toRadians(180));
    private final Pose redPickUpDw = new Pose(111, 121, Math.toRadians(180));

    //Score Blue
    private final Pose blueScoreB = new Pose(21,123, Math.toRadians(135));
    private final Pose blueScoreP = new Pose(37,72, Math.toRadians(0));

    //Score Red
    private final Pose redScoreB = new Pose(123,21, Math.toRadians(135));
    private final Pose redScoreP = new Pose(107,72, Math.toRadians(180));

    //Human player station Blue
    //private final Pose blueLargeHmStationUp = new Pose(24,15, Math.toRadians(180));
    private final Pose blueHmSt = new Pose(24,12, Math.toRadians(180));
    //private final Pose blueLargeHmStationDw = new Pose(24,10, Math.toRadians(180));

    private final Pose blueHmPk = new Pose(10,48, Math.toRadians(180));

    //private final Pose blueShortHmStationMid = new Pose(15,40, Math.toRadians(135));
    //private final Pose blueShortHmStationDw = new Pose(21,27, Math.toRadians(200));

    //Human player station Red
    //private final Pose redLargeHmStationUp = new Pose(119,134, Math.toRadians(0));
    private final Pose redHmSt = new Pose(119,132, Math.toRadians(0));
    //private final Pose redLargeHmStationDw = new Pose(119,129, Math.toRadians(0));

    //private final Pose redShortHmStationMid = new Pose(128,103, Math.toRadians(45));
    //private final Pose redShortHmStationDw = new Pose(121,116, Math.toRadians(20));


    /*----------Paths----------
    // alianza - inicio - acción - lugar (de ida o del que parte)
    //Start blue
    private Path bStLPkYdw, bStLPkYmid, bStLPkYup;
    private Path bStMPkYdw, bStMPkYmid, bStMPkYup;
    private Path bStRPkYdw, bStRPkYmid, bStRPkYup;

    /*----------Paths----------
    //alianza - inicio - sector
    //Blue
    private PathChain bLY, bMY, bRY;

    private PathChain bLB, bMB, bRB;

    //Red
    private PathChain rLY, rMY, rRY;

    private PathChain rLB, rMB, rRB;

    //Utils
    public Follower follower;



    public Point conectorPonit(Pose start, Pose end, int numConect){
        Point conector = null;
        if(numConect == 1){
            conector = new Point(start.getY(), end.getX(),Point.CARTESIAN);
        } else if (numConect == 2){
            conector = new Point(end.getY(), start.getX(),Point.CARTESIAN);
        }
        return  conector;
    }//Crea un conector a partir de dos puntos

    public Point comvertedPose(Pose pose){
        return new Point(pose.getX(), pose.getY(), Point.CARTESIAN);
    }//Comvierte una varieble Pose a una Point

    public void createPath(Path path, Pose start, Pose end, int numConect){
        switch (numConect) {
            case 0://Sin conectores
                path = new Path(
                        new BezierLine(
                                comvertedPose(start),
                                comvertedPose(end)));
                break;

            case 1://Un conector
                path = new Path(
                        new BezierCurve(
                                comvertedPose(start),
                                conectorPonit(start, end, 1),
                                comvertedPose(end)));
                break;
            case 2://Dos conectores
                path = new Path(
                        new BezierCurve(
                                comvertedPose(start),
                                conectorPonit(start, end, 1), conectorPonit(start, end, 2),
                                comvertedPose(end)));
                break;

        }
    }//Asigna un valor a path según numConect(#conectores)

    public PathChain createChain(Path path1,Path path2,Path path3,Path path4,Path path5,Path path6){
        return follower.pathBuilder()
                .addPath(path1)
                .setLinearHeadingInterpolation(path1.getFirstControlPoint().getR(),
                        path1.getSecondControlPoint().getR())

                .addPath(path2)
                .setLinearHeadingInterpolation(path2.getFirstControlPoint().getR(),
                        path2.getSecondControlPoint().getR())

                .addPath(path3)
                .setLinearHeadingInterpolation(path3.getFirstControlPoint().getR(),
                        path3.getSecondControlPoint().getR())

                .addPath(path4)
                .setLinearHeadingInterpolation(path4.getFirstControlPoint().getR(),
                        path4.getSecondControlPoint().getR())

                .addPath(path5)
                .setLinearHeadingInterpolation(path5.getFirstControlPoint().getR(),
                        path5.getSecondControlPoint().getR())

                .addPath(path6)
                .setLinearHeadingInterpolation(path6.getFirstControlPoint().getR(),
                        path6.getSecondControlPoint().getR())
                .build();
    }
    public PathChain createChain(Path path1,Path path2,Path path3,Path path4,Path path5,Path path6,Path path7){
        return follower.pathBuilder()
                .addPath(path1)
                .setLinearHeadingInterpolation(path1.getFirstControlPoint().getR(),
                        path1.getSecondControlPoint().getR())

                .addPath(path2)
                .setLinearHeadingInterpolation(path2.getFirstControlPoint().getR(),
                        path2.getSecondControlPoint().getR())

                .addPath(path3)
                .setLinearHeadingInterpolation(path3.getFirstControlPoint().getR(),
                        path3.getSecondControlPoint().getR())

                .addPath(path4)
                .setLinearHeadingInterpolation(path4.getFirstControlPoint().getR(),
                        path4.getSecondControlPoint().getR())

                .addPath(path5)
                .setLinearHeadingInterpolation(path5.getFirstControlPoint().getR(),
                        path5.getSecondControlPoint().getR())

                .addPath(path6)
                .setLinearHeadingInterpolation(path6.getFirstControlPoint().getR(),
                        path6.getSecondControlPoint().getR())

                .addPath(path7)
                .setLinearHeadingInterpolation(path7.getFirstControlPoint().getR(),
                        path7.getSecondControlPoint().getR())

                .build();
    }

}*/


