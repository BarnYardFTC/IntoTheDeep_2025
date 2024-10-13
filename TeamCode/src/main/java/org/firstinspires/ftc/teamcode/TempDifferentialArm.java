package org.firstinspires.ftc.teamcode;

// Imports.
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.modules.ServoProps;

public class TempDifferentialArm {

    // Servos (starting positions: right (reversed): 0.5, left: 0.5).
    private static final int SERVO_AMOUNT = 2;
    private static final Servo[] servos = new Servo[SERVO_AMOUNT];
    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final ServoProps RIGHT_PROPS = new ServoProps();
    private static final ServoProps LEFT_PROPS = new ServoProps();

    // Angles
    private static final int ANGLE_SPECIMEN_INTAKE = 0;
    private static final int ANGLE_SPECIMEN_UNLOAD = 0;
    private static final int ANGLE_SAMPLE_INTAKE = 0;
    private static final int ANGLE_SAMPLE_UNLOAD = 0;

    // Initializing.
    public static void init(Servo right, Servo left) {
        // Assigning objects to variables.
        servos[RIGHT] = right;
        servos[LEFT] = left;

        // Moving Servos to starting position.
        movement(0);
    }

    // Get value of angles for movement of arm.
    public static int getAngleSpecimenIntake() {
        return ANGLE_SPECIMEN_INTAKE;
    }
    public static int getAngleSpecimenUnload() {
        return ANGLE_SPECIMEN_UNLOAD;
    }
    public static int getAngleSampleIntake() {
        return ANGLE_SAMPLE_INTAKE;
    }
    public static int getAngleSampleUnload() {
        return ANGLE_SAMPLE_UNLOAD;
    }

    // Differential arm movement based on a given angle.
    public static void movement(int angle) {
        servos[RIGHT].setPosition(RIGHT_PROPS.getStartPosition() + (double) angle / RIGHT_PROPS.getMaxRotation() * RIGHT_PROPS.getRotationRatio());
        servos[LEFT].setPosition(LEFT_PROPS.getStartPosition() + (double) angle / LEFT_PROPS.getMaxRotation() * LEFT_PROPS.getRotationRatio());
    }
}
