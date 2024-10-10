package org.firstinspires.ftc.teamcode;

// Imports.
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class TempLED {

    // LED object.
    private static RevBlinkinLedDriver LED;

    // Meant to create blinking.
    private static int LEDTime = 16;

    // Initializing.
    public static void init(RevBlinkinLedDriver LED) {
        TempLED.LED = LED;

        // Changing color to default.
        LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.DARK_RED);
    }

    // Set LED blink time
    public static void setLEDTime(int time) {
        LEDTime = time;
    }

    // Get LED blink time
    public static int getLEDTime() {
        return LEDTime;
    }

    // Color change functions.
    public static void changeLEDColor(String color) throws Exception {
        switch (color) {
            case "red":
                LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.DARK_RED);
            case "green":
                LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
            case "purple":
                LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE_VIOLET);
            case "black":
                LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);
        }

        throw new Exception("No such operation");
    }
}
