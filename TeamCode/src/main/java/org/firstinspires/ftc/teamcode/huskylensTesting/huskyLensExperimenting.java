package org.firstinspires.ftc.teamcode.huskylensTesting;

import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class huskyLensExperimenting extends LinearOpMode {

    private HuskyLens huskyLens;
    @Override
    public void runOpMode() throws InterruptedException {
        huskyLens = hardwareMap.get(HuskyLens.class, "huskyLens");
        if (!huskyLens.knock()) {
            telemetry.addData(">>", "Problem communicating with " + huskyLens.getDeviceName());
        } else {
            telemetry.addData(">>", "Press start to continue");
        }
        huskyLens.selectAlgorithm(HuskyLens.Algorithm.TAG_RECOGNITION);
        telemetry.update();
        waitForStart();
        while(opModeIsActive()){

        }
    }
}