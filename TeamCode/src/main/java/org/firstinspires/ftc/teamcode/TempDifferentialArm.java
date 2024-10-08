package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

public class TempDifferentialArm {
    // Servos (starting positions: rightDifferentialArm (reversed): 0.5, leftDifferentialArm: 0.5)
    public static final int rightDifferentialArm = 0;
    public static final int leftDifferentialArm = 1;
    // Angles
    private static final double rightDifferentialArmStartPos = 0.0;
    private static final double leftDifferentialArmStartPos = 0.0;
    private static final int angleSpecimenIntake = 0;
    private static final int angleSpecimenUnload = 0;
    private static final int angleSampleIntake = 0;
    private static final int angleSampleUnload = 0;
    // Servo characteristics
    private static final int rightDifferentialArmMaxRotation = 355;
    private static final int leftDifferentialArmMaxRotation = 355;
    public static Servo[] differentialArmServos;

    // Initializing
    public static void init(Servo rightDifferentialArm, Servo leftDifferentialArm) {
        // Assigning objects to variables
        TempDifferential.differentialServos = new Servo[]{rightDifferentialArm, leftDifferentialArm};
        // Moving Servos to starting position
        differentialArmMovement(0);
    }

    // Differential arm movement
    public static void differentialArmMovement(int angle) {
        differentialArmServos[rightDifferentialArm].setPosition(rightDifferentialArmStartPos + (double) angle / rightDifferentialArmMaxRotation);
        differentialArmServos[leftDifferentialArm].setPosition(leftDifferentialArmStartPos + (double) angle / leftDifferentialArmMaxRotation);
    }
}
