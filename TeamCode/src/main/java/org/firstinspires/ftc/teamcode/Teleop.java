package org.firstinspires.ftc.teamcode;

// Imports.

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
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
import org.firstinspires.ftc.teamcode.subSystems.TempIntake;
import org.firstinspires.ftc.teamcode.subSystems.TempVerticalLift;

import java.io.PrintWriter;
import java.io.StringWriter;

// TeleOp name.
@TeleOp(name = "INTO_THE_DEEP")

public class Teleop extends LinearOpMode {
    private boolean reseted;
    private boolean wasAPressed;
    private boolean wasBPressed;
    private boolean wasYPressed;
    private boolean wasXPressed;
    private boolean wasLBumperPressed;
    private boolean wasRBumperPressed;
    private boolean wasDPadUpPressed;
    private boolean wasDPadDownPressed;

    /*
     * Functions for initialization of the hardware.
     * Each function gets the name of the hardware and assigns it to a variable.
     * The variables are given to a each classes inner initialization function.
     */
    // TODO: Change names of all hardware in configuration.

    /**
     * Initialize all hardware.
     */
    private void initializeAll() {
        reseted = false;
        wasAPressed = false;
        wasBPressed = false;
        wasYPressed = false;
        wasXPressed = false;
        wasLBumperPressed = false;
        wasRBumperPressed = false;
        wasDPadUpPressed = false;
        wasDPadDownPressed = false;

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
     * Makes the robot not move between auto and teleop period and instead at start of teleop.
     */
    private void resetRobot() {
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
    private void initDriveTrain() {
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
        RevBlinkinLedDriver LEDConfig = hardwareMap.get(RevBlinkinLedDriver.class, "LED");

        LED.init(LEDConfig);
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
     * Operate all robot systems.
     */
    private void runAll() {
        Drivetrain.move(gamepad1);
        if (gamepad1.dpad_down && !wasDPadDownPressed) {
            Drivetrain.resetImu();
            wasDPadDownPressed = true;
        }
        if (!gamepad1.dpad_down) {
            wasDPadDownPressed = false;
        }
        collectAllianceColoredSample();
        collectYellowColoredSample();
        collectSpecimen();
        moveToHighUnloadingPosition();
        moveToLowUnloadingPosition();
        unload();
        climb();
    }

    /**
     * Moves all parts to be ready for specimen intake.
     * Allows automated collection of a specimen.
     */
    private void collectSpecimen() {
        if (gamepad1.x && !wasXPressed) {
            Differential.collectSpecimen();
            wasXPressed = true;
        }
        if (!gamepad1.x) {
            wasXPressed = false;
        }
        Claw.collectSpecimen();
    }

    /**
     * Moves all parts to be ready for sample intake.
     * Allows automated collection of an alliance colored sample.
     */
    private void collectAllianceColoredSample() {
        if (gamepad1.a && !wasAPressed) {
            if (TempIntake.isSampleCollected()) {
                IntakeArm.reset();
            }
            wasAPressed = true;
        }
        if (!gamepad1.a) {
            wasAPressed = false;
        }
    }

    /**
     * Moves all parts to be ready for sample intake.
     * Allows automated collection of a yellow colored sample.
     */
    private void collectYellowColoredSample() {
        if (gamepad1.y && !wasYPressed) {
            if (TempIntake.isSampleCollected()) {
                IntakeArm.reset();
            }
            wasYPressed = true;
        }
        if (!gamepad1.y) {
            wasYPressed = false;
        }
    }

    /**
     * Unloads a sample and resets all parts of the robot.
     */
    private void unload() {
        if (gamepad1.b && !wasBPressed) {
            Claw.open();
            Differential.reset();
            DifferentialArm.reset();
            wasBPressed = true;
        }
        if (!gamepad1.b) {
            wasBPressed = false;
        }
    }

    /**
     * Moves all parts to be ready for sample or specimen unload in their high position.
     * Checks rather a sample or a specimen needs to be unloaded.
     */
    private void moveToHighUnloadingPosition() {
        if (gamepad1.right_bumper && !wasRBumperPressed) {
            if (Claw.isSpecimenCollected()) {
                Differential.unloadSpecimen();
                DifferentialArm.unload();
                LED.changeColor(RevBlinkinLedDriver.BlinkinPattern.VIOLET);
            }
            if (TempIntake.isSampleCollected()) {
                Differential.unloadSample();
                LED.changeColor(RevBlinkinLedDriver.BlinkinPattern.VIOLET);
            }
            wasRBumperPressed = true;
        }
        if (!gamepad1.right_bumper) {
            wasRBumperPressed = false;
        }
    }

    /**
     * Moves all parts to be ready for sample or specimen unload in their low position.
     * Checks rather a sample or a specimen needs to be unloaded.
     */
    private void moveToLowUnloadingPosition() {
        if (gamepad1.left_bumper && !wasLBumperPressed) {
            if (Claw.isSpecimenCollected()) {
                Differential.unloadSpecimen();
                DifferentialArm.unload();
                LED.changeColor(RevBlinkinLedDriver.BlinkinPattern.VIOLET);
            }
            if (TempIntake.isSampleCollected()) {
                Differential.unloadSample();
                LED.changeColor(RevBlinkinLedDriver.BlinkinPattern.VIOLET);
            }
            wasLBumperPressed = true;
        }
        if (!gamepad1.left_bumper) {
            wasLBumperPressed = false;
        }
    }

    /**
     * Starts 2nd level ascend.
     * If pressed again it goes for 3rd level ascend.
     */
    private void climb() {
        if (gamepad1.dpad_up && !wasDPadUpPressed) {
            LED.changeColor(RevBlinkinLedDriver.BlinkinPattern.VIOLET);
            wasDPadUpPressed = true;
        }
        if (!gamepad1.dpad_up) {
            wasDPadUpPressed = false;
        }
    }

    @Override
    public void runOpMode() {
//        initializeAll();
        initDriveTrain();

        waitForStart();

        // Main Loop
        while (opModeIsActive()) {
//            resetRobot();
//            runAll();
            Drivetrain.move(gamepad1);
            if (gamepad1.dpad_down && !wasDPadDownPressed) {
                Drivetrain.resetImu();
                wasDPadDownPressed = true;
            }
            if (!gamepad1.dpad_down) {
                wasDPadDownPressed = false;
            }
            telemetry.log().clear();
            telemetry.addData("Heading", Drivetrain.getRobotHeading());
            telemetry.update();
        }
    }
}