/*package org.firstinspires.ftc.teamcode.Autonomus.Subsystems;

import android.annotation.SuppressLint;

import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class AutonomusPaths {
    private AutonomusPositions pos = new AutonomusPositions();
    private AutonomusFunctions functions = new AutonomusFunctions();

    private Follower follower;

    /*----------PathChains----------
    // alianza - inicio - sector - secuencia
    public Map<String, Path> blueL_Y_DwUp, blueM_Y_DwUp, blueR_Y_DwUp;
    public List<Path> blueL_Y_MiDw, blueM_Y_MiDw, blueR_Y_MiDw;

    public Queue<PathChain> blueL_B_UpDw, blueM_B_UpDw, blueR_B_UpDw;
    public List<Path> blueL_B_MidUp, blueM_B_MidUp, blueR_B_MidUp;


    public PathChain redL_Y_DwUp, redM_Y_DwUp, redR_Y_DwUp;
    public List<Path> redL_Y_MiDw, redM_Y_MiDw, redR_Y_MiDw;

    public PathChain redL_R_UpDw, redM_R_UpDw, redR_R_UpDw;
    public List<Path> redL_R_MidUp, redM_R_MidUp, redR_R_MidUp;


    @SuppressLint("SuspiciousIndentation")
    public AutonomusPaths(){
        blueL_Y_DwUp.put("pk1", functions.createPath(pos.bStartPos.get("L"), pos.yPickUpPos.get("blueDw"),2) );
        blueL_Y_DwUp.put("score1", functions.createPath(pos.yPickUpPos.get("blueDw"), pos.Scorepos.get("blueB"),0) );
        blueL_Y_DwUp.put("pk2", functions.createPath(pos.Scorepos.get("blueB"), pos.yPickUpPos.get("blueMid"),1) );
        blueL_Y_DwUp.put("score2", functions.createPath(pos.yPickUpPos.get("blueMid"), pos.Scorepos.get("blueB"),0) );
        blueL_Y_DwUp.put("pk3", functions.createPath(pos.Scorepos.get("blueB"), pos.yPickUpPos.get("blueUp"),1) );
        blueL_Y_DwUp.put("score3", functions.createPath(pos.yPickUpPos.get("blueUp"), pos.Scorepos.get("blueB"),0) );

        blueM_B_UpDw = new LinkedList<PathChain>();
            blueM_B_UpDw.add(functions.Chain(pos.bStartPos.get("M"), pos.brPickUpPos.get("blueUp"), 2));
            blueM_B_UpDw.add(functions.Chain(pos.brPickUpPos.get("blueUp"), pos.HumanPlayerPos.get("bluePt"), 1));
            blueM_B_UpDw.add(functions.Chain(pos.HumanPlayerPos.get("bluePt"), pos.HumanPlayerPos.get("bluePk"), 1));
            blueM_B_UpDw.add(functions.Chain(pos.HumanPlayerPos.get("bluePk"), pos.Scorepos.get("blueP"), 0));
        blueM_B_UpDw.add(functions.Chain(pos.Scorepos.get("blueP"), pos.brPickUpPos.get("blueMid"), 1));
        blueM_B_UpDw.add(functions.Chain(pos.brPickUpPos.get("blueMid"), pos.HumanPlayerPos.get("bluePt"), 1));
        blueM_B_UpDw.add(functions.Chain(pos.HumanPlayerPos.get("bluePt"), pos.HumanPlayerPos.get("bluePk"), 1));
        blueM_B_UpDw.add(functions.Chain(pos.HumanPlayerPos.get("bluePk"), pos.Scorepos.get("blueP"), 0));
            blueM_B_UpDw.add(functions.Chain(pos.Scorepos.get("blueP"), pos.brPickUpPos.get("blueDw "), 1));
            blueM_B_UpDw.add(functions.Chain(pos.brPickUpPos.get("blueDw"), pos.HumanPlayerPos.get("bluePt"), 1));
            blueM_B_UpDw.add(functions.Chain(pos.HumanPlayerPos.get("bluePt"), pos.HumanPlayerPos.get("bluePk"), 1));
            blueM_B_UpDw.add(functions.Chain(pos.HumanPlayerPos.get("bluePk"), pos.Scorepos.get("blueP"), 0));
    }
}*/
