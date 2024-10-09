package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

public class TempDifferentialArm extends ServoProps {

    // Servos (starting positions: rightDifferentialArm (reversed): 0.5, leftDifferentialArm: 0.5)
    public static final int RIGHT = 0;
    public static final int LEFT = 1;
    private static final int SERVO_AMOUNT = 2;

    // Angles
    private static final double RIGHT_DIFFERENTIAL_ARM_START_POS = 0.0;
    private static final double LEFT_DIFFERENTIAL_ARM_START_POS = 0.0;

    private static final int ANGLE_SPECIMEN_INTAKE = 0;
    private static final int ANGLE_SPECIMEN_UNLOAD = 0;
    private static final int ANGLE_SAMPLE_INTAKE = 0;
    private static final int ANGLE_SAMPLE_UNLOAD = 0;

    // Servo characteristics
    private static final int RIGHT_DIFFERENTIAL_ARM_MAX_ROTATION = ServoProps.getMaxRotation();
    private static final int LEFT_DIFFERENTIAL_ARM_MAX_ROTATION = ServoProps.getMaxRotation();

    public static Servo[] differentialArmServos = new Servo[SERVO_AMOUNT];

    // Initializing
    public static void init(Servo rightDifferentialArm, Servo leftDifferentialArm) {
        // Assigning objects to variables
        differentialArmServos[RIGHT] = rightDifferentialArm;
        differentialArmServos[LEFT] = leftDifferentialArm;

        // Moving Servos to starting position
        differentialArmMovement(0);
    }

    // Differential arm movement
    public static void differentialArmMovement(int angle) {
        differentialArmServos[RIGHT].setPosition(RIGHT_DIFFERENTIAL_ARM_START_POS + (double) angle / RIGHT_DIFFERENTIAL_ARM_MAX_ROTATION);
        differentialArmServos[LEFT].setPosition(LEFT_DIFFERENTIAL_ARM_START_POS + (double) angle / LEFT_DIFFERENTIAL_ARM_MAX_ROTATION);
    }
}
