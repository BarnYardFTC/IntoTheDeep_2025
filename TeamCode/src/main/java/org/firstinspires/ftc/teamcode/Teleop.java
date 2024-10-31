package org.firstinspires.ftc.teamcode;

// Imports.

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import java.io.PrintWriter;
import java.io.StringWriter;

// TeleOp name.
@TeleOp(name = "INTO_THE_DEEP")

public class Teleop extends LinearOpMode {
    /**
     * Functions for initialization of the hardware.
     * Each function gets the name of the hardware and assigns it to a variable.
     * The variables are given to a each classes inner initialization function.
     */
    // TODO: Change names of all hardware in configuration.

    /**
     * Initializes ignition system.
     */
    private void initIgnitionSystem() {
        DcMotorEx frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        DcMotorEx frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        DcMotorEx backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        DcMotorEx backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        IMU imu = hardwareMap.get(IMU.class, "IMU");

        IgnitionSystem.init(frontLeft, frontRight, backLeft, backRight, imu);
    }

    /**
     * Initializes differential system.
     */
    private void initDifferential() {
        Servo right = hardwareMap.get(Servo.class, "rightDifferential");
        Servo left = hardwareMap.get(Servo.class, "leftDifferential");
        AnalogInput analogSensor = hardwareMap.get(AnalogInput.class, "analogSensor");

        Differential.init(right, left, analogSensor);
    }

    /**
     * Initializes differential arm system.
     */
    private void initDifferentialArm() {
        Servo right = hardwareMap.get(Servo.class, "rightDifferentialArm");
        Servo left = hardwareMap.get(Servo.class, "leftDifferentialArm");

        DifferentialArm.init(right, left);
    }

    /**
     * Initializes claw system.
     */
    private void initClaw() {
        Servo claw = hardwareMap.get(Servo.class, "claw");
        ColorRangeSensor distanceSensor = hardwareMap.get(ColorRangeSensor.class, "distanceSensor");

        Claw.init(claw, distanceSensor);
    }

    /**
     * Initializes intake arm system.
     */
    private void initIntakeArm() {
        Servo right = hardwareMap.get(Servo.class, "rightIntakeArm");
        Servo left = hardwareMap.get(Servo.class, "leftIntakeArm");

        IntakeArm.init(right, left);
    }

    /**
     * Initializes LED system.
     */
    private void initLED() {
        RevBlinkinLedDriver LED = hardwareMap.get(RevBlinkinLedDriver.class, "LED");

        org.firstinspires.ftc.teamcode.LED.init(LED);
    }

    /**
     * Initializes vertical lift system.
     */
    private void initVerticalLift() {
        DcMotorEx left = hardwareMap.get(DcMotorEx.class, "leftVerticalLift");
        DcMotorEx right = hardwareMap.get(DcMotorEx.class, "rightVerticalLift");

        TempVerticalLift.init(left, right);
    }

    /**
     * Initializes horizontal lift system.
     */
    private void initHorizontalLift() {

    }

    /**
     * Initializes hang system.
     */
    private void initHang() {

    }

    /**
     * Initializes husky lens system.
     */
    private void initHuskyLens() {

    }

    /**
     * Initializes intake system.
     */
    private void initIntake() {

    }

    // Functions which work based on a rc input.
    // Each main functions can use multiple functions and systems.

    /**
     * Moves all parts to be ready for specimen intake.
     * Allows automated collection of a specimen.
     *
     * @param x - Gampad1 x button input.
     */
    private void collectSpecimen(boolean x) {
        Differential.collectSpecimen();
        Claw.collectSpecimen();
    }

    /**
     * Moves all parts to be ready for sample intake.
     * Allows automated collection of an alliance colored sample.
     *
     * @param a - Gampad1 a button input.
     */
    private void collectAllianceColoredSample(boolean a) {

    }

    /**
     * Moves all parts to be ready for sample intake.
     * Allows automated collection of a yellow colored sample.
     *
     * @param y - Gampad1 y button input.
     */
    private void collectYellowColoredSample(boolean y) {

    }

    /**
     * Unloads a sample and resets all parts of the robot.
     *
     * @param b - Gampad1 b button input.
     */
    private void unload(boolean b) {

    }

    /**
     * Moves all parts to be ready for sample or specimen unload in their high position.
     * It check rather a sample or a specimen needs to be unloaded.
     *
     * @param rBumper - Gampad1 rBumper button input.
     */
    private void moveToHighUnloadingPosition(boolean rBumper) {

    }

    /**
     * Moves all parts to be ready for sample or specimen unload in their low position.
     * It check rather a sample or a specimen needs to be unloaded.
     *
     * @param lBumper - Gampad1 lBumper button input.
     */
    private void moveToLowUnloadingPosition(boolean lBumper) {

    }

    /**
     * Starts 2nd level ascend.
     * If pressed again it goes for 3rd level ascend.
     *
     * @param dpadUp - Gampad1 dpadUp button input.
     */
    private void climb(boolean dpadUp) {

    }

    @Override
    public void runOpMode() {

        waitForStart();

        // Initializing

        // Main Loop
        while (opModeIsActive()) {

            // We use a try & catch block so that any error in the main loop will stop the robot and add the error line to the telemetry.
            try {
                IgnitionSystem.move(gamepad1);
                collectAllianceColoredSample(gamepad1.a);
                collectYellowColoredSample(gamepad1.y);
                collectSpecimen(gamepad1.x);
                moveToHighUnloadingPosition(gamepad1.right_bumper);
                moveToLowUnloadingPosition(gamepad1.left_bumper);
                unload(gamepad1.b);
                climb(gamepad1.dpad_up);
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String stackTrace = sw.toString();
                telemetry.addData("stackTrace", stackTrace);
                telemetry.update();
                throw e;
            }
        }
    }
}