package org.firstinspires.ftc.teamcode;

// Imports.
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.modules.ServoProps;

public class TempIntakeArm {
    // Servos (starting positions: right (reversed): 0.5, left: 0.5).
    private static final int SERVO_AMOUNT = 2;
    private static final Servo[] servos = new Servo[SERVO_AMOUNT];
    private static final int RIGHT = 0;
    private static final int LEFT = 1;

    // Servo start positions.
    private static final double RIGHT_START_POS = 0.0;
    private static final double LEFT_START_POS = 0.0;

    // Servo characteristics.
    private static final int RIGHT_MAX_ROTATION = ServoProps.getMaxRotation();
    private static final int LEFT_MAX_ROTATION = ServoProps.getMaxRotation();

    // Angles.
    private static final int ANGLE_INTAKE = 0;

    // Initializing.
    public static void init(Servo right, Servo left) {
        // Assigning objects to variables.
        servos[RIGHT] = right;
        servos[LEFT] = left;

        // Moving servos to starting position.
        movement(0);
    }

    // Returns angle for intake.
    public static int getAngleIntake() {
        return ANGLE_INTAKE;
    }


    // Moving to an assigned angle.
    public static void movement(int angle) {
        servos[RIGHT].setPosition(RIGHT_START_POS + (double) angle / RIGHT_MAX_ROTATION);
        servos[LEFT].setPosition(LEFT_START_POS + (double) angle / LEFT_MAX_ROTATION);
    }
}
