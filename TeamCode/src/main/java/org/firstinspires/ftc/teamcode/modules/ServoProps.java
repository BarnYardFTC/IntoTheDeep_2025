package org.firstinspires.ftc.teamcode.modules;

// Imports.

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoProps {
    private final int maxRotation; // Max rotation of the servo.
    private final double startPosition; // Servo's start position.
    private final double gearRatio; // The ratio between the part we want to move and the servo's rotation. We calculate it by dividing one rotation of the part by the amount of rotations the servo does for one rotation of the part.

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
     * Checks if analog has a bigger difference than 30 between it's reading and a given angle.
     * 30 is an exaggeration for minor errors.
     * The functions tests a difference and not equality because the sensor is inaccurate.
     *
     * @param analogInput - Analog sensor which is part of the servo and can give the servo's current angle, the max voltage of the analog sensor is 3.3.
     * @param angle       - The target angle.
     * @return - If the servo reached the target angle (if the values of the parameters are equal).
     */
    public static boolean isAnalogInPosition(AnalogInput analogInput, double angle) {
        int analogInputAngle = (int) (analogInput.getVoltage() / 3.3 * 360);
        return Math.abs(analogInputAngle - angle) <= 30;
    }

    /**
     * A positive angle is measured counter clockwise.
     * This function gets the angle for the objects movement so some servos may need to get the wanted angle multiplied by -1 (opposite direction).
     * Calculates the needed position of the servo so the part moved by the servo gets to the wanted angle.
     * For example: if a servo starts at a position of 0, max rotation of 360 degrees, a rotation ratio of 1, and we want it the part to be in move 180 degrees.
     * The calculation is: 0 + 180 / 360 / 1. it will move the servo to the position os 0.5 which is also 180 degrees.
     *
     * @param angle - Wanted end angle of the servo.
     */
    public double getTargetPosition(double angle) {
        if (this.startPosition + angle / this.maxRotation / this.gearRatio <= 1 && this.startPosition + angle / this.maxRotation / this.gearRatio >= 0)
            return this.startPosition + angle / this.maxRotation / this.gearRatio;
        throw new IllegalArgumentException("Servo position must be between 0 and 1.");
    }

    /**
     * Gives the current angle the servo is in.
     *
     * @param pos - Servo's current position.
     * @return - Servo's position in degrees.
     */
    public double getCurrentAngle(double pos) {
        return pos / this.maxRotation / this.gearRatio;
    }
}