package org.firstinspires.ftc.teamcode;

// Imports.

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

public class LED {
    private static RevBlinkinLedDriver LED; // LED strip.

    private static int blinkTime = 16; // Meant to create blinking by for changing color when the blinkTime is an equal number while constantly decreasing its value (reset when it's smaller the zero).

    /**
     * Initializing.
     *
     * @param LEDConfig - Hardware for LED strip.
     */
    public static void init(RevBlinkinLedDriver LEDConfig) {
        LED = LEDConfig;

        // Changing LED color to default color (red).
        LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.DARK_RED);
    }

    /**
     * Get the value of the blinkTime parameter.
     *
     * @return - The blinkTime value.
     */
    public static int getBlinkTime() {
        return blinkTime;
    }

    /**
     * Set the value of the blinkTime parameter.
     *
     * @param time - The wanted blinkTime after reset.
     */
    public static void setBlinkTime(int time) {
        blinkTime = time;
    }

    /**
     * Change LED strip to a given color.
     *
     * @param color - Wanted LED strip color.
     */
    public static void changeColor(LEDColor color) {
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
    }

    /**
     * Enum for giving a color to the LED strip.
     */
    public enum LEDColor {
        RED, GREEN, PURPLE, BLACK
    }
}
