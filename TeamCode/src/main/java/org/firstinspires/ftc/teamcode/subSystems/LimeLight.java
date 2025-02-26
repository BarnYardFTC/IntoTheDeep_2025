package org.firstinspires.ftc.teamcode.subSystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

@TeleOp(name = "Limelight3A")
public class LimeLight extends LinearOpMode{
    private Limelight3A limelight;

    @Override
    public void runOpMode() throws InterruptedException {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        telemetry.setMsTransmissionInterval(11);

        limelight.pipelineSwitch(9);

        limelight.start();

        telemetry.addData(">", "Robot Ready.  Press Play.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            LLResult result = limelight.getLatestResult();

            LLStatus status = limelight.getStatus();
            telemetry.addData("Pipeline", "Index: %d, Type: %s",
                    status.getPipelineIndex(), status.getPipelineType());
            if (result != null) {
                if (result.isValid()) {
                    // Access color results
                    List<LLResultTypes.ColorResult> colorResults = result.getColorResults();
                    for (LLResultTypes.ColorResult cr : colorResults) {
                        int corner1X;
                        int corner1Y;
                        int corner2X;
                        int corner2Y;

                        double angle;

                        if (cr.getTargetCorners().size() == 4) {
                            corner1X = (int) (cr.getTargetCorners().get(2).get(0) - cr.getTargetCorners().get(2).get(0) % 1);
                            corner1Y = (int) (cr.getTargetCorners().get(2).get(1) - cr.getTargetCorners().get(2).get(1) % 1);
                            corner2X = (int) (cr.getTargetCorners().get(1).get(0) - cr.getTargetCorners().get(1).get(0) % 1);
                            corner2Y = (int) (cr.getTargetCorners().get(1).get(1) - cr.getTargetCorners().get(1).get(1) % 1);

                            if (corner1X == corner2X) {
                                angle = 0;
                            }
                            else if (corner1Y == corner2Y) {
                                angle = 90;
                            }
                            else {
                                angle = Math.toDegrees(Math.atan((double) (corner1Y - corner2Y) / (corner1X - corner2X)));
                                if (angle < 0) {
                                    angle = 180 + angle;
                                }
                            }
                        }
                        else {
                            angle = 0;
                        }

                        telemetry.addData("angle", angle);
                        telemetry.addData("size", cr.getTargetCorners().size());
                        telemetry.addData("corners", cr.getTargetCorners());
                    }
                }
            } else {
                telemetry.addData("Limelight", "No data available");
            }

            telemetry.update();
        }
        limelight.stop();
    }
}
