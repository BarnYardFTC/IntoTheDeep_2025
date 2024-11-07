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

        double degree = 0;
        while(opModeIsActive()){
            if (!rateLimit.hasExpired()) {
                continue;
            }
            rateLimit.reset();
            HuskyLens.Block[] blocks = huskyLens.blocks();
            for (int i = 0; i < blocks.length; i++) {
                degree =Math.atan((double) Math.abs(69-101) / Math.abs(90-167));
                telemetry.addData("Degree:", degree);
                telemetry.addData("X", blocks[i].x);
                telemetry.addData("Y", blocks[i].y);
                telemetry.addData("top", blocks[i].top);
                telemetry.addData("left", blocks[i].left);
            }
            telemetry.update();
        }
    }
}