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
        boolean found = false;
        double w0 = 7, h0 = 3;
        double w1 = 7, h1 = 3;
        double degree = 0;
        while(opModeIsActive()){
            if (!rateLimit.hasExpired()) {
                continue;
            }
            rateLimit.reset();
            HuskyLens.Block[] blocks = huskyLens.blocks();
            for (int i = 0; i < blocks.length; i++) {
//                if (!found) {
//                    w0 = blocks[i].width;
//                    h0 = blocks[i].height;
//                    found = true;
//                    continue;
//                }
                w1 = blocks[i].width;
                h1 = blocks[i].height;
                if (w0 == h1 && h0 == w1){
                    degree = 90;
                }
                else if (w0 == w1 && h0 == h1){
                    degree = 0;
                }
                else {
                    degree = Math.toDegrees(Math.atan(
                            Math.abs( (w0-h0*w1/h1) / (w0*w1/h1-h0) )
                    ));
                }
            }
            telemetry.addData("w0", w0);
            telemetry.addData("h0", h0);
            telemetry.addData("w1", w1);
            telemetry.addData("h1", h1);
            telemetry.addData("degree", degree);
            telemetry.update();
        }
    }
}