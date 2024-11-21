package org.firstinspires.ftc.teamcode.modules;

// Imports.

public class MotorProps {
    protected final double ENCODER_RESOLUTION; // Amount of encoders in one motor shaft rotation..
    protected final double OUTER_GEAR_RATIO; // Number of motor shaft rotations to one final gear rotation.
    protected final double ENCODER_TO_DEGREE; // Encoders in one degree of movement.

    /**
     * Default constructor for standard motor.
     * A 19.2 motor, 5203.
     */
    public MotorProps() {
        this.ENCODER_RESOLUTION = 537.7;
        this.OUTER_GEAR_RATIO = 1;
        this.ENCODER_TO_DEGREE = ENCODER_RESOLUTION / 360;
    }

    /**
     * Non standard constructor.
     *
     * @param ENCODER_RESOLUTION - Encoders needed for one shaft rotation.
     * @param OUTER_GEAR_RATIO    - Gear ratio of outer motor shaft rotation to one final gear rotation.
     */
    public MotorProps(double ENCODER_RESOLUTION, double OUTER_GEAR_RATIO) {
        this.ENCODER_RESOLUTION = ENCODER_RESOLUTION;
        this.OUTER_GEAR_RATIO = OUTER_GEAR_RATIO;
        this.ENCODER_TO_DEGREE = ENCODER_RESOLUTION / 360 * OUTER_GEAR_RATIO;
    }

    public double getENCODER_RESOLUTION() {
        return ENCODER_RESOLUTION;
    }

    public double getOUTER_GEAR_RATIO() {
        return OUTER_GEAR_RATIO;
    }

    public double getENCODER_TO_DEGREE() {
        return ENCODER_TO_DEGREE;
    }

    /**
     * Converts degrees of movement to encoders.
     *
     * @param angle - Degrees of movement.
     * @return - Number encoders needed for moving the amount of degrees given.
     */
    public int getAngleToEncoder(double angle) {
        return (int) (angle * ENCODER_TO_DEGREE);
    }
}