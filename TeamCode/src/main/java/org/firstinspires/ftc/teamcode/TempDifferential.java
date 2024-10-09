package org.firstinspires.ftc.teamcode;

// Imports
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.Servo;

public class TempDifferential {
    // Servos (starting positions: rightDifferential: 0, leftDifferential: 0.5)
    public static final int rightDifferential = 0;
    public static final int leftDifferential = 1;
    // Analog, position equation: position = analogSensor.getVoltage() / 3.3 * 360
    public static AnalogInput analogSensor;
    // Angles
    private static final double rightDifferentialStartPos = 0.0;
    private static final double leftDifferentialStartPos = 0.0;
    private static final int anglePitchSpecimenIntake = -180;
    private static final int angleRollSpecimenUnload = 180;
    private static final int angleRollSampleUnload = 90;
    private static final int anglePitchSampleUnload = -180;
    // Servo characteristics
    private static final int rightDifferentialMaxRotation = 355;
    private static final int leftDifferentialMaxRotation = 355;
    public static Servo[] differentialServos;

    // Initializing
    public static void init(Servo rightDifferential, Servo leftDifferential, AnalogInput analogSensor) {
        // Assigning objects to variables
        TempDifferential.differentialServos = new Servo[]{rightDifferential, leftDifferential};
        TempDifferential.analogSensor = analogSensor;
        // Moving Servos to starting position
        differentialServos[TempDifferential.rightDifferential].setPosition(rightDifferentialStartPos);
        differentialServos[TempDifferential.leftDifferential].setPosition(leftDifferentialStartPos);
    }
    public static void init(Servo rightDifferential, Servo leftDifferential) {
        // Assigning objects to variables
        TempDifferential.differentialServos = new Servo[]{rightDifferential, leftDifferential};
        // Moving Servos to starting position
        differentialServos[TempDifferential.rightDifferential].setPosition(rightDifferentialStartPos);
        differentialServos[TempDifferential.leftDifferential].setPosition(leftDifferentialStartPos);
    }
    public static void init(Servo rightDifferential, AnalogInput analogSensor) {
        // Assigning objects to variables
        TempDifferential.differentialServos = new Servo[]{rightDifferential};
        TempDifferential.analogSensor = analogSensor;
        // Moving Servos to starting position
        differentialServos[TempDifferential.rightDifferential].setPosition(rightDifferentialStartPos);
    }

    // Roll movement
    public static void differentialRollMovement(int angleRoll) {
        differentialServos[rightDifferential].setPosition(rightDifferentialStartPos + (double) angleRoll / rightDifferentialMaxRotation);
        differentialServos[leftDifferential].setPosition(leftDifferentialStartPos + (double) angleRoll / leftDifferentialMaxRotation);
    }

    // Yaw movement
    public static void differentialPitchMovement(int anglePitch) {
        differentialServos[rightDifferential].setPosition(rightDifferentialStartPos - (double) anglePitch / rightDifferentialMaxRotation);
        differentialServos[leftDifferential].setPosition(leftDifferentialStartPos + (double) anglePitch / leftDifferentialMaxRotation);
    }
}
