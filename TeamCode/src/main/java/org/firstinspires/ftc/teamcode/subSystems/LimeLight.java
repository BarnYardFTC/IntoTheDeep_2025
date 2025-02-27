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
    int corner1X;
    int corner1Y;
    int corner2X;
    int corner2Y;
    int corner3X;
    int corner3Y;
    int corner4X;
    int corner4Y;

    int angle;

    private Limelight3A limelight;

    @Override
    public void runOpMode() throws InterruptedException {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        Differential.initialize(this);

        telemetry.setMsTransmissionInterval(11);

        limelight.pipelineSwitch(9);

        limelight.start();

        telemetry.addData(">", "Robot Ready.  Press Play.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            LLResult result = limelight.getLatestResult();
            if (result != null) {
                if (result.isValid()) {
                    // Access color results
                    List<LLResultTypes.ColorResult> colorResults = result.getColorResults();

                    for (LLResultTypes.ColorResult cr : colorResults) {
                        if (cr.getTargetCorners().size() == 4) {
                            corner1X = (int) (cr.getTargetCorners().get(0).get(0) - cr.getTargetCorners().get(0).get(0) % 1);
                            corner1Y = (int) (cr.getTargetCorners().get(0).get(1) - cr.getTargetCorners().get(0).get(1) % 1);
                            corner2X = (int) (cr.getTargetCorners().get(1).get(0) - cr.getTargetCorners().get(1).get(0) % 1);
                            corner2Y = (int) (cr.getTargetCorners().get(1).get(1) - cr.getTargetCorners().get(1).get(1) % 1);
                            corner3X = (int) (cr.getTargetCorners().get(2).get(0) - cr.getTargetCorners().get(2).get(0) % 1);
                            corner3Y = (int) (cr.getTargetCorners().get(2).get(1) - cr.getTargetCorners().get(2).get(1) % 1);
                            corner4X = (int) (cr.getTargetCorners().get(3).get(0) - cr.getTargetCorners().get(3).get(0) % 1);
                            corner4Y = (int) (cr.getTargetCorners().get(3).get(1) - cr.getTargetCorners().get(3).get(1) % 1);

                            if (corner1X <= corner4X - 10 && corner1X >= corner4X + 10) {
                                angle = 0;
                            }
                            else if (corner1Y <= corner4Y - 10 && corner1Y >= corner4Y + 10) {
                                angle = 90;
                            }
                            else {
                                int newAngle = ((int) Math.toDegrees(Math.atan((double) (corner1Y - corner4Y) / (corner1X - corner4X))));
                                if (newAngle >= angle + 5 || newAngle <= angle - 5) {
                                    angle = newAngle;
                                    if (angle < 0) {
                                        angle = 180 + angle;
                                    }
                                }
                            }

                            if (gamepad1.y) {
                                if (angle - 90 < 0) {
                                    Differential.move(angle + 90, 0);
                                }
                                else {
                                    Differential.move(angle - 90, 0);
                                }
                            }
                        }
                        else {
                            angle = 0;
                        }

                        telemetry.addData("angle", angle);
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
