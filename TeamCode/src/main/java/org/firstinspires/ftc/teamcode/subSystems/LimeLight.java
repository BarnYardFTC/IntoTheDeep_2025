package org.firstinspires.ftc.teamcode.subSystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Robot;

public class LimeLight {
    public static Limelight3A limelight;

    private static final double H1 = 16.5; // CM
    private static final double H2 = 3.8; // CM
    private static final double A1 = 45;
    private static final int LIFT_EXTENSION = 4;

    public static int angle;
    public static double distance;

    public static final int YELLOW = 0;
    public static final int BLUE = 1;
    public static final int RED = 2;

    public static final int driveP = 0;
    public static final int driveI = 0;
    public static final int driveD = 0;
    private static PIDController driveController; // PID controller.

    public static void initialize(OpMode opMode) {
        limelight = opMode.hardwareMap.get(Limelight3A.class, "limelight");

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

    public static void runLimeLight() {
        limelight.setPollRateHz(75);
        limelight.start();
    }

    public static int getAngle() {
        LLResult result = limelight.getLatestResult();
        if (result != null) {
            angle = (int) result.getPythonOutput()[3];
        }
        return angle;
    }

    public static int getPipeline() {
        return limelight.getStatus().getPipelineIndex();
    }

    public static boolean isCenter() {
        LLResult result = limelight.getLatestResult();
        double tx;
        double ty;
        if (result != null) {
            tx = result.getTx();
            ty = result.getTy();
            return tx <= 0.5 && tx >= -0.5 && ty <= 0.5 && ty >= -0.5;
        }
        return false;
    }

    public static void drivePID() {
        driveController.setPID(driveP, driveI, driveD);
        LLResult result = limelight.getLatestResult();
        double tx;

        if (result != null) {
            tx = result.getTx();
        }
        else {
            tx = 0;
        }

        // Calculate motor power.
        if (tx <= 0.5 && tx >= -0.5) {
            double power = driveController.calculate(tx);
            Drivetrain.moveHorizontally(power);
        }
        else {
            Drivetrain.moveHorizontally(0);
        }
    }

    public static double getDistance() {
        LLResult result = limelight.getLatestResult();
        double a2;

        if (result != null) {
            a2 = result.getTy();
        }
        else {
            return 0;
        }

        if (a2 <= 0.5 && a2 >= -0.5) {
            return 0;
        }
        double angleToGoal = Math.toRadians(A1 + a2);
        distance = (int) ((H2 - H1) / Math.tan(angleToGoal));

        return distance;
    }

    public static class CollectFinal implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            Differential.collectSample();
            Differential.move(getAngle(), 0);
            Lift.move(LIFT_EXTENSION);
            return false;
        }
    }

    public static Action collectFinal(){
        return new CollectFinal();
    }

    public static class MoveLift implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            Lift.move(getDistance());
            return false;
        }
    }

    public static Action moveLift(){
        return new MoveLift();
    }

    public static class MoveChassis implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            drivePID();
            return !isCenter();
        }
    }

    public static Action moveChassis(){
        return new MoveChassis();
    }

    public static Action autoCollection(){
        return new SequentialAction(
            new ParallelAction(
                moveChassis(),
                moveLift()
            ),
            collectFinal(),
            Robot.sleep(100),
            Claw.closeClaw(),
            Robot.reset()
        );
    }

    public enum pipeLine {
        YELLOW,
        BLUE,
        RED
    }
}
