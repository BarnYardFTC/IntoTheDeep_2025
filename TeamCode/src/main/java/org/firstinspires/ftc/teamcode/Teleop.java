package org.firstinspires.ftc.teamcode;

// Imports.

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
import org.firstinspires.ftc.teamcode.subSystems.DifferentialWrist;
import org.firstinspires.ftc.teamcode.subSystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subSystems.LED;
import org.firstinspires.ftc.teamcode.subSystems.LiftArm;

// TeleOp name.
@TeleOp(name = "INTO_THE_DEEP")

public class Teleop extends LinearOpMode {
    private boolean reseted;
    private GamepadEx gamepadEx;

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

        initClaw();
        initDifferential();
        initDifferentialWrist();
        initDriveTrain();
        initLED();
        initHuskyLens();
        initLiftArm();
    }

    /**
     * Move all robot parts to their starting position.
     * Makes the robot not move between auto and teleop period and instead at start of teleop.
     */
    private void resetRobot() {
        if (reseted) {
            Claw.open();
            Differential.reset();
            DifferentialWrist.reset();
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
    private void initDifferentialWrist() {
        Servo right = hardwareMap.get(Servo.class, "rightDifferentialArm");
        Servo left = hardwareMap.get(Servo.class, "leftDifferentialArm");

        DifferentialWrist.init(right, left);
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
     * Initializes LED system.
     */
    private void initLED() {
        RevBlinkinLedDriver LEDConfig = hardwareMap.get(RevBlinkinLedDriver.class, "LED");

        LED.init(LEDConfig);
    }

    /**
     * Initializes husky lens system.
     */
    private void initHuskyLens() {

    }

    /**
     * Initializes lift arm system.
     */
    private void initLiftArm() {
        DcMotorEx right = hardwareMap.get(DcMotorEx.class, "right");
        DcMotorEx left = hardwareMap.get(DcMotorEx.class, "left");

        LiftArm.init(right, left);
    }


    // Functions which work based on a rc input.
    // Each main functions can use multiple functions and systems.

    /**
     * Operate all robot systems.
     */
    private void runAll() {
        Drivetrain.move(gamepad1);
        collectSample();
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
        if (gamepad1.x) {
            Differential.collectSpecimen();
        }
        Claw.collectSpecimen();
    }

    /**
     * Unloads a sample and resets all parts of the robot.
     */
    private void unload() {
        if (gamepad1.b) {
            Claw.open();
            Differential.reset();
            DifferentialWrist.reset();
            LiftArm.makeHorizontal();
        }
    }

    /**
     * Moves all parts to be ready for sample or specimen unload in their high position.
     * Checks rather a sample or a specimen needs to be unloaded.
     */
    private void moveToHighUnloadingPosition() {
        if (gamepad1.right_bumper) {
            LiftArm.makeVertical();
            if (Claw.isSpecimenCollected()) {
                Differential.unloadSpecimen();
                DifferentialWrist.unload();
                LED.changeColor(RevBlinkinLedDriver.BlinkinPattern.VIOLET);
            }
            if (Claw.isSampleCollected()) {
                Differential.unloadSample();
                LED.changeColor(RevBlinkinLedDriver.BlinkinPattern.VIOLET);
            }
        }
    }

    /**
     * Moves all parts to be ready for sample or specimen unload in their low position.
     * Checks rather a sample or a specimen needs to be unloaded.
     */
    private void moveToLowUnloadingPosition() {
        if (gamepad1.left_bumper) {
            if (Claw.isSpecimenCollected()) {
                Differential.unloadSpecimen();
                DifferentialWrist.unload();
                LED.changeColor(RevBlinkinLedDriver.BlinkinPattern.VIOLET);
            }
            if (Claw.isSampleCollected()) {
                LiftArm.makeVertical();
                Differential.unloadSample();
                LED.changeColor(RevBlinkinLedDriver.BlinkinPattern.VIOLET);
            }
        }
    }

    /**
     *
     */
    private void collectSample() {
        if (LiftArm.isHorizontal() && gamepad1.left_trigger > 0.05) {
            Differential.reset();
        }
        if (gamepad1.dpad_right) {
            Differential.rollRight();
        }
        if (gamepad1.dpad_left) {
            Differential.rollLeft();
        }
    }

    /**
     * Starts 2nd level ascend.
     * If pressed again it goes for 3rd level ascend.
     */
    private void climb() {
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.DPAD_UP)) {
            LED.changeColor(RevBlinkinLedDriver.BlinkinPattern.VIOLET);
        }
    }

    @Override
    public void runOpMode() {
        initLiftArm();

        waitForStart();

        // Main Loop
        while (opModeIsActive()) {
            if (gamepad1.right_bumper) {
                LiftArm.makeVertical();
            }
            if (gamepad1.left_bumper) {
                LiftArm.makeHorizontal();
            }
            LiftArm.liftArmPIDF();

            telemetry.log().clear();
            telemetry.addData("rPos", LiftArm.motors[0].getCurrentPosition());
            telemetry.addData("r", LiftArm.motors[0].getPower());
            telemetry.addData("lPos", LiftArm.motors[1].getCurrentPosition());
            telemetry.addData("l", LiftArm.motors[1].getPower());
            telemetry.update();
        }
    }
}