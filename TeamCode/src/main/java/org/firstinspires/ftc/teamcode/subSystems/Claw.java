package org.firstinspires.ftc.teamcode.subSystems;

// Imports.

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.modules.ServoProps;

public class Claw {
    private static final int COLLECTION_DISTANCE = 5; // Specimen collection distance.

    // Servo positions.
    private static final double OPENED_POSITION = 1; // Opened claw position.
    private static final double CLOSED_POSITION = 0.0; // Closed claw position.

    private static Servo claw; // Servo (starting position: claw: 0).
    private static ColorRangeSensor distanceSensor; // ColorRangeSensor.
    private static boolean specimenCollected; // State of whether or not a specimen is currently collected and is controlled by the robot.
    private static boolean sampleCollected; // State of whether or not a sample is currently collected and is controlled by the robot.

    /**
     * Initializing.
     *
     * @param clawConfig           - Hardware for claw.
     * @param distanceSensorConfig - Hardware for distance sensor.
     */
    public static void init(Servo clawConfig, ColorRangeSensor distanceSensorConfig) {
        // Assigning objects to variables.
        claw = clawConfig;
        distanceSensor = distanceSensorConfig;
    }

    /**
     * Get the value of the collected specimen parameter.
     *
     * @return - If a specimen was collected and controlled by the robot or not.
     */
    public static boolean isSpecimenCollected() {
        return specimenCollected;
    }

    /**
     * Get the value of the collected sample parameter.
     *
     * @return - If a sample was collected and controlled by the robot or not.
     */
    public static boolean isSampleCollected() {
        return sampleCollected;
    }

    /**
     * Set the value of the collected sample parameter.
     *
     */
    public static void setSampleCollected(boolean sampleCollected) {
        Claw.sampleCollected = sampleCollected;
    }

    /**
     * Open claw.
     */
    public static void open() {
        claw.setPosition(OPENED_POSITION);
        LED.changeColor(RevBlinkinLedDriver.BlinkinPattern.DARK_RED);
        specimenCollected = false;
        sampleCollected = false;
    }

    /**
     * Close claw.
     */
    public static void close() {
        claw.setPosition(CLOSED_POSITION);
        LED.changeColor(RevBlinkinLedDriver.BlinkinPattern.GREEN);
    }

    /**
     * Automated closure of claw when a specimen is close enough.
     */
    public static void collectSpecimen() {
        if (getProximityValue() <= COLLECTION_DISTANCE && !sampleCollected && isOpened()) {
            close();
            Differential.reset();
            specimenCollected = true;
        }
    }

    /**
     * Checks if the claw is in the opened position.
     *
     * @return - If the claw is opened.
     */
    private static boolean isOpened() {
        return ServoProps.isServoInPosition(claw, OPENED_POSITION);
    }

    /**
     * Get the value of the proximity parameter.
     *
     * @return - The value of proximity from distance sensor.
     */
    public static int getProximityValue() {
        int proximityValue;
        proximityValue = (int) distanceSensor.getDistance(DistanceUnit.MM);
        return proximityValue;
    }
}
