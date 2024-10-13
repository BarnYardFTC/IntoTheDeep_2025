package org.firstinspires.ftc.teamcode;

// Imports.

import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class TempClaw {
    // Variables.
    static private final int COLLECTION_DISTANCE = 5;

    // Servo positions.
    private static final double OPENED_POSITION = 0.0;
    private static final double CLOSED_POSITION = 0.0;

    // Servo (starting position: claw: 0).
    private static Servo claw;

    // ColorRangeSensor.
    private static ColorRangeSensor distanceSensor;

    // Variables.
    static private boolean specimenCollected;

    // Initializing.
    public static void init(Servo clawConfig, ColorRangeSensor distanceSensorConfig) {
        // Assigning objects to variables.
        claw = clawConfig;
        distanceSensor = distanceSensorConfig;

        // Moving Servo to starting position.g
        openClaw();
    }

    // Get if specimenCollected.
    public static boolean getSpecimenCollected() {
        return specimenCollected;
    }

    // Set if specimenCollected.
    public static void setSpecimenCollected(boolean collected) {
        specimenCollected = collected;
    }

    // Open claw.
    public static void openClaw() {
        claw.setPosition(OPENED_POSITION);
        specimenCollected = false;
    }

    // Close claw.
    public static void closeClaw() {
        claw.setPosition(CLOSED_POSITION);
    }

    // Automated closure of claw.
    public static void collectSpecimen() {
        if (getProximityValue() <= COLLECTION_DISTANCE) {
            closeClaw();
            specimenCollected = true;
        }
    }

    // Get distance from sensor.
    public static int getProximityValue() {
        int proximityValue;
        proximityValue = (int) distanceSensor.getDistance(DistanceUnit.MM);
        return proximityValue;
    }
}
