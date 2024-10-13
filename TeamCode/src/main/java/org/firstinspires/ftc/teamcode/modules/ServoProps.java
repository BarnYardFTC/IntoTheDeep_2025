package org.firstinspires.ftc.teamcode.modules;

// Imports.
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.Servo;
public class ServoProps {
    // Servo props.
    private int maxRotation;
    private double startPosition;
    private double rotationRatio;

    // Initializing.
    public ServoProps(int maxRotation, double startPosition, double rotationRatio) {
        this.maxRotation = maxRotation;
        this.startPosition = startPosition;
        this.rotationRatio = rotationRatio;
    }
    public ServoProps() {
        this.maxRotation = 355;
        this.startPosition = 0;
        this.rotationRatio = 1;
    }

    // Sets servo props.
    public void setMaxRotation(int maxRotation) {
        this.maxRotation = maxRotation;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public void setRotationRatio(int rotationRatio) {
        this.rotationRatio = rotationRatio;
    }

    // Gets servo props.
    public int getMaxRotation() {
        return maxRotation;
    }

    public double getStartPosition() {
        return startPosition;
    }

    public double getRotationRatio() {return rotationRatio;}

    // Move servo by angle.
    public static void movement(int angle, Servo servo, double startPosition, int maxRotation, double rotationRatio) {
        servo.setPosition(startPosition + (double) angle / maxRotation / rotationRatio);
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
