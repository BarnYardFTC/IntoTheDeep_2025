package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.util.concurrent.TimeUnit;

@TeleOp
public class huskyLensExperimenting extends LinearOpMode {

    private final int READ_PERIOD = 1;
    private HuskyLens huskyLens;
    @Override
    public void runOpMode() throws InterruptedException {
        huskyLens = hardwareMap.get(HuskyLens.class, "huskyLens");
        Deadline rateLimit = new Deadline(READ_PERIOD, TimeUnit.SECONDS);
        rateLimit.expire();
        if (!huskyLens.knock()) {
            telemetry.addData(">>", "Problem communicating with " + huskyLens.getDeviceName());
        } else {
            telemetry.addData(">>", "Press start to continue");
        }
        huskyLens.selectAlgorithm(HuskyLens.Algorithm.COLOR_RECOGNITION);
        telemetry.update();
        waitForStart();
        int degree = 0;
        int ref_height = 0;
        boolean found = false;
        double delta_height = 0;
        final double HEIGHT_TO_DEGREE = 30;
        int ref_area = 0;
        int cur_area = 0;
        while(opModeIsActive()){
            if (!rateLimit.hasExpired()) {
                continue;
            }
            rateLimit.reset();
            HuskyLens.Block[] blocks = huskyLens.blocks();
            for (int i = 0; i < blocks.length; i++) {
                if (!found){
                    ref_height = blocks[i].height;
                    ref_area = blocks[i].height*blocks[i].width;
                    found = true;
                }
                cur_area = blocks[i].width*blocks[i].height;
                if (equals(cur_area, ref_area)){
                    delta_height = Math.abs((double) blocks[i].height/ref_height);
                    degree = (int) (delta_height*HEIGHT_TO_DEGREE);
                }
            }
            telemetry.addData("delta height", delta_height);
            telemetry.addData("degree", degree);
            telemetry.update();
        }
    }
    public boolean equals(int n1, int n2){
        int equals_difference = 10;
        if (Math.abs(n1-n2)<=equals_difference){
            return true;
        }
        return false;
    }
}