package org.firstinspires.ftc.teamcode.subSystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class LimeLight {
    public static Limelight3A limelight;

    private static final double h1 = 16.5;
    private static final double h2 = 3.8;
    private static final int a1 = 45;

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

    public static LLResult runLimeLight() {
        limelight.start();
        LLResult result = limelight.getLatestResult();
        return result;
    }

    public static int getAngle(LLResult result) {
        if (result != null) {
            angle = (int) result.getPythonOutput()[5];
            return angle;
        }
        return angle;
    }

    public static int getPipeline() {
        return limelight.getStatus().getPipelineIndex();
    }

    public static boolean isCenter(LLResult result) {
        double tx = result.getTx();
        double ty = result.getTy();
        if (tx <= 0.5 && tx >= -0.5 && ty <= 0.5 && ty >= -0.5) {
            return true;
        }
        return false;
    }

    public static void drivePID(LLResult result) {
        driveController.setPID(driveP, driveI, driveD);
        double tx = result.getTx();

        // Calculate motor power.
        if (tx <= 0.5 && tx >= -0.5) {
            double power = driveController.calculate(tx);
            Drivetrain.moveHorizontally(power);
        }
        else {
            Drivetrain.moveHorizontally(0);
        }
    }

    public static int getDistance(LLResult result) {
        double a2 = result.getTy();
        if (a2 <= 0.5 && a2 >= -0.5) {
            return 0;
        }
        double angleToGoal = Math.toRadians(a1 + a2);
        distance = (int) ((h2 - h1) / Math.tan(angleToGoal));

        return distance;
    }

    public static class MoveLift implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            Lift.move(getDistance(runLimeLight()));
            return false;
        }
    }

    public static Action moveLift(){
        return new MoveLift();
    }

    public static class MoveChassis implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            drivePID(runLimeLight());
            return true;
        }
    }

    public static Action moveChassis(){
        return new MoveChassis();
    }

    public enum pipeLine {
        YELLOW,
        BLUE,
        RED
    }
}
