package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

public class ServoProps {
    private static int maxRotation = 355;
    private static double startPosition = 0;

    public static void setMaxRotation(int maxRot) {
        maxRotation = maxRot;
    }

    public static int getMaxRotation() {
        return maxRotation;
    }

    public static void setStartPosition(int startPos) {
        startPosition = startPos;
    }

    public static double getStartPosition() {
        return startPosition;
    }

    public static boolean isServoInPosition(Servo servo, double position) {
        return (int) (servo.getPosition() * 10000) == position * 10000;
    }
}
