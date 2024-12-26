package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.subSystems.Claw;
import org.firstinspires.ftc.teamcode.subSystems.Differential;
import org.firstinspires.ftc.teamcode.subSystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subSystems.LED;
import org.firstinspires.ftc.teamcode.subSystems.Lift;
import org.firstinspires.ftc.teamcode.subSystems.LiftArm;

public class TeleOpFunctions {
    private static boolean reseted;
    private static GamepadEx gamepadEx;
    private static TriggerReader RIGHT_TRIGGER;
    private static TriggerReader LEFT_TRIGGER;

    public static boolean isReseted() {
        return reseted;
    }

    public static void setReseted(boolean reseted) {
        TeleOpFunctions.reseted = reseted;
    }

    public static GamepadEx getGamepadEx() {
        return gamepadEx;
    }

    public static void setGamepadEx(GamepadEx gamepadEx) {
        TeleOpFunctions.gamepadEx = gamepadEx;
    }

    public static TriggerReader getRightTrigger() {
        return RIGHT_TRIGGER;
    }

    public static void setRightTrigger(TriggerReader rightTrigger) {
        RIGHT_TRIGGER = rightTrigger;
    }

    public static TriggerReader getLeftTrigger() {
        return LEFT_TRIGGER;
    }

    public static void setLeftTrigger(TriggerReader leftTrigger) {
        LEFT_TRIGGER = leftTrigger;
    }

    private void resetRobot() {
        if (reseted) {
            Claw.open();
            Differential.reset();
            LiftArm.move(LiftArm.Angle.HORIZONTAL);
            Lift.move(Lift.Pos.RESET);
            LED.changeColor(RevBlinkinLedDriver.BlinkinPattern.DARK_RED);
            reseted = true;
        }
    }

    private void runAll(Gamepad gamepad) {
        // Gamepad actions.
        Drivetrain.move(gamepad);
        collectSample();
        collectSpecimen();
        moveToHighUnloadingPosition();
        moveToLowUnloadingPosition();
        unload();


        // Passive actions.
        LiftArm.liftArmPID();
        Lift.liftPID();

        // Updating gamepad.
        gamepadEx.readButtons();
        RIGHT_TRIGGER.readValue();
        LEFT_TRIGGER.readValue();
    }

    private void collectSpecimen() {

    }

    private void unload() {

    }

    private void moveToHighUnloadingPosition() {

    }

    private void moveToLowUnloadingPosition() {

    }

    private void collectSample() {

    }
}
