package org.firstinspires.ftc.teamcode.subSystems;

// Imports.

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.modules.ServoProps;

public class DifferentialWrist {
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
    }

    /**
     * Moves differential arm to the specimen unload position.
     * The action set the servos position once in a loop until the moved value is changed.
     */
    public static void unload() {
        servos[RIGHT].setPosition(RIGHT_SERVO.getTargetPosition(ANGLE_SPECIMEN_UNLOAD));
        servos[LEFT].setPosition(LEFT_SERVO.getTargetPosition(ANGLE_SPECIMEN_UNLOAD));
    }

    /**
     * Resets differential arm to it's starting position.
     * The action set the servos position once in a loop until the reseted value is changed.
     */
    public static void reset() {
        servos[RIGHT].setPosition(RIGHT_SERVO.getTargetPosition(0));
        servos[LEFT].setPosition(LEFT_SERVO.getTargetPosition(0));
    }
}
