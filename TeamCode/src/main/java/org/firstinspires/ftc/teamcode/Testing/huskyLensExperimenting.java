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
        int ref_width = 0;
        boolean found = false;
        int delta_y = 0;
        int delta_x = 0;
        while(opModeIsActive()){
            if (!rateLimit.hasExpired()) {
                continue;
            }
            rateLimit.reset();
            HuskyLens.Block[] blocks = huskyLens.blocks();
            for (int i = 0; i < blocks.length; i++) {
                if (!found){
                    ref_height = blocks[i].height;
                    ref_width = blocks[i].width;
                    found = true;
                }
                delta_y = Math.abs(blocks[i].height-ref_height);
                delta_x =  blocks[i].width - Math.abs(blocks[i].width-ref_width);
                degree = (int) Math.toDegrees(Math.atan((double)
                        delta_y / delta_x));
                telemetry.addData("Degree:", degree);
                telemetry.addData("width", blocks[i].width);
                telemetry.addData("ref height", ref_height);
                telemetry.addData("ref width", ref_width);
                telemetry.addData("delta y", delta_y);
                telemetry.addData("delta x", delta_x);
            }
            telemetry.update();
        }
    }
}