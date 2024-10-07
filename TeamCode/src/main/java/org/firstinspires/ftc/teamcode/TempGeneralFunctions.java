package org.firstinspires.ftc.teamcode;

import java.lang.Exception;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

public class TempGeneralFunctions {
    public static boolean xWasPressed = false;
    public static boolean yWasPressed = false;
    public static boolean bWasPressed = false;
    public static boolean aWasPressed = false;
    public static boolean rightBumperWasPressed = false;
    public static boolean leftBumperWasPressed = false;
    public static boolean dpadUpWasPressed = false;

    // Example for general key press function to prevent multiple inputs in one press
//        if (key && !wasPressed) {
//            wasPressed = true;
//            function
//        }
//        if (!key) {
//            wasPressed = false;
//        }

    // Position arrival
    public static boolean isServoInPosition(Servo servo, int position) {return (int) (servo.getPosition() * 1000) == position * 1000;}
    public static boolean isEncoderPosition(DcMotorEx motorEx, int position, String direction) throws Exception {
        switch (direction) {
            case "<":
                return motorEx.getCurrentPosition() < position;
            case ">":
                return motorEx.getCurrentPosition() > position;
            case "=":
                return motorEx.getCurrentPosition() == position;
        }
        throw new Exception("No such operation");
    }
}
