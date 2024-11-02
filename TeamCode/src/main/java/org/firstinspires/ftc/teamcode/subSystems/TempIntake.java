package org.firstinspires.ftc.teamcode.subSystems;

// Imports.

public class TempIntake {
    private static boolean sampleCollected; // State of whether or not a sample is currently collected and is controlled by the robot.

    /**
     * Get the value of the collected parameter.
     *
     * @return - If a specimen was collected and controlled by the robot or not.
     */
    public static boolean getSampleCollected() {
        return sampleCollected;
    }

    /**
     * Set the value of the specimenCollected parameter.
     *
     * @param collected - The state of whether a specimen is collected and controlled by the robot or not.
     */
    public static void setSampleCollected(boolean collected) {
        sampleCollected = collected;
    }
}