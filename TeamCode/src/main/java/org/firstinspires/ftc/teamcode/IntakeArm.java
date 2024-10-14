package org.firstinspires.ftc.teamcode;

// Imports.

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.modules.ServoProps;

public class IntakeArm {
    // Servos (starting positions: right (reversed): 0.5, left: 0.5).
    private static final int SERVO_AMOUNT = 2;
    private static final Servo[] servos = new Servo[SERVO_AMOUNT];
    private static final ServoProps RIGHT_PROPS = new ServoProps();
    private static final ServoProps LEFT_PROPS = new ServoProps();
    private static final int RIGHT = 0;
    private static final int LEFT = 1;

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


    // Moving arm to an assigned angle.
    public static void movement(int angle) {
        servos[RIGHT].setPosition(RIGHT_PROPS.getStartPosition() + (double) angle / RIGHT_PROPS.getMaxRotation() * RIGHT_PROPS.getRotationRatio());
        servos[LEFT].setPosition(LEFT_PROPS.getStartPosition() + (double) angle / LEFT_PROPS.getMaxRotation() * LEFT_PROPS.getRotationRatio());
    }
}
