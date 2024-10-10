package org.firstinspires.ftc.teamcode;

// Imports.
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class TempClaw {

    // Servo (starting position: claw: 0).
    private static Servo claw;

    // ColorRangeSensor.
    private static ColorRangeSensor distanceSensor;

    // Servo positions.
    private static final double OPENED_CLAW = 0.0;
    private static final double CLOSED_CLAW = 0.0;

    // Variables.
    static private final int COLLECTION_DISTANCE = 5;
    static private boolean specimenCollected;

    // Initializing.
    public static void init(Servo claw, ColorRangeSensor distanceSensor) {
        // Assigning objects to variables.
        TempClaw.claw = claw;
        TempClaw.distanceSensor = distanceSensor;

        // Moving Servo to starting position.
        openClaw();
    }

    // Set if specimenCollected.
    public static void setSpecimenCollected(boolean collected) {
        specimenCollected = collected;
    }

    // Get if specimenCollected.
    public static boolean getSpecimenCollected() {
        return specimenCollected;
    }

    // Open claw.
    public static void openClaw() {
        claw.setPosition(OPENED_CLAW);
        specimenCollected = false;
    }

    // Close claw.
    public static void closeClaw() {
        claw.setPosition(CLOSED_CLAW);
    }

    // Automated closure of claw.
    public static void collectSpecimen() {
        if (getProximityValue() == COLLECTION_DISTANCE) {closeClaw();
            specimenCollected = true;
        }
    }

    // Get distance from sensor.
    public static int getProximityValue() {
        int proximityValue = (int) distanceSensor.getDistance(DistanceUnit.MM);
        return proximityValue;
    }
}
