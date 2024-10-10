package org.firstinspires.ftc.teamcode;

// Imports.
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.modules.ServoProps;

public class TempDifferential {
    // Servos (starting positions: right: 0, left: 0.5).
    private static final Servo[] servos = new Servo[2];
    private static final int RIGHT = 0; // ToDo: Maybe change to RIGHT_INDEX to make more understandable?
    private static final int LEFT = 1; // ToDo: Maybe change to LEFT_INDEX to make more understandable?

    // Analog, position equation: position = analogInput.getVoltage() / 3.3 * 360.
    private static AnalogInput analogInput;

    // Start positions.
    private static final double RIGHT_START_POS = 0.0;
    private static final double LEFT_START_POS = 0.0;

    // Servo characteristics.
    private static final int RIGHT_MAX_ROTATION = 355;
    private static final int LEFT_MAX_ROTATION = 355;

    // Angles.
    private static final int ANGLE_PITCH_SPECIMEN_INTAKE = -180;
    private static final int ANGLE_ROLL_SPECIMEN_UNLOAD = 180;
    private static final int ANGLE_ROLL_SAMPLE_UNLOAD = 90;
    private static final int ANGLE_PITCH_SAMPLE_UNLOAD = -180;

    // Initializing.
    public static void init(Servo right, Servo left, AnalogInput analogSensor) {
        // Assigning objects to variables.
        servos[RIGHT] = right;
        servos[LEFT] = left;
        analogInput = analogSensor;

        // Moving Servos to starting position.
        servos[RIGHT].setPosition(RIGHT_START_POS);
        servos[LEFT].setPosition(LEFT_START_POS);

    }

    // Movement.
    public static void movement(int angle, String axis) throws Exception {

        // ToDo: It's very hard to understand what this function does

        switch (axis) {
            case "pitch":
                servos[RIGHT].setPosition(RIGHT_START_POS + (double) angle / RIGHT_MAX_ROTATION);
                servos[LEFT].setPosition(LEFT_START_POS + (double) angle / LEFT_MAX_ROTATION);
            case "roll":
                servos[RIGHT].setPosition(RIGHT_START_POS - (double) angle / RIGHT_MAX_ROTATION);
                servos[LEFT].setPosition(LEFT_START_POS + (double) angle / LEFT_MAX_ROTATION);
        }

        throw new Exception("No such operation");
    }
}
