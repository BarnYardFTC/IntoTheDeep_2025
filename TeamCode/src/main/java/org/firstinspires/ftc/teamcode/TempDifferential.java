package org.firstinspires.ftc.teamcode;

// Imports
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.Servo;

public class TempDifferential {
    // Angles
    private static final double RIGHT_DIFFERENTIAL_START_POS = 0.0;
    private static final double LEFT_DIFFERENTIAL_START_POS = 0.0;

    private static final int ANGLE_PITCH_SPECIMEN_INTAKE = -180;
    private static final int ANGLE_ROLL_SPECIMEN_UNLOAD = 180;
    private static final int ANGLE_ROLL_SAMPLE_UNLOAD = 90;
    private static final int ANGLE_PITCH_SAMPLE_UNLOAD = -180;

    // Servo characteristics
    private static final int RIGHT_DIFFERENTIAL_MAX_ROTATION = 355;
    private static final int LEFT_DIFFERENTIAL_MAX_ROTATION = 355;

    public static final Servo[] differentialServos = new Servo[2];

    // Servos (starting positions: rightDifferential: 0, leftDifferential: 0.5)
    private static final int RIGHT = 0;
    private static final int LEFT = 1;

    // Analog, position equation: position = analogSensor.getVoltage() / 3.3 * 360
    public static AnalogInput analogSensor;

    // Initializing
    public static void init(Servo rightDifferential, Servo leftDifferential, AnalogInput analogSensorConfig) {
        // Assigning objects to variables
        differentialServos[RIGHT] = rightDifferential;
        differentialServos[LEFT] = leftDifferential;
        analogSensor = analogSensorConfig;

        // Moving Servos to starting position
        differentialServos[RIGHT].setPosition(RIGHT_DIFFERENTIAL_START_POS);
        differentialServos[LEFT].setPosition(LEFT_DIFFERENTIAL_START_POS);
    }

    // Roll movement
    public static void differentialRollMovement(int angleRoll) {
        differentialServos[RIGHT].setPosition(RIGHT_DIFFERENTIAL_START_POS + (double) angleRoll / RIGHT_DIFFERENTIAL_MAX_ROTATION);
        differentialServos[LEFT].setPosition(LEFT_DIFFERENTIAL_START_POS + (double) angleRoll / LEFT_DIFFERENTIAL_MAX_ROTATION);
    }

    // Yaw movement
    public static void differentialPitchMovement(int anglePitch) {
        differentialServos[RIGHT].setPosition(RIGHT_DIFFERENTIAL_START_POS - (double) anglePitch / RIGHT_DIFFERENTIAL_MAX_ROTATION);
        differentialServos[LEFT].setPosition(LEFT_DIFFERENTIAL_START_POS + (double) anglePitch / LEFT_DIFFERENTIAL_MAX_ROTATION);
    }
}
