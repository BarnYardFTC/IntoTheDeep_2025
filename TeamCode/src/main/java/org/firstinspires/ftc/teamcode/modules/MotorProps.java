package org.firstinspires.ftc.teamcode.modules;

// Imports.

public class MotorProps {
    private final double encoderResolution; // Encoder resolution of a motor.
    private final double outerGearRatio; // Ratio of outer motor shaft rotation to one final gear rotation.
    private final double encoderToDegree; // Convert the gear rotation to degrees.

    /**
     * Default constructor for standard motor.
     */
    public MotorProps() {
        this.encoderResolution = 537.7;
        this.outerGearRatio = 1;
        this.encoderToDegree = outerGearRatio / 360;
    }

    /**
     * Non standard constructor.
     * @param encoderResolution - Encoders needed for one shaft rotation.
     * @param outerGearRatio - Gear ratio of outer motor shaft rotation to one final gear rotation.
     */
    public MotorProps(double encoderResolution, double outerGearRatio) {
        this.encoderResolution = encoderResolution;
        this.outerGearRatio = outerGearRatio;
        this.encoderToDegree = encoderResolution / 360/ outerGearRatio;
    }

    public int getAngleToEncoder(double angle) {
        return (int) (angle * encoderToDegree);
    }
}