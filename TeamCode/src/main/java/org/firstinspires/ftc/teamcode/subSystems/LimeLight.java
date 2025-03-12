package org.firstinspires.ftc.teamcode.subSystems;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class LimeLight {
    public static Limelight3A limelight;
    public static int angle;
    public static int distance;

    public static final int YELLOW = 0;
    public static final int BLUE = 1;
    public static final int RED = 2;

    public static final int driveP = 0;
    public static final int driveI = 0;
    public static final int driveD = 0;
    private static PIDController driveController; // PID controller.

    public static void initialize(OpMode opMode) {
        limelight = opMode.hardwareMap.get(Limelight3A.class, "limelight");

        limelight.setPollRateHz(75);
        driveController = new PIDController(driveP, driveI, driveD);
    }

    public static void pipeLineSwitch(pipeLine pipeLineIndex) {
        switch (pipeLineIndex) {
            case YELLOW:
                limelight.pipelineSwitch(YELLOW);
                break;
            case RED:
                limelight.pipelineSwitch(RED);
                break;
            case BLUE:
                limelight.pipelineSwitch(BLUE);
                break;
        }
    }

    public static int getAngle() {
        limelight.start();
        LLResult result = limelight.getLatestResult();
        if (result != null) {
            angle = (int) result.getPythonOutput()[5];
            return angle;
        }
        return angle;
    }

    public static int getPipeline() {
        return limelight.getStatus().getPipelineIndex();
    }

    public static void drivePID() {
        driveController.setPID(driveP, driveI, driveD);
        LLResult result = limelight.getLatestResult();

        // Calculate motor power.
        double power = driveController.calculate(result.getTx());
        Drivetrain.moveHorizontally(power);
    }

    public static int getDistance() {
        return distance;
    }

    public enum pipeLine {
        YELLOW,
        BLUE,
        RED
    }
}
