package org.firstinspires.ftc.teamcode.subSystems;

// Imports.

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.modules.ServoProps;

public class IntakeArm {
    private static final int SERVO_AMOUNT = 2; // Amount of servos used.
    private static final Servo[] servos = new Servo[SERVO_AMOUNT]; // Servos array.
    private static final ServoProps RIGHT_SERVO = new ServoProps(180, 0.5, 1); // Right's servo props.
    private static final ServoProps LEFT_SERVO = new ServoProps(180, 0.5, 1); // Left's servo props.
    private static final int RIGHT = 0; // Right's servo index.
    private static final int LEFT = 1; // Left's servo index.

    // Angle for moving the intake arm to the intake position.
    private static final int ANGLE_INTAKE = 30;

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
    }

    /**
     * Get the value of the ANGLE_INTAKE parameter.
     *
     * @return - The ANGLE_INTAKE value.
     */
    public static int getAngleIntake() {
        return ANGLE_INTAKE;
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
     * Moves intake arm to the intake position.
     * The action set the servos position once in a loop until the moved value is changed.
     */
    public static void collect() {
        if (isReseted()) {
            servos[RIGHT].setPosition(RIGHT_SERVO.getServoTargetPosition(ANGLE_INTAKE));
            servos[LEFT].setPosition(LEFT_SERVO.getServoTargetPosition(ANGLE_INTAKE));
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
     * Checks if the intake arm is in the reseted position.
     *
     * @return - If the intake arm is reseted.
     */
    private static boolean isReseted() {
        return ServoProps.isServoInPosition(servos[RIGHT], 0);
    }
}
