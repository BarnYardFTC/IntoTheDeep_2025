package org.firstinspires.ftc.teamcode.subSystems;

// Imports.

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.modules.ServoProps;

public class Differential {
    public static final Servo[] servos = new Servo[2]; // Servos array.
    private static final ServoProps RIGHT_SERVO = new ServoProps((double) 175 / 355); // Right's servo props.
    private static final ServoProps LEFT_SERVO = new ServoProps(0); // Left's servo props.
    private static final int RIGHT = 0; // Right's servo index.
    private static final int LEFT = 1; // Left's servo index.
    // Angles for moving the differential.
    private static final int PITCH_ANGLE_SAMPLE = 0;
    private static final int PITCH_ANGLE_SPECIMEN = 30;
    private static final int PITCH_ANGLE_RESET = 175;

    public static int currentRollAngle;
    public static int currentPitchAngle;

    public static void initialize(OpMode opMode) {
        servos[RIGHT] = opMode.hardwareMap.get(Servo.class, "rightDifferential");
        servos[LEFT] = opMode.hardwareMap.get(Servo.class, "leftDifferential");
    }

    /**
     * Move each servo based on a given target angle and an axis for movement.
     * The logic for the movement is in the class ServoProps.
     * The action set the servos position once in a loop until the moved value is changed.
     *
     * @param angleRoll  - Wanted end angle of the differential on the roll axis.
     * @param anglePitch - Wanted end angle of the differential on the pitch axis.
     */
    public static void move(int angleRoll, int anglePitch) {
        servos[RIGHT].setPosition(RIGHT_SERVO.getTargetPosition(angleRoll - anglePitch));
        servos[LEFT].setPosition(LEFT_SERVO.getTargetPosition(angleRoll + anglePitch));
    }

    /**
     * Resets differential to it's starting position.
     * The action set the servos position once in a loop until the reseted value is changed.
     */
    public static void reset() {
        move(0, PITCH_ANGLE_RESET);
        currentRollAngle = 0;
        currentPitchAngle = PITCH_ANGLE_RESET;
        servos[RIGHT].setDirection(Servo.Direction.FORWARD);
        servos[LEFT].setDirection(Servo.Direction.FORWARD);
    }

    /**
     * Moves differential to the sample intake position.
     */
    public static void collectSample() {
        move(currentRollAngle, PITCH_ANGLE_SAMPLE);
        currentPitchAngle = PITCH_ANGLE_SAMPLE;
    }

    /**
     * Moves the differential to the specimen intake position.
     */
    public static void collectSpecimen() {
        move(0, PITCH_ANGLE_SPECIMEN);
        currentPitchAngle = PITCH_ANGLE_SPECIMEN;
    }

    /**
     * Checks if the differential is in the reseted position.
     *
     * @return - If the differential is reseted.
     */
    public static boolean isReseted() {
        return currentPitchAngle == PITCH_ANGLE_RESET;
    }

    public static boolean isCollectSamplePos() {
        return servos[RIGHT].getPosition() == 0;
    }
}