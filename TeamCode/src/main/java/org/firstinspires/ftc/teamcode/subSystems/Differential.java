package org.firstinspires.ftc.teamcode.subSystems;

// Imports.

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.modules.ServoProps;

public class Differential {
    private static final Servo[] servos = new Servo[2]; // Servos array.
    private static final ServoProps RIGHT_SERVO = new ServoProps(355, (double) 175 / 355, 1); // Right's servo props.
    private static final ServoProps LEFT_SERVO = new ServoProps(); // Left's servo props.
    private static final int RIGHT = 0; // Right's servo index.
    private static final int LEFT = 1; // Left's servo index.

    // Angles for moving the differential.
    private static final int PITCH_ANGLE_SPECIMEN_INTAKE = 175;
    private static final int ROLL_ANGLE_SPECIMEN_INTAKE = 180;
    private static final int PITCH_ANGLE_SPECIMEN_UNLOAD = 90;
    private static final int ROLL_ANGLE_SAMPLE_UNLOAD = 90;
    private static final int PITCH_ANGLE_SAMPLE_UNLOAD = 140;
    public static boolean moved; // Meant to prevent the move method be ran in a loop multiple times when unneeded.
    public static boolean reseted; // Meant to prevent the reset method be ran in a loop multiple times when unneeded.
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
        moved = false;
        reseted = false;

        // Moving Servos to their starting position.
        reset();
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
    public static int getRollAngleSpecimenIntake() {
        return ROLL_ANGLE_SPECIMEN_INTAKE;
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
     * Get the value of the PITCH_ANGLE_SPECIMEN_UNLOAD parameter.
     *
     * @return - The PITCH_ANGLE_SPECIMEN_UNLOAD value.
     */
    public static int getPitchAngleSpecimenUnload() {
        return PITCH_ANGLE_SPECIMEN_UNLOAD;
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
     * Get the value of the analogInput parameter.
     *
     * @return - The analogInput value.
     */
    public static AnalogInput getAnalogInput() {
        return analogInput;
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
     * Move each servo based on a given target angle and an axis for movement.
     * The logic for the movement is in the class ServoProps.
     * The action set the servos position once in a loop until the moved value is changed.
     *
     * @param angleRoll - Wanted end angle of the differential on the roll axis.
     * @param anglePitch - Wanted end angle of the differential on the pitch axis.
     */
    private static void move(int angleRoll, int anglePitch) {
        if (!moved) {
            servos[RIGHT].setPosition(RIGHT_SERVO.getServoTargetPosition(angleRoll - anglePitch));
            servos[LEFT].setPosition(LEFT_SERVO.getServoTargetPosition(angleRoll + anglePitch));
            moved = true;
        }
    }

    /**
     * Resets differential to it's starting position.
     * The action set the servos position once in a loop until the reseted value is changed.
     */
    public static void reset() {
        if (!reseted) {
            servos[RIGHT].setDirection(Servo.Direction.FORWARD);
            servos[LEFT].setDirection(Servo.Direction.FORWARD);
            servos[RIGHT].setPosition(RIGHT_SERVO.getServoTargetPosition(0));
            servos[LEFT].setPosition(LEFT_SERVO.getServoTargetPosition(0));
            reseted = true;
        }
    }

    /**
     * Moves differential to the specimen intake position.
     */
    public static void collectSpecimen() {
        Claw.close();
        move(ROLL_ANGLE_SPECIMEN_INTAKE, PITCH_ANGLE_SPECIMEN_INTAKE);
        if (isInSpecimenCollectPos()) {
            Claw.open();
        }
    }

    /**
     * Moves differential to the specimen unload position.
     * The function checks when the differential finished it's movement in a certain axis before initiating the movement in another axis.
     */
    public static void unloadSpecimen() {
        if (isReseted()) {
            servos[RIGHT].setDirection(Servo.Direction.REVERSE);
            servos[LEFT].setDirection(Servo.Direction.REVERSE);
            move(0, PITCH_ANGLE_SPECIMEN_UNLOAD);
        }
    }

    /**
     * Moves differential to the sample unload position.
     */
    public static void unloadSample() {
        move(ROLL_ANGLE_SAMPLE_UNLOAD, PITCH_ANGLE_SAMPLE_UNLOAD);
    }

    /**
     * Checks if the differential is in the reseted position.
     *
     * @return - If the differential is reseted.
     */
    public static boolean isReseted() {
        return ServoProps.isAnalogInPosition(analogInput, 0);
    }

    /**
     * Checks if the differential is in the position to collect a specimen.
     *
     * @return - If the differential is in the position to collect a specimen.
     */
    public static boolean isInSpecimenCollectPos() {
        return ServoProps.isAnalogInPosition(analogInput, PITCH_ANGLE_SPECIMEN_INTAKE + ROLL_ANGLE_SPECIMEN_INTAKE);
    }
}