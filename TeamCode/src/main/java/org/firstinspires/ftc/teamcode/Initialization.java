package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subSystems.Claw;
import org.firstinspires.ftc.teamcode.subSystems.Differential;
import org.firstinspires.ftc.teamcode.subSystems.DifferentialArm;
import org.firstinspires.ftc.teamcode.subSystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subSystems.IntakeArm;
import org.firstinspires.ftc.teamcode.subSystems.LED;
import org.firstinspires.ftc.teamcode.subSystems.TempVerticalLift;

public class Initialization {

    public static boolean reseted;

    /*
     * Functions for initialization of the hardware.
     * Each function gets the name of the hardware and assigns it to a variable.
     * The variables are given to a each classes inner initialization function.
     */
    // TODO: Change names of all hardware in configuration.

    /**
     * Initialize all hardware.
     */
    public static void initializeAll() {
        reseted = false;
        initClaw();
        initDifferential();
        initDifferentialArm();
        initDriveTrain();
        initVerticalLift();
        initHorizontalLift();
        initIntake();
        initIntakeArm();
        initHuskyLens();
        initHang();
        initLED();
    }

    /**
     * Move all robot parts to their starting position.
     * This function is made so the robot doesn't move between auto and teleop period.
     */
    public static void resetRobot() {
        if (reseted) {
            Claw.open();
            Differential.reset();
            DifferentialArm.reset();
            IntakeArm.reset();
            LED.changeColor(RevBlinkinLedDriver.BlinkinPattern.DARK_RED);
            reseted = true;
        }
    }

    /**
     * Initializes ignition system.
     */
    public static void initDriveTrain() {
        DcMotorEx leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        DcMotorEx rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
        DcMotorEx leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
        DcMotorEx rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");
        IMU imu = hardwareMap.get(IMU.class, "imu");

        Drivetrain.init(leftFront, rightFront, leftBack, rightBack, imu);
    }

    /**
     * Initializes differential system.
     */
    public static void initDifferential() {
        Servo right = hardwareMap.get(Servo.class, "rightDifferential");
        Servo left = hardwareMap.get(Servo.class, "leftDifferential");
        AnalogInput analogSensor = hardwareMap.get(AnalogInput.class, "analogSensor");

        Differential.init(right, left, analogSensor);
    }

    /**
     * Initializes differential arm system.
     */
    public static void initDifferentialArm() {
        Servo right = hardwareMap.get(Servo.class, "rightDifferentialArm");
        Servo left = hardwareMap.get(Servo.class, "leftDifferentialArm");

        DifferentialArm.init(right, left);
    }

    /**
     * Initializes claw system.
     */
    public static void initClaw() {
        Servo claw = hardwareMap.get(Servo.class, "claw");
        ColorRangeSensor distanceSensor = hardwareMap.get(ColorRangeSensor.class, "distanceSensor");

        Claw.init(claw, distanceSensor);
    }

    /**
     * Initializes intake arm system.
     */
    public static void initIntakeArm() {
        Servo right = hardwareMap.get(Servo.class, "rightIntakeArm");
        Servo left = hardwareMap.get(Servo.class, "leftIntakeArm");

        IntakeArm.init(right, left);
    }

    /**
     * Initializes LED system.
     */
    public static void initLED() {
        RevBlinkinLedDriver LEDConfig = hardwareMap.get(RevBlinkinLedDriver.class, "LED");

        LED.init(LEDConfig);
    }

    /**
     * Initializes vertical lift system.
     */
    public static void initVerticalLift() {
        DcMotorEx left = hardwareMap.get(DcMotorEx.class, "leftVerticalLift");
        DcMotorEx right = hardwareMap.get(DcMotorEx.class, "rightVerticalLift");

        TempVerticalLift.init(left, right);
    }

    /**
     * Initializes horizontal lift system.
     */
    public static void initHorizontalLift() {

    }

    /**
     * Initializes hang system.
     */
    public static void initHang() {

    }

    /**
     * Initializes husky lens system.
     */
    public static void initHuskyLens() {

    }

    /**
     * Initializes intake system.
     */
    public static void initIntake() {

    }
}
