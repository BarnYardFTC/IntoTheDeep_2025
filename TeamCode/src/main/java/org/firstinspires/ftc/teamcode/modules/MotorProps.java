package org.firstinspires.ftc.teamcode.modules;

// Imports.

public class MotorProps {
    private final double encoderResolution; // Amount of encoders in one motor shaft rotation..
    private final double outerGearRatio; // Ratio of outer motor shaft rotation to one final gear rotation.
    private final double encoderToDegree; // Encoders in one degree of movement.

    /**
     * Default constructor for standard motor.
     * A 19.2 motor, 5203.
     */
    public MotorProps() {
        this.encoderResolution = 537.7;
        this.outerGearRatio = 1;
        this.encoderToDegree = encoderResolution / 360;
    }

    /**
     * Non standard constructor.
     *
     * @param encoderResolution - Encoders needed for one shaft rotation.
     * @param outerGearRatio    - Gear ratio of outer motor shaft rotation to one final gear rotation.
     */
    public MotorProps(double encoderResolution, double outerGearRatio) {
        this.encoderResolution = encoderResolution;
        this.outerGearRatio = outerGearRatio;
        this.encoderToDegree = encoderResolution / 360 / outerGearRatio;
    }

    /**
     * Converts degrees of movement to encoders.
     *
     * @param angle - Degrees of movement.
     * @return - Number encoders needed for moving the amount of degrees given.
     */
    public int getAngleToEncoder(double angle) {
        return (int) (angle * encoderToDegree);
    }
}