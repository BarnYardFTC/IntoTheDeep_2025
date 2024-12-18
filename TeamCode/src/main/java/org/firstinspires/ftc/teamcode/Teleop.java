package org.firstinspires.ftc.teamcode;

// Imports.

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subSystems.Claw;
import org.firstinspires.ftc.teamcode.subSystems.Differential;
import org.firstinspires.ftc.teamcode.subSystems.DifferentialWrist;
import org.firstinspires.ftc.teamcode.subSystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subSystems.LED;
import org.firstinspires.ftc.teamcode.subSystems.LiftArm;
import org.firstinspires.ftc.teamcode.subSystems.Lift;

// TeleOp name.
@Config
@TeleOp(name = "INTO_THE_DEEP")

public class Teleop extends LinearOpMode {
    private boolean reseted;
    private GamepadEx gamepadEx;
    private TriggerReader RIGHT_TRIGGER;
    private TriggerReader LEFT_TRIGGER;

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

        Claw claw = new Claw(this);
        Differential differential = new Differential(this);
        DifferentialWrist differentialWrist = new DifferentialWrist(this);
        Drivetrain drivetrain = new Drivetrain(this);
        LED led = new LED(this);
        LiftArm liftArm = new LiftArm(this);
        Lift lift = new Lift(this);
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
            LiftArm.move(LiftArm.Angle.HORIZONTAL);
            Lift.move(Lift.Pos.RESET);
            LED.changeColor(RevBlinkinLedDriver.BlinkinPattern.DARK_RED);
            reseted = true;
        }
    }


    // Functions which work based on a rc input.
    // Each main functions can use multiple functions and systems.

    /**
     * Operate all robot systems.
     */
    private void runAll() {
        // Gamepad actions.
        Drivetrain.move(gamepad1);
        collectSample();
        collectSpecimen();
        moveToHighUnloadingPosition();
        moveToLowUnloadingPosition();
        unload();
        climb();
        LiftArm.liftArmPIDF();

        // Passive actions.

        // Updating gamepad.
        gamepadEx.readButtons();
        RIGHT_TRIGGER.readValue();
        LEFT_TRIGGER.readValue();
    }

    /**
     * Moves all parts to be ready for specimen intake.
     * Allows automated collection of a specimen.
     */
    private void collectSpecimen() {
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.X)) {
            Differential.collectSpecimen();
        }
        Claw.collectSpecimen();
    }

    /**
     * Unloads a sample and resets all parts of the robot.
     */
    private void unload() {
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.B)) {
            Claw.open();
            Differential.reset();
            DifferentialWrist.reset();
            LiftArm.move(LiftArm.Angle.HORIZONTAL);
        }
    }

    /**
     * Moves all parts to be ready for sample or specimen unload in their high position.
     * Checks rather a sample or a specimen needs to be unloaded.
     */
    private void moveToHighUnloadingPosition() {
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)) {
            LiftArm.move(LiftArm.Angle.VERTICAL);
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
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)) {
            if (Claw.isSpecimenCollected()) {
                Differential.unloadSpecimen();
                DifferentialWrist.unload();
                LED.changeColor(RevBlinkinLedDriver.BlinkinPattern.VIOLET);
            }
            if (Claw.isSampleCollected()) {
                LiftArm.move(LiftArm.Angle.VERTICAL);
                Differential.unloadSample();
                LED.changeColor(RevBlinkinLedDriver.BlinkinPattern.VIOLET);
            }
        }
    }

    /**
     *
     */
    private void collectSample() {
        if (LiftArm.getHorizontalPos() && LEFT_TRIGGER.wasJustPressed()) {
            Differential.reset();
        }
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT)) {
            Differential.rollRight();
        }
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)) {
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
        gamepadEx = new GamepadEx(gamepad1);
        RIGHT_TRIGGER = new TriggerReader(gamepadEx, GamepadKeys.Trigger.RIGHT_TRIGGER);
        LEFT_TRIGGER = new TriggerReader(gamepadEx, GamepadKeys.Trigger.LEFT_TRIGGER);

//        initializeAll();

        LiftArm liftArm = new LiftArm(this);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

                waitForStart();

        // Main Loop
        while (opModeIsActive()) {
//            if (gamepadEx.wasJustPressed(GamepadKeys.Button.A)) {
//                LiftArm.move(LiftArm.Angle.HORIZONTAL);
//            }
//            if (gamepadEx.wasJustPressed(GamepadKeys.Button.B)) {
//                LiftArm.move(LiftArm.Angle.VERTICAL);
//            }

            LiftArm.liftArmPIDF();
            telemetry.addData("pos", LiftArm.getTargetAngle());
            telemetry.addData("ff", Math.cos(LiftArm.targetAngle) * LiftArm.f);
            telemetry.addData("current", LiftArm.motors[0].getCurrentPosition());
            telemetry.update();
        }
    }
}