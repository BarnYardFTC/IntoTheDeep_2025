package org.firstinspires.ftc.teamcode;

// Imports.

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.modules.ServoProps;

public class DifferentialArm {

    private static final int SERVO_AMOUNT = 2; // Amount of servos used.
    private static final Servo[] servos = new Servo[SERVO_AMOUNT]; // Servos array.
    private static final ServoProps RIGHT_SERVO = new ServoProps(355, 0.5, 1); // Right's servo props.
    private static final ServoProps LEFT_SERVO = new ServoProps(355, 0.5, 1); // Left's servo props.
    private static final int RIGHT = 0; // Right's servo index.
    private static final int LEFT = 1; // Left's servo index.

    // Angles for moving the arm.
    private static final int ANGLE_SPECIMEN_INTAKE = 0;
    private static final int ANGLE_SPECIMEN_UNLOAD = 0;
    private static final int ANGLE_SAMPLE_INTAKE = 0;
    private static final int ANGLE_SAMPLE_UNLOAD = 0;

    /**
     * Initializing all hardware.
     *
     * @param right - Hardware for right servo.
     * @param left  - Hardware for left servo.
     */
    public static void init(Servo right, Servo left) {
        // Assigning objects to variables.
        servos[RIGHT] = right;
        servos[LEFT] = left;

        // Moving Servos to starting position.
        move(0);
    }

    /**
     * Get the value of the ANGLE_SPECIMEN_INTAKE parameter.
     *
     * @return - The ANGLE_SPECIMEN_INTAKE value.
     */
    public static int getAngleSpecimenIntake() {
        return ANGLE_SPECIMEN_INTAKE;
    }

    /**
     * Get the value of the ANGLE_SPECIMEN_UNLOAD parameter.
     *
     * @return - The ANGLE_SPECIMEN_UNLOAD value.
     */
    public static int getAngleSpecimenUnload() {
        return ANGLE_SPECIMEN_UNLOAD;
    }

    /**
     * Get the value of the ANGLE_SAMPLE_INTAKE parameter.
     *
     * @return - The ANGLE_SAMPLE_INTAKE value.
     */
    public static int getAngleSampleIntake() {
        return ANGLE_SAMPLE_INTAKE;
    }

    /**
     * Get the value of the ANGLE_SAMPLE_UNLOAD parameter.
     *
     * @return - The ANGLE_SAMPLE_UNLOAD value.
     */
    public static int getAngleSampleUnload() {
        return ANGLE_SAMPLE_UNLOAD;
    }

    /**
     * Move each servo based on a given target angle.
     * The logic for the movement is in the class ServoProps.
     *
     * @param angle - Wanted end angle of the arm.
     */
    public static void move(int angle) {
        servos[RIGHT].setPosition(RIGHT_SERVO.getServoTargetPosition(angle));
        servos[LEFT].setPosition(LEFT_SERVO.getServoTargetPosition(angle));
    }
}
