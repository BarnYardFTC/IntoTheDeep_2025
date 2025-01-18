package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.subSystems.Differential;
import org.firstinspires.ftc.teamcode.subSystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subSystems.Lift;
import org.firstinspires.ftc.teamcode.subSystems.LiftArm;

public class TeleOpFunctions {

    /*
    The only variable the class requires as an input.
    The other variable are variables which are initialized based on this input variable.
    */
    private static Gamepad gamepad;

    //
    private static boolean is_reset;
    private static GamepadEx gamepadEx;
    private static TriggerReader RIGHT_TRIGGER;
    private static TriggerReader LEFT_TRIGGER;

    public static void setGamepad(Gamepad gamepad){

    }

    public static boolean isReset() {
        return is_reset;
    }

    public static void setIs_reset(boolean is_reset) {
        TeleOpFunctions.is_reset = is_reset;
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

    public static void initializeRightTrigger() {
        RIGHT_TRIGGER = new TriggerReader(gamepadEx, GamepadKeys.Trigger.RIGHT_TRIGGER, 0.1);
    }

    public static TriggerReader getLeftTrigger() {
        return LEFT_TRIGGER;
    }

    public static void setLeftTrigger(TriggerReader leftTrigger) {
        LEFT_TRIGGER = leftTrigger;
    }

    private static void resetRobot() {
        if (is_reset) {
            reset();
            is_reset = true;
        }
    }

    public static void runAll(Gamepad gamepad) {
        Drivetrain.move(gamepad);
        Drivetrain.resetImu(gamepad);

        LiftArm.liftArmPID();
        Lift.liftPID();
    }

    private static void reset() {
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.Y)) {
            Differential.reset();
            LiftArm.move(LiftArm.Angle.HORIZONTAL);
            Lift.move(Lift.Pos.RESET);
        }
    }
}
