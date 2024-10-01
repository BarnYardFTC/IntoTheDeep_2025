package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

public class TestDifferential {
    // Servos
    public static final int rightDifferential = 0;
    public static final int leftDifferential = 1;
    public static final int rightDifferentialArm = 2;
    public static final int leftDifferentialArm = 3;
    private static final int maxRotationDifferential = 300;
    private static final int maxRotationDifferentialArm = 300;
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
    }

    // Position arrival
    public static boolean isServoInPosition(Servo servo, int position) {return (int) (servo.getPosition() * 1000) == position * 1000;}
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

    // Roll movement
    public static void differentialRollMovement(int angleRoll) {
        differentialServos[rightDifferential].setPosition(differentialServos[rightDifferential].getPosition() + (double) angleRoll / maxRotationDifferential);
        differentialServos[leftDifferential].setPosition(differentialServos[leftDifferential].getPosition() + (double) angleRoll / maxRotationDifferential);
    }

    // Yaw movement
    public static void differentialYawMovement(int angleYaw) {
        differentialServos[rightDifferential].setPosition(differentialServos[rightDifferential].getPosition() + (double) angleYaw / maxRotationDifferential);
        differentialServos[leftDifferential].setPosition(differentialServos[leftDifferential].getPosition() - (double) angleYaw / maxRotationDifferential);
    }

    // Differential arm movement
    public static void differentialArmR(int angle) {
        differentialServos[rightDifferentialArm].setPosition(differentialServos[rightDifferentialArm].getPosition() + (double) angle / maxRotationDifferentialArm);
    }
    public static void differentialArmL(int angle) {
        differentialServos[leftDifferentialArm].setPosition(differentialServos[leftDifferentialArm].getPosition() - (double) angle / maxRotationDifferentialArm);
    }
}
