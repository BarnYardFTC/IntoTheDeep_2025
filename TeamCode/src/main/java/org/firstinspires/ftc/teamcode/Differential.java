package org.firstinspires.ftc.teamcode;

// Imports.

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.modules.ServoProps;

public class Differential {
    private static final int SERVO_AMOUNT = 2; // Amount of servos used.
    private static final Servo[] servos = new Servo[SERVO_AMOUNT]; // Servos array.
    private static final ServoProps RIGHT_SERVO = new ServoProps(); // Right's servo props.
    private static final ServoProps LEFT_SERVO = new ServoProps(355, 0.5, 1); // Left's servo props.
    private static final int RIGHT = 0; // Right's servo index.
    private static final int LEFT = 1; // Left's servo index.

    // Angles for moving the differential.
    private static final int PITCH_ANGLE_SPECIMEN_INTAKE = -180;
    private static final int ROLL_ANGLE_SPECIMEN_UNLOAD = 180;
    private static final int ROLL_ANGLE_SAMPLE_UNLOAD = 90;
    private static final int PITCH_ANGLE_SAMPLE_UNLOAD = -180;

    // Analog, position equation: position = analogInput.getVoltage() / 3.3 * 360.
    private static AnalogInput analogInput;

    /**
     * Initializing all hardware.
     *
     * @param right        - Hardware for right servo.
     * @param left         - Hardware for left servo.
     * @param analogSensor - Hardware for analogInput.
     */
    public static void init(Servo right, Servo left, AnalogInput analogSensor) {
        // Assigning objects to variables.
        servos[RIGHT] = right;
        servos[LEFT] = left;
        analogInput = analogSensor;

        // Moving Servos to starting position.
        move(0, axis.PITCH);
    }

    /**
     * Get the value of the PITCH_ANGLE_SPECIMEN_INTAKE parameter.
     *
     * @return - The PITCH_ANGLE_SPECIMEN_INTAKE value.
     */
    public static int getPitchAngleSpecimenIntake() {
        return PITCH_ANGLE_SPECIMEN_INTAKE;
    }

    /**
     * Get the value of the ROLL_ANGLE_SPECIMEN_UNLOAD parameter.
     *
     * @return - The ROLL_ANGLE_SPECIMEN_UNLOAD value.
     */
    public static int getRollAngleSpecimenUnload() {
        return ROLL_ANGLE_SPECIMEN_UNLOAD;
    }

    /**
     * Get the value of the ROLL_ANGLE_SAMPLE_UNLOAD parameter.
     *
     * @return - The ROLL_ANGLE_SAMPLE_UNLOAD value.
     */
    public static int getRollAngleSampleUnload() {
        return ROLL_ANGLE_SAMPLE_UNLOAD;
    }

    /**
     * Get the value of the PITCH_ANGLE_SAMPLE_UNLOAD parameter.
     *
     * @return - The PITCH_ANGLE_SAMPLE_UNLOAD value.
     */
    public static int getPitchAngleSampleUnload() {
        return PITCH_ANGLE_SAMPLE_UNLOAD;
    }

    /**
     * Move each servo based on a given target angle and an axis for movement.
     * The logic for the movement is in the class ServoProps.
     *
     * @param angle - Wanted end angle of the differential.
     * @param ax    - Wanted movement axis of the differential.
     */
    public static void move(int angle, axis ax) {
        switch (ax) {
            case PITCH:
                RIGHT_SERVO.move(angle, servos[RIGHT]);
                LEFT_SERVO.move(angle, servos[LEFT]);
                break;
            case ROLL:
                RIGHT_SERVO.move(-angle, servos[RIGHT]);
                LEFT_SERVO.move(angle, servos[LEFT]);
                break;
        }
    }

    /**
     * Enum for giving an axis to the systems movement.
     */
    public enum axis {
        PITCH, ROLL
    }
}
