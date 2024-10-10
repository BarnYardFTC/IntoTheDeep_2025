package org.firstinspires.ftc.teamcode;

// Imports.
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class TempClaw {

    // Servo (starting position: claw: 0).
    private static Servo claw; // ToDo: Why not make this variable final?

    // ColorRangeSensor.
    private static ColorRangeSensor distanceSensor; // ToDo: Why not make this variable final?

    // Servo positions.
    private static final double OPENED_CLAW = 0.0; // ToDo: OPENED_POS would be more clear
    private static final double CLOSED_CLAW = 0.0; // ToDo: CLOSED_POS would be more clear

    // Variables.
    static private final int COLLECTION_DISTANCE = 5;
    static private boolean specimenCollected;

    // Initializing.
    public static void init(Servo claw, ColorRangeSensor distanceSensor) {
        // Assigning objects to variables.
        // ToDo: Unclear Inconsistency. Here: TempClaw.clas. In TempDifferential: servos[0] (without the name of the class),
        TempClaw.claw = claw;
        TempClaw.distanceSensor = distanceSensor;

        // Moving Servo to starting position.g
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
        if (getProximityValue() == COLLECTION_DISTANCE) { // ToDo: Shouldn't it be <= instead of == ?
            closeClaw();
            specimenCollected = true;
        }
    }

    // Get distance from sensor.
    public static int getProximityValue() {
        int proximityValue = (int) distanceSensor.getDistance(DistanceUnit.MM);
        return proximityValue;
    }
}
