/*package org.firstinspires.ftc.teamcode.Autonomus.Subsystems;

import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierCurve;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;

public class AutonomusFunctions {

    private Follower follower;

    public final String BLUE_Y = "blueY";
    public final String BLUE_B = "blueB";
    public final String RED_Y = "redY";
    public final String RED_R = "redR";

    public AutonomusPositions pos = new AutonomusPositions();

    public Point conectorPonit(Pose start, Pose end, int numConect){
        Point conector = null;
        if(numConect == 1){
            conector = new Point(start.getY(), end.getX(),Point.CARTESIAN);
        } else if (numConect == 2){
            conector = new Point(end.getY(), start.getX(),Point.CARTESIAN);
        }
        return  conector;
    }

    public Path createPath(Pose start, Pose end, int numConect){
        Path path = null;
        switch (numConect) {
            case 0://Sin conectores
                path = new Path(
                        new BezierLine(
                                new Point(start),
                                new Point(end)));
                break;

            case 1://Un conector
                path = new Path(
                        new BezierCurve(
                                new Point(start),
                                conectorPonit(start, end, 1),
                                new Point(end)));
                break;
            case 2://Dos conectores
                path = new Path(
                        new BezierCurve(
                                new Point(start),
                                conectorPonit(start, end, 1),
                                conectorPonit(start, end, 2),
                                new Point(end)));
                break;
        }
        return path;  // Devuelves el path creado
    }//Crea un path según numConect(#conectores)

    public PathChain Chain(Pose start, Pose end, int ConectorPt) {
        return follower.pathBuilder()
                .addPath(createPath(start, end, ConectorPt))
                .setLinearHeadingInterpolation(start.getHeading(), end.getHeading())
                .build();
    }

    public double getX(PathChain chain){
        Path target = chain.getPath(2);
        Point point = target.getPoint(target.getPathEndTimeoutConstraint());
        return point.getX();
    }

    public double getY(PathChain chain){
        Path target = chain.getPath(2);
        Point point = target.getPoint(target.getPathEndTimeoutConstraint());
        return point.getY();
    }

}*/
