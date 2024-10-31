package org.firstinspires.ftc.teamcode;

// Imports.

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.modules.ServoProps;

public class DifferentialArm {
    private static final int SERVO_AMOUNT = 2; // Amount of servos used.
    private static final Servo[] servos = new Servo[SERVO_AMOUNT]; // Servos array.
    private static final ServoProps RIGHT_SERVO = new ServoProps(180, 0.5, 1); // Right's servo props.
    private static final ServoProps LEFT_SERVO = new ServoProps(180, 0.5, 1); // Left's servo props.
    private static final int RIGHT = 0; // Right's servo index.
    private static final int LEFT = 1; // Left's servo index.

    // Angle for moving the differential arm to the specimen unloading position.
    private static final int ANGLE_SPECIMEN_UNLOAD = 90;

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
        servos[RIGHT].setDirection(Servo.Direction.REVERSE); // Reversing right servo's direction so that both servos can get the same angle value.

        // Moving Servos to their starting position.
        reset();
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
     * Get the values of the right servo properties object.
     *
     * @return - The right servo properties.
     */
    public static ServoProps getRightServo() {
        return RIGHT_SERVO;
    }

    /**
     * Get the values of the left servo properties object.
     *
     * @return - The left servo properties.
     */
    public static ServoProps getLeftServo() {
        return LEFT_SERVO;
    }

    /**
     * Moves differential arm to the specimen unload position.
     * The action set the servos position once in a loop until the moved value is changed.
     */
    public static void unload() {
        if (isReseted()) {
            servos[RIGHT].setPosition(RIGHT_SERVO.getServoTargetPosition(ANGLE_SPECIMEN_UNLOAD));
            servos[LEFT].setPosition(LEFT_SERVO.getServoTargetPosition(ANGLE_SPECIMEN_UNLOAD));
        }
    }

    /**
     * Resets differential arm to it's starting position.
     * The action set the servos position once in a loop until the reseted value is changed.
     */
    public static void reset() {
        if (!isReseted()) {
            servos[RIGHT].setPosition(RIGHT_SERVO.getServoTargetPosition(0));
            servos[LEFT].setPosition(LEFT_SERVO.getServoTargetPosition(0));
        }
    }

    /**
     * Checks if the differential arm is in the reseted position.
     *
     * @return - If the differential arm is reseted.
     */
    private static boolean isReseted() {
        return ServoProps.isServoInPosition(servos[RIGHT], 0);
    }
}
