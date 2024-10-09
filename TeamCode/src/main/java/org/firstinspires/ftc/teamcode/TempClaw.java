package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class TempClaw {
    // Servo (starting positions: claw: 0)
    public static Servo claw;
    static private ColorRangeSensor distanceSensor;
    // Servo positions
    public static final double openedClaw = 0.0;
    public static final double closedClaw = 0.0;
    // parameters
    static private final int collectionDistance = 5;
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
    public static void openClaw(){claw.setPosition(openedClaw); specimenCollected = false;}
    // Close claw
    public static void closeClaw(){claw.setPosition(closedClaw);}
    // Get distance from sensor
    public static int getProximityValue() {return (int) distanceSensor.getDistance(DistanceUnit.MM);}
    // Automated closure of claw
    public static void collectSpecimen() {if (getProximityValue() == collectionDistance) {closeClaw(); specimenCollected = true;}}
}
