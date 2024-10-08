package org.firstinspires.ftc.teamcode;

// Imports
import com.qualcomm.robotcore.hardware.Servo;

public class TempDifferential {
    // Servos
    public static final int rightDifferential = 0;
    public static final int leftDifferential = 1;
    // Servo characteristics
    private static final int rightDifferentialMaxRotation = 355;
    private static final int leftDifferentialMaxRotation = 355;
    public static Servo[] differentialServos;

    // Initializing
    public static void init(Servo rightDifferential, Servo leftDifferential) {
        // Assigning objects to variables
        TempDifferential.differentialServos = new Servo[]{rightDifferential, leftDifferential};
        differentialServos[TempDifferential.rightDifferential].setPosition(0);
        differentialServos[TempDifferential.leftDifferential].setPosition(0);
    }

    // Roll movement
    public static void differentialRollMovement(int angleRoll) {
        differentialServos[rightDifferential].setPosition(differentialServos[rightDifferential].getPosition() + (double) angleRoll / rightDifferentialMaxRotation);
        differentialServos[leftDifferential].setPosition(differentialServos[leftDifferential].getPosition() + (double) angleRoll / leftDifferentialMaxRotation);
    }

    // Yaw movement
    public static void differentialYawMovement(int angleYaw) {
        differentialServos[rightDifferential].setPosition(differentialServos[rightDifferential].getPosition() - (double) angleYaw / rightDifferentialMaxRotation);
        differentialServos[leftDifferential].setPosition(differentialServos[leftDifferential].getPosition() + (double) angleYaw / leftDifferentialMaxRotation);
    }
}
