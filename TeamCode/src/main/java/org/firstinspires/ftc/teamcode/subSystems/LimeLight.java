package org.firstinspires.ftc.teamcode.subSystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Config
@TeleOp(name = "Limelight3A")
public class LimeLight extends LinearOpMode {
    int angle;

    @Override
    public void runOpMode() throws InterruptedException {
        Limelight3A limelight = hardwareMap.get(Limelight3A.class, "limelight");
        Differential.initialize(this);

        limelight.pipelineSwitch(9);

        telemetry.setMsTransmissionInterval(11);

        limelight.start();

        waitForStart();

        while (opModeIsActive()) {
            limelight.start();
            LLResult result = limelight.getLatestResult();

            if (result != null) {
                angle = (int) result.getPythonOutput()[5];
                telemetry.addData("angle", angle);
            } else {
                telemetry.addData("Limelight", "No data available");
            }

            telemetry.update();
        }
        limelight.stop();
    }
}
