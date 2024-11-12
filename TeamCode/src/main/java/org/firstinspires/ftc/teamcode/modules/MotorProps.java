package org.firstinspires.ftc.teamcode.modules;

// Imports.

public class MotorProps {
    private final double encoderResolution; // Encoder resolution of a motor.
    private final double outerGearRatio; // Ratio of outer motor shaft rotation to one final gear rotation.
    private final double encoderToDegree; // Convert the gear rotation to degrees.

    /**
     * Default constructor for standard servo.
     */
    public MotorProps() {
        this.encoderResolution = 537.7;
        this.outerGearRatio = 1;
        this.encoderToDegree = outerGearRatio / 360;
    }

    public MotorProps(double encoderResolution, double outerGearRatio) {
        this.encoderResolution = encoderResolution;
        this.outerGearRatio = outerGearRatio;
        this.encoderToDegree = encoderResolution / outerGearRatio / 360;
    }

    public double getAngleToEncoder(double angle) {
        return angle * encoderToDegree;
    }
}