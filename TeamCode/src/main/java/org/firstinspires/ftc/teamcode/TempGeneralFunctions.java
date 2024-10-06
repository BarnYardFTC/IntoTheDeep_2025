package org.firstinspires.ftc.teamcode;

import java.lang.Exception;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

public class TempGeneralFunctions {
    public static boolean wasPressed = false;
    public static boolean xWasPressed = false;
    public static boolean yWasPressed = false;
    public static boolean bWasPressed = false;
    public static boolean aWasPressed = false;
    public static boolean rightBumperWasPressed = false;
    public static boolean leftBumperWasPressed = false;
    public static boolean dpadUpWasPressed = false;

    // Example for general key press function to prevent multiple inputs in one press
    public static boolean isPressed(boolean key) {
        if (key && !wasPressed) {
            wasPressed = true;
            return true;
        }
        if (!key) {
            wasPressed = false;
        }
        return false;
    }

    // Position arrival
    public static boolean isServoInPosition(Servo servo, int position) {return (int) (servo.getPosition() * 1000) == position * 1000;}
    public static boolean isEncoderPosition(DcMotorEx motorEx, int position, String direction) throws Exception {
        if (direction.equals("<")) {
            return motorEx.getCurrentPosition() < position;
        } else if (direction.equals(">")) {
            return motorEx.getCurrentPosition() > position;
        } else if (direction.equals("=")){
            return motorEx.getCurrentPosition() == position;
        }
        
        throw new Exception("No such operation");
    }
    
    public static boolean isEncoderSmallerThan(DcMotorEx motorEx, int position) {return motorEx.getCurrentPosition() < position;}
    public static boolean isEncoderCloseTo(DcMotorEx motorEx, int position) {return motorEx.getCurrentPosition() <= position + 5 || motorEx.getCurrentPosition() >= position - 5;}
}
