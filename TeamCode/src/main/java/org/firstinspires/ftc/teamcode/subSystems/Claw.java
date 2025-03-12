package org.firstinspires.ftc.teamcode.subSystems;

// Imports.

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.modules.ServoProps;

@Config
public class Claw {
    // Servo positions.
    public static double OPENED_POSITION = 0.25; // Opened claw position.
    public static double CLOSED_POSITION = 0.6; // Closed claw position.

    private static Servo claw; // Servo (starting position: claw: 0).

    // Time it takes for the claw to close/open (Milliseconds)
    public static int CLAW_MOVEMENT_DURATION = 200;


    public static void initialize(OpMode opMode) {
        claw = opMode.hardwareMap.get(Servo.class, "claw");
    }

    /**
     * Open claw.
     */
    public static void open() {
        claw.setPosition(OPENED_POSITION);
    }

    /**
     * Close claw.
     */
    public static void close() {
        claw.setPosition(CLOSED_POSITION);
    }

    /**
     * Checks if the claw is in the opened position.
     *
     * @return true if the claw is opened.
     */
    public static boolean isOpen() {
        return ServoProps.isServoInPosition(claw, OPENED_POSITION);
    }

    /**
     * Check if the claw is in the closed position.
     *
     * @return - true if the claw is closed
     */
    public static boolean isClose() {
        return ServoProps.isServoInPosition(claw, CLOSED_POSITION);
    }

    public static double getPosition() {
        return claw.getPosition();
    }


    /**
     * Autonomous Actions - Actions which can be used in the autonomous programs.
     */

    public static class OpenClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            open();
            return !isOpen();
        }
    }
    public static Action openClaw() {
        return new OpenClaw();
    }

    public static class CloseClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            close();
            return !isClose();
        }
    }
    public static Action closeClaw(){
        return new CloseClaw();
    }
}
