package org.firstinspires.ftc.teamcode.subSystems;

// Imports.

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.modules.ServoProps;

public class Claw {
    // Servo positions.
    private static final double OPENED_POSITION = 0.2; // Opened claw position.
    private static final double CLOSED_POSITION = 0.0; // Closed claw position.

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
}
