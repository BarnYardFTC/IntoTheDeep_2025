package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

public class TempDifferentialArm {
    public static final int rightDifferentialArm = 0;
    public static final int leftDifferentialArm = 1;
    // Servo characteristics
    private static final int rightDifferentialArmMaxRotation = 300;
    private static final int leftDifferentialArmMaxRotation = 300;

    /*  Positive direction is based on the angle of movement
        positive angle of movement is counter clockwise
    */
    private static final int rightDifferentialArmDirection = -1;
    private static final int leftDifferentialArmDirection = 1;
    public static Servo[] differentialServos;

    // Initializing
    public static void init(Servo rightDifferentialArm, Servo leftDifferentialArm) {
        // Assigning objects to variables
        TempDifferential.differentialServos = new Servo[]{rightDifferentialArm, leftDifferentialArm};
    }

    // Differential arm movement
    public static void differentialArmMovement(int angle) {
        differentialServos[rightDifferentialArm].setPosition(differentialServos[rightDifferentialArm].getPosition() + (double) angle / rightDifferentialArmMaxRotation * rightDifferentialArmDirection);
        differentialServos[leftDifferentialArm].setPosition(differentialServos[leftDifferentialArm].getPosition() + (double) angle / leftDifferentialArmMaxRotation * leftDifferentialArmDirection);
    }
}
