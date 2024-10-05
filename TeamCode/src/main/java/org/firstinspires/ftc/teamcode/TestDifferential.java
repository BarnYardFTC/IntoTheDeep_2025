package org.firstinspires.ftc.teamcode;

// Imports
import com.qualcomm.robotcore.hardware.Servo;

public class TestDifferential {
    // Servos
    public static final int rightDifferential = 0;
    public static final int leftDifferential = 1;
    public static final int rightDifferentialArm = 2;
    public static final int leftDifferentialArm = 3;
    // Servo characteristics
    private static final int rightDifferentialMaxRotation = 300;
    private static final int leftDifferentialMaxRotation = 300;
    private static final int rightDifferentialArmMaxRotation = 300;
    private static final int leftDifferentialArmMaxRotation = 300;

    /*  Positive direction is based on the angle of movement
        positive angle of movement is counter clockwise
    */
    private static final int rightDifferentialDirection = -1;
    private static final int leftDifferentialDirection = 1;
    private static final int rightDifferentialArmDirection = -1;
    private static final int leftDifferentialArmDirection = 1;
    public static Servo[] differentialServos;
    public static boolean wasPressed = false;

    // Initializing
    public static void init(Servo rightDifferential, Servo leftDifferential, Servo rightDifferentialArm, Servo leftDifferentialArm) {
        // Assigning objects to variables
        TestDifferential.differentialServos = new Servo[]{rightDifferential, leftDifferential, rightDifferentialArm, leftDifferentialArm};
    }
    public static void init(Servo rightDifferential, Servo leftDifferential) {
        // Assigning objects to variables
        TestDifferential.differentialServos = new Servo[]{rightDifferential, leftDifferential};
        wasPressed = false;
    }

    // General key press function to prevent multiple inputs in one press
    public static boolean isPressed(boolean key) {
        if (key && !wasPressed) {
            wasPressed = true;
        }
        if (!key && wasPressed) {
            wasPressed = false;
            return true;
        }
        return false;
    }

    // Position arrival
    public static boolean isServoInPosition(Servo servo, int position) {return (int) (servo.getPosition() * 1000) == position * 1000;}

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

    // Differential arm movement
    public static void differentialArmMovement(int angle) {
        differentialServos[rightDifferentialArm].setPosition(differentialServos[rightDifferentialArm].getPosition() + (double) angle / rightDifferentialArmMaxRotation * rightDifferentialArmDirection);
        differentialServos[leftDifferentialArm].setPosition(differentialServos[leftDifferentialArm].getPosition() + (double) angle / leftDifferentialArmMaxRotation * leftDifferentialArmDirection);
    }
}
