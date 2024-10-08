package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

public class TempLED {
    static private RevBlinkinLedDriver LED;
    static public int LEDTime = 16;

    // Initializing
    public static void init(RevBlinkinLedDriver LED) {
        TempLED.LED = LED;
        // Changing color to default
        LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.DARK_RED);
    }
    // Color change functions
    public static void red() {LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.DARK_RED);}
    public static void black() {LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);}
    public static void green() {LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);}
    public static void blue() {LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE_VIOLET);}
}
