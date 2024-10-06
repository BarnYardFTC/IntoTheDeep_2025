package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

public class TempGeneralFunctions {
    public static boolean wasPressed = false;

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
    public static boolean isEncoderBiggerThen(DcMotorEx motorEx, int position) {return motorEx.getCurrentPosition() > position;}
    public static boolean isEncoderSmallerThen(DcMotorEx motorEx, int position) {return motorEx.getCurrentPosition() < position;}
    public static boolean isEncoderCloseTo(DcMotorEx motorEx, int position) {return motorEx.getCurrentPosition() <= position + 5 || motorEx.getCurrentPosition() >= position - 5;}
}
