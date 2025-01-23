package org.firstinspires.ftc.teamcode.subSystems;

// Imports.

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.modules.ServoProps;

@Config
public class Claw {
    // Servo positions.
    public static double OPENED_POSITION = 0.27; // Opened claw position.
    public static double CLOSED_POSITION = 0.54; // Closed claw position.

    private static Servo claw; // Servo (starting position: claw: 0).

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
     * @return - If the claw is opened.
     */
    public static boolean isOpen() {
        return ServoProps.isServoInPosition(claw, OPENED_POSITION);
    }

    public static double getPosition() {
        return claw.getPosition();
    }
}
