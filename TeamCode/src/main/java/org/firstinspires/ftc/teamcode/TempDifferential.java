package org.firstinspires.ftc.teamcode;

// Imports.

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.modules.ServoProps;

public class TempDifferential {
    // Servos (starting positions: right: 0, left: 0.5).
    private static final Servo[] servos = new Servo[2];
    private static final ServoProps RIGHT_PROPS = new ServoProps();
    private static final ServoProps LEFT_PROPS = new ServoProps();
    private static final int RIGHT = 0;
    private static final int LEFT = 1;

    // Angles.
    private static final int ANGLE_PITCH_SPECIMEN_INTAKE = -180;
    private static final int ANGLE_ROLL_SPECIMEN_UNLOAD = 180;
    private static final int ANGLE_ROLL_SAMPLE_UNLOAD = 90;
    private static final int ANGLE_PITCH_SAMPLE_UNLOAD = -180;

    // Analog, position equation: position = analogInput.getVoltage() / 3.3 * 360.
    private static AnalogInput analogInput;

    // Initializing.
    public static void init(Servo right, Servo left, AnalogInput analogSensor) {
        // Assigning objects to variables.
        servos[RIGHT] = right;
        servos[LEFT] = left;
        analogInput = analogSensor;

        // Moving Servos to starting position.
        servos[RIGHT].setPosition(RIGHT_PROPS.getStartPosition());
        servos[LEFT].setPosition(LEFT_PROPS.getStartPosition());
    }

    // Get value of angles for movement of arm.
    public static int getAnglePitchSpecimenIntake() {
        return ANGLE_PITCH_SPECIMEN_INTAKE;
    }

    public static int getAngleRollSpecimenUnload() {
        return ANGLE_ROLL_SPECIMEN_UNLOAD;
    }

    public static int getAngleRollSampleUnload() {
        return ANGLE_ROLL_SAMPLE_UNLOAD;
    }

    public static int getAnglePitchSampleUnload() {
        return ANGLE_PITCH_SAMPLE_UNLOAD;
    }

    // Movement of differential system based on a given axis and angle.
    public static void movement(int angle, String axis) throws Exception {
        switch (axis) {
            case "pitch":
                servos[RIGHT].setPosition(RIGHT_PROPS.getStartPosition() + (double) angle / RIGHT_PROPS.getMaxRotation() * RIGHT_PROPS.getRotationRatio());
                servos[LEFT].setPosition(LEFT_PROPS.getStartPosition() + (double) angle / LEFT_PROPS.getMaxRotation() * LEFT_PROPS.getRotationRatio());
            case "roll":
                servos[RIGHT].setPosition(RIGHT_PROPS.getStartPosition() - (double) angle / RIGHT_PROPS.getMaxRotation() * RIGHT_PROPS.getRotationRatio());
                servos[LEFT].setPosition(LEFT_PROPS.getStartPosition() + (double) angle / LEFT_PROPS.getMaxRotation() * LEFT_PROPS.getRotationRatio());
        }

        throw new Exception("No such operation");
    }
}
