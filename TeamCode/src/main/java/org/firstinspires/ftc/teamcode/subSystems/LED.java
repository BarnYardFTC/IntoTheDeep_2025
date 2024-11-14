package org.firstinspires.ftc.teamcode.subSystems;

// Imports.

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

public class LED {
    private static RevBlinkinLedDriver LED; // LED strip.
    private static RevBlinkinLedDriver.BlinkinPattern allianceColor; // LED strip.

    /**
     * Initializing all hardware.
     *
     * @param LEDConfig - Hardware for LED strip.
     */
    public static void init(RevBlinkinLedDriver LEDConfig) {
        LED = LEDConfig;

        // Changing LED color to default color.
        changeColor(allianceColor);
    }

    /**
     * Gets the current alliance color.
     *
     * @return - the alliance color;
     */
    public static RevBlinkinLedDriver.BlinkinPattern getAllianceColor() {
        return allianceColor;
    }

    /**
     * Change LED strip to a given color.
     *
     * @param color - Wanted LED strip color.
     */
    public static void changeColor(RevBlinkinLedDriver.BlinkinPattern color) {
        switch (color) {
            case DARK_RED:
                LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.DARK_RED); // Default and at start of teleop, depends on alliance color.
                break;
            case GREEN:
                LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN); // When a sample or specimen is collected.
                break;
            case VIOLET:
                LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.VIOLET); // When hanging / being in a unloading position.
                break;
            case BLUE:
                LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE); // At start of teleop, depends on alliance color.
        }
    }

    /**
     * Gives the drive team an indicator of the alliance side since it affects the collection of samples.
     *
     * @param autonomousAllianceColor - the color of the current alliance.
     */
    public static void changeAllianceColor(RevBlinkinLedDriver.BlinkinPattern autonomousAllianceColor) {
        allianceColor = autonomousAllianceColor;
    }
}
