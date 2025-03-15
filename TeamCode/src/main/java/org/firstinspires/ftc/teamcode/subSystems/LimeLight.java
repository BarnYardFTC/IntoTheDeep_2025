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

    private static final double H1 = 17.5; // CM
    private static final double H2 = 3.8; // CM
    private static final double A1 = -45;
    private static final int LIFT_EXTENSION = 4;
    private static double trackSample = 0;
    private LLResult result;

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
        limelight.pipelineSwitch(YELLOW);

        runLimeLight();
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

    public static void startTracking() {
        trackSample = 1;
    }

    public static void stopTracking() {
        trackSample = 0;
    }

    public static int getAngle() {
        if (limelight.getLatestResult() == null) {
            return 0;
        }
        return angle = (int) limelight.getLatestResult().getPythonOutput()[3];
    }

    public static int getPipeline() {
        return limelight.getStatus().getPipelineIndex();
    }

    public static boolean isCenterX() {
        if (limelight.getLatestResult() == null) {
            return false;
        }
        double tx = limelight.getLatestResult().getTx();
        return tx <= 0.5 && tx >= -0.5;
    }

    public static boolean isCenterY() {
        if (limelight.getLatestResult() == null) {
            return false;
        }
        double ty = limelight.getLatestResult().getTy();
        return ty <= 0.5 && ty >= -0.5;
    }

    public static void drivePID() {
        driveController.setPID(driveP, driveI, driveD);
        double tx;

        if (limelight.getLatestResult() == null) {
            Drivetrain.changeHeading(0);
        }
        else {
            tx = limelight.getLatestResult().getTx();
            if (tx <= 0.5 || tx >= -0.5) {
                Drivetrain.changeHeading(0);
            }
            else {
                double power = driveController.calculate(tx);
                Drivetrain.changeHeading(power);
            }
        }
    }

    public static double getDistance() {
        double a2;

        if (limelight.getLatestResult() == null) {
            return 0;
        }
        else {
            a2 = limelight.getLatestResult().getTy();
            if (a2 <= 0.5 && a2 >= -0.5) {
                return 0;
            }
            else {
                double angleToGoal = Math.toRadians(A1 + a2);
                distance = (int) ((H2 - H1) / Math.tan(angleToGoal));
                return distance;
            }
        }
    }

    public static void limelightUpdate() {
        limelight.updatePythonInputs(
                new double[] {0, trackSample, 0, 0, 0, 0, 0, 0}
        );
    }

    public static class MoveLift implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            Lift.move(Lift.getTargetPos() + getDistance());
            return false;
        }
    }

    public static Action moveLift(){
        return new MoveLift();
    }

    public static class MoveDifferential implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            Differential.move(LimeLight.getAngle(), 0);
            return false;
        }
    }

    public static Action moveDifferential(){
        return new MoveDifferential();
    }

    public static class MoveChassis implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            drivePID();
            return !isCenterX();
        }
    }

    public static Action moveChassis(){
        return new MoveChassis();
    }

    public static class UpdateInputs implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            limelightUpdate();
            return true;
        }
    }

    public static Action updateInputs(){
        return new UpdateInputs();
    }

    public static class StartTracking implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            startTracking();
            return false;
        }
    }

    public static Action startTrackingAction(){
        return new StartTracking();
    }

    public static class StopTracking implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            stopTracking();
            return false;
        }
    }

    public static Action stopTrackingAction(){
        return new StartTracking();
    }

    public static Action autoCollection() {
        return new ParallelAction(
            updateInputs(),
            new SequentialAction(
                Differential.moveToLimeLightAction(),
                startTrackingAction(),
                moveLift(),
                moveDifferential(),
                stopTrackingAction(),
                Robot.sleep(100),
                Claw.closeClaw(),
                Robot.sleep(100),
                Robot.reset()
            )
        );
    }

    public enum pipeLine {
        YELLOW,
        BLUE,
        RED
    }
}
