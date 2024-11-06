package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

import org.firstinspires.ftc.teamcode.subSystems.Claw;
import org.firstinspires.ftc.teamcode.subSystems.Differential;
import org.firstinspires.ftc.teamcode.subSystems.DifferentialArm;
import org.firstinspires.ftc.teamcode.subSystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subSystems.IntakeArm;
import org.firstinspires.ftc.teamcode.subSystems.LED;
import org.firstinspires.ftc.teamcode.subSystems.TempIntake;

public class Functions {
    public static GamepadEx gamepadEx1 = new GamepadEx(gamepad1);

    // Functions which work based on a rc input.
    // Each main functions can use multiple functions and systems.

    public static void runAll() {
        Drivetrain.move(gamepadEx1.gamepad);
        if (gamepadEx1.wasJustPressed(GamepadKeys.Button.DPAD_DOWN)) {
            Drivetrain.resetImu();
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
    public static void collectSpecimen() {
        if (gamepadEx1.wasJustPressed(GamepadKeys.Button.X)) {
            Differential.collectSpecimen();
        }
        Claw.collectSpecimen();
    }

    /**
     * Moves all parts to be ready for sample intake.
     * Allows automated collection of an alliance colored sample.
     */
    public static void collectAllianceColoredSample() {
        if (gamepadEx1.wasJustPressed(GamepadKeys.Button.A)) {
            if (TempIntake.isSampleCollected()) {
                IntakeArm.reset();
            }
        }
    }

    /**
     * Moves all parts to be ready for sample intake.
     * Allows automated collection of a yellow colored sample.
     */
    public static void collectYellowColoredSample() {
        if (gamepadEx1.wasJustPressed(GamepadKeys.Button.Y)) {
            if (TempIntake.isSampleCollected()) {
                IntakeArm.reset();
            }
        }
    }

    /**
     * Unloads a sample and resets all parts of the robot.
     */
    public static void unload() {
        if (gamepadEx1.wasJustPressed(GamepadKeys.Button.B)) {
            Claw.open();
            Differential.reset();
            DifferentialArm.reset();
        }
    }

    /**
     * Moves all parts to be ready for sample or specimen unload in their high position.
     * It check rather a sample or a specimen needs to be unloaded.
     */
    public static void moveToHighUnloadingPosition() {
        if (gamepadEx1.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)) {
            if (Claw.isSpecimenCollected()) {
                Differential.unloadSpecimen();
                DifferentialArm.unload();
                LED.changeColor(RevBlinkinLedDriver.BlinkinPattern.VIOLET);
            }
            if (TempIntake.isSampleCollected()) {
                Differential.unloadSample();
                LED.changeColor(RevBlinkinLedDriver.BlinkinPattern.VIOLET);
            }
        }
    }

    /**
     * Moves all parts to be ready for sample or specimen unload in their low position.
     * It check rather a sample or a specimen needs to be unloaded.
     */
    public static void moveToLowUnloadingPosition() {
        if (gamepadEx1.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)) {
            if (Claw.isSpecimenCollected()) {
                Differential.unloadSpecimen();
                DifferentialArm.unload();
                LED.changeColor(RevBlinkinLedDriver.BlinkinPattern.VIOLET);
            }
            if (TempIntake.isSampleCollected()) {
                Differential.unloadSample();
                LED.changeColor(RevBlinkinLedDriver.BlinkinPattern.VIOLET);
            }
        }
    }

    /**
     * Starts 2nd level ascend.
     * If pressed again it goes for 3rd level ascend.
     */
    public static void climb() {
        if (gamepadEx1.wasJustPressed(GamepadKeys.Button.DPAD_UP)) {
            LED.changeColor(RevBlinkinLedDriver.BlinkinPattern.VIOLET);
        }
    }
}
