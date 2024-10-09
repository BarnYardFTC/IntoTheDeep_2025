package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class TempClaw {

    // Servo (starting positions: claw: 0)
    public static Servo claw;
    static private ColorRangeSensor distanceSensor;

    // Servo positions
    public static final double OPENED_CLAW = 0.0;
    public static final double CLOSED_CLAW = 0.0;

    // parameters
    static private final int COLLECTION_DISTANCE = 5;
    static public boolean specimenCollected;

    // Initializing
    public static void init(Servo claw, ColorRangeSensor distanceSensor) {
        // Assigning objects to variables
        TempClaw.claw = claw;
        TempClaw.distanceSensor = distanceSensor;

        // Moving Servo to starting position
        openClaw();
    }

    // Open claw
    public static void openClaw() {
        claw.setPosition(OPENED_CLAW);
        specimenCollected = false;
    }

    // Close claw
    public static void closeClaw() {
        claw.setPosition(CLOSED_CLAW);
    }

    // Get distance from sensor
    public static int getProximityValue() {
        int proximityValue = (int) distanceSensor.getDistance(DistanceUnit.MM);
        return proximityValue;
    }

    // Automated closure of claw
    public static void collectSpecimen() {
        if (getProximityValue() == COLLECTION_DISTANCE) {closeClaw();
            specimenCollected = true;
        }
    }
}
