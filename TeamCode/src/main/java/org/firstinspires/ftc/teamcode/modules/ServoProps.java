package org.firstinspires.ftc.teamcode.modules;

// Imports.
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.Servo;
public class ServoProps {
    // Servo props.
    private static int maxRotation = 355;
    private static double startPosition = 0;

    // Initializing.
    public static void init(int maxRot, double startPos) {
        maxRotation = maxRot;
        startPosition = startPos;
    }

    // Sets servo props.
    public static void setMaxRotation(int maxRot) {
        maxRotation = maxRot;
    }

    public static void setStartPosition(int startPos) {
        startPosition = startPos;
    }

    // Gets servo props.
    public static int getMaxRotation() {
        return maxRotation;
    }

    public static double getStartPosition() {
        return startPosition;
    }

    // Checks if servo is in a given position;
    public static boolean isServoInPosition(Servo servo, double position) {
        int servoRoundedPos = (int) (servo.getPosition() * 10000);
        int roundedPos = (int) (position * 1000);
        return servoRoundedPos == roundedPos;
    }

    // Checks if analog is a given angle.
    public static boolean isAnalogInPosition(AnalogInput analogInput, int angle) {
        int analogInputAngle = (int) (analogInput.getVoltage() / 3.3 * 360);
        return analogInputAngle == angle;
    }
}
