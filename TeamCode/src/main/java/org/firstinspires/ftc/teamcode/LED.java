package org.firstinspires.ftc.teamcode;

// Imports.

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

public class LED {

    // LED object.
    private static RevBlinkinLedDriver LED;

    // Meant to create blinking.
    private static int LEDTime = 16;

    // Initializing.
    public static void init(RevBlinkinLedDriver LEDConfig) {
        LED = LEDConfig;

        // Changing color to default.
        LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.DARK_RED);
    }

    // Get LED blink time
    public static int getLEDTime() {
        return LEDTime;
    }

    // Set LED blink time
    public static void setLEDTime(int time) {
        LEDTime = time;
    }

    // Color change functions.
    public static void changeLEDColor(LEDColor color) throws Exception {
        switch (color) {
            case RED:
                LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.DARK_RED);
                break;
            case GREEN:
                LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
                break;
            case PURPLE:
                LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE_VIOLET);
                break;
            case BLACK:
                LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);
                break;
        }

        throw new Exception("No such operation");
    }

    // enum for giving a wanted color to the LED strip.
    public enum LEDColor {
        RED, GREEN, PURPLE, BLACK
    }
}
