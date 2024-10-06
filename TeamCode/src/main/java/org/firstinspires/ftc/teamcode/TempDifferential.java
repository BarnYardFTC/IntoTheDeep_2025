package org.firstinspires.ftc.teamcode;

// Imports
import com.qualcomm.robotcore.hardware.Servo;

public class TempDifferential {
    // Servos
    public static final int rightDifferential = 0;
    public static final int leftDifferential = 1;
    // Servo characteristics
    private static final int rightDifferentialMaxRotation = 300;
    private static final int leftDifferentialMaxRotation = 300;

    /*  Positive direction is based on the angle of movement
        positive angle of movement is counter clockwise
    */
    private static final int rightDifferentialDirection = -1;
    private static final int leftDifferentialDirection = 1;
    public static Servo[] differentialServos;

    // Initializing
    public static void init(Servo rightDifferential, Servo leftDifferential) {
        // Assigning objects to variables
        TempDifferential.differentialServos = new Servo[]{rightDifferential, leftDifferential};
    }

    // Roll movement
    public static void differentialRollMovement(int angleRoll) {
        differentialServos[rightDifferential].setPosition(differentialServos[rightDifferential].getPosition() + (double) angleRoll / rightDifferentialMaxRotation * rightDifferentialDirection);
        differentialServos[leftDifferential].setPosition(differentialServos[leftDifferential].getPosition() + (double) angleRoll / leftDifferentialMaxRotation * leftDifferentialDirection);
    }

    // Yaw movement
    public static void differentialYawMovement(int angleYaw) {
        differentialServos[rightDifferential].setPosition(differentialServos[rightDifferential].getPosition() + (double) angleYaw / rightDifferentialMaxRotation * rightDifferentialDirection);
        differentialServos[leftDifferential].setPosition(differentialServos[leftDifferential].getPosition() + (double) angleYaw / leftDifferentialMaxRotation * leftDifferentialDirection);
    }
}
