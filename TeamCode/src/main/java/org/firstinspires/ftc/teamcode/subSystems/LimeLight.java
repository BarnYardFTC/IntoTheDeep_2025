package org.firstinspires.ftc.teamcode.subSystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot;

public class LimeLight {
    public static Limelight3A limelight;

    private static final double H1 = 16.5; // CM
    private static final double H2 = 3.8; // CM
    private static final double A1 = -45;
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
        limelight.pipelineSwitch(YELLOW);

        driveController = new PIDController(driveP, driveI, driveD);

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

    public static int getAngle() {
        if (limelight.getLatestResult() == null) {
            return 0;
        }
        return angle = (int) limelight.getLatestResult().getPythonOutput()[3];
    }

    public static int getPipeline() {
        return limelight.getStatus().getPipelineIndex();
    }

    public static boolean isCenter() {
        if (limelight.getLatestResult() == null) {
            return false;
        }
        double tx = limelight.getLatestResult().getTx();
        double ty = limelight.getLatestResult().getTy();
        return tx <= 0.5 && tx >= -0.5 && ty <= 0.5 && ty >= -0.5;
    }

    public static void drivePID() {
        driveController.setPID(driveP, driveI, driveD);
        double tx;

        if (limelight.getLatestResult() == null) {
            Drivetrain.moveHorizontally(0);
        }
        else {
            tx = limelight.getLatestResult().getTx();
            if (tx <= 0.5 || tx >= -0.5) {
                Drivetrain.moveHorizontally(0);
            }
            else {
                double power = driveController.calculate(tx);
                Drivetrain.moveHorizontally(power);
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
