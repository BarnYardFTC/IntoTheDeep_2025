package org.firstinspires.ftc.teamcode;

// Imports.

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.modules.ServoProps;

public class IntakeArm {
    private static final int SERVO_AMOUNT = 2; // Amount of servos used.
    private static final Servo[] servos = new Servo[SERVO_AMOUNT]; // Servos array.
    private static final ServoProps RIGHT_SERVO = new ServoProps(355, 0.5, 1); // Right's servo props.
    private static final ServoProps LEFT_SERVO = new ServoProps(355, 0.5, 1); // Left's servo props.
    private static final int RIGHT = 0; // Right's servo index.
    private static final int LEFT = 1; // Left's servo index.

    // Angles.
    private static final int ANGLE_INTAKE = 0;

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

        // Moving servos to starting position.
        move(0);
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
     * Move each servo based on a given target angle.
     * The logic for the movement is in the class ServoProps.
     *
     * @param angle - Wanted end angle of the arm.
     */
    public static void move(int angle) {
        RIGHT_SERVO.move(angle, servos[RIGHT]);
        LEFT_SERVO.move(angle, servos[LEFT]);
    }
}
