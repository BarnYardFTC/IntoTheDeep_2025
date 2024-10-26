package org.firstinspires.ftc.teamcode.modules;

// Imports.

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoProps {
    private int maxRotation; // Max rotation of the servo.
    private double startPosition; // Servo's start position.
    private double gearRatio; // The ratio between the part we want to move and the servo's rotation. we calculate it by dividing one rotation of the part by the amount of rotations the servo does for one rotation of the part.

    /**
     * Default constructor for standard servo.
     */
    public ServoProps() {
        this(355, 0, 1);
    }

    /**
     * For non standard servo.
     *
     * @param maxRotation   - Max rotation of the servo.
     * @param startPosition - Servo's start position.
     * @param gearRatio     - The ratio between the part we want to move and the servo's rotation. we calculate it by dividing one rotation of the part by the amount of rotations the servo does for one rotation of the part.
     */
    public ServoProps(int maxRotation, double startPosition, double gearRatio) {
        if (maxRotation <= 0)
            throw new IllegalArgumentException("Max rotation must be greater than zero.");
        if (gearRatio <= 0)
            throw new IllegalArgumentException("Gear ratio must be greater than zero.");
        this.gearRatio = gearRatio;
        this.maxRotation = maxRotation;
        this.startPosition = startPosition;
    }

    /**
     * Checks if a servo reached its position while ignoring any deviation.
     * Only the the 3 digits after the decimal point are checked.
     *
     * @param servo    - Servo which we check its position.
     * @param position - Target position which we check, the value is received as the servo position we want but with no deviation.
     * @return - If the servo reached the target angle (if the values of the servo rounded position and the rounded target position are equal).
     */
    public static boolean isServoInPosition(Servo servo, double position) {
        int servoRoundedPos = (int) (servo.getPosition() * 1000);
        int roundedTargetPos = (int) (position * 1000);
        return servoRoundedPos == roundedTargetPos;
    }

    /**
     * Checks if analog is a given angle.
     *
     * @param analogInput - Analog sensor which is part of the servo and can give the servo's current angle, the max voltage of the analog sensor is 3.3.
     * @param angle       - The target angle.
     * @return - If the servo reached the target angle (if the values of the parameters are equal).
     */
    public static boolean isAnalogInPosition(AnalogInput analogInput, int angle) {
        int analogInputAngle = (int) (analogInput.getVoltage() / 3.3 * 360);
        return analogInputAngle == angle;
    }

    /**
     * A positive angle is measured counter clockwise.
     * This function gets the angle for the objects movement so some servos may need to get the wanted angle multiplied by -1 (opposite direction).
     * Sets the part moved by the servo to an angle relative to the servo's start position.
     * For example: if a servo starts at a position of 0, max rotation of 360 degrees, a rotation ratio of 1, and we want it the part to be in move 180 degrees.
     * The calculation is: 0 + 180 / 360 / 1. it will move the servo to the position os 0.5 which is also 180 degrees.
     *
     * @param angle - Wanted end angle of the servo.
     * @param servo - Servo moved.
     */
    public void move(int angle, Servo servo) {
        servo.setPosition(this.startPosition + (double) angle / this.maxRotation / this.gearRatio);
    }

    /**
     * Get the value of the maxRotation parameter.
     *
     * @return - The servo's max rotation.
     */
    public int getMaxRotation() {
        return maxRotation;
    }

    /**
     * Set the value of the maxRotation parameter while checking if a given max rotation is above zero, if not it throws an exception.
     *
     * @param maxRotation - The servo's max rotation.
     */
    public void setMaxRotation(int maxRotation) {
        if (maxRotation <= 0)
            throw new IllegalArgumentException("Max rotation must be greater than zero.");
        this.maxRotation = maxRotation;
    }

    /**
     * Get the value of the startPosition parameter.
     *
     * @return - The servo's start position.
     */
    public double getStartPosition() {
        return startPosition;
    }

    /**
     * Set the value of the startPosition parameter.
     *
     * @param startPosition - The servo's start position.
     */
    public void setStartPosition(double startPosition) {
        this.startPosition = startPosition;
    }

    /**
     * Get the value of the gearRatio parameter.
     *
     * @return - The servo's gear ratio.
     */
    public double getGearRatio() {
        return gearRatio;
    }

    /**
     * Set the value of the gearRatio parameter while checking if a given gear ratio is above zero, if not it throws an exception.
     *
     * @param gearRatio - The servo's gear ratio.
     */
    public void setGearRatio(double gearRatio) {
        if (gearRatio <= 0)
            throw new IllegalArgumentException("Gear ratio must be greater than zero.");
        this.gearRatio = gearRatio;
    }
}