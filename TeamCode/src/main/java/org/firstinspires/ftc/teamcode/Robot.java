package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.subSystems.Claw;
import org.firstinspires.ftc.teamcode.subSystems.Differential;
import org.firstinspires.ftc.teamcode.subSystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subSystems.Lift;
import org.firstinspires.ftc.teamcode.subSystems.LiftArm;
import org.firstinspires.ftc.teamcode.subSystems.VisionProcessor;

/**
 * Gamepad Buttons Documentation:
 *  A: Move differential to Collect-Sample/Reset position
 *  X: Move differential to Collect-Specimen/Reset position
 *  Y: Open/Close Claw. Claw opened – Reset every system of the robot | Claw closed – Reset only the differential
 *  Dpad-Down: Move Lift to the Low Basket (When claw is opened the lift will automatically reset)
 *  Dpad-Up: Move Lift to the High Basket (When claw is opened the lift will automatically reset)
 *  Right-Trigger: Open the lift
 *  Left-Trigger: Close the lift
 *  Right-Bumper: Rotate the lift to it's vertical position
 *  Left-Bumper: Rotate the lift to it's horizontal position
 */

public class Robot {

    /*
    The only variable the class requires as an input.
    The other variable are variables which are initialized based on this input variable.
    */
    private static Gamepad gamepad;

    // Variables which are determined by the class based on the gamepad input
    private static boolean is_reset;
    private static GamepadEx gamepadEx;
    private static TriggerReader RIGHT_TRIGGER;
    private static TriggerReader LEFT_TRIGGER;
    private static final double TRIGGERS_THRESHOLD = 0.1; // When the trigger value exceeds this value, the trigger is considered active

    private static void initialize(OpMode opMode) {
        VisionProcessor.initialize(opMode);
        LiftArm.initialize(opMode);
        Lift.initialize(opMode);
        Drivetrain.initialize(opMode);
        Differential.initialize(opMode);
        Claw.initialize(opMode);
    }

    private static void initializeGamepadEx() {
        Robot.gamepadEx = new GamepadEx(gamepad);
    }
    private static void initializeRightTrigger() {
        RIGHT_TRIGGER = new TriggerReader(gamepadEx, GamepadKeys.Trigger.RIGHT_TRIGGER, TRIGGERS_THRESHOLD);
    }
    private static void initializeLeftTrigger() {
    LEFT_TRIGGER = new TriggerReader(gamepadEx, GamepadKeys.Trigger.LEFT_TRIGGER, TRIGGERS_THRESHOLD);
    }
    private static void initializeGamepad(Gamepad gamepad) {
        Robot.gamepad = gamepad;
        initializeGamepadEx();
        initializeRightTrigger();
        initializeLeftTrigger();
    }


    public static void initializeTeleop(OpMode opMode){
        initialize(opMode);
        initializeGamepad(opMode.gamepad1);
    }


    public static GamepadEx getGamepadEx() {
        return gamepadEx;
    }
    public static TriggerReader getRightTrigger() {
        return RIGHT_TRIGGER;
    }
    public static TriggerReader getLeftTrigger() {
        return LEFT_TRIGGER;
    }
    public static boolean isReset() {
        return is_reset;
    }

    /**
     * BOG = Based On Gamepad
     * This means that the following functions are built ONLY for Teleop usage
     */
    public static void activateClawBOG() {
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.Y)){
            if (Claw.isOpen()) {
                Claw.close();
                Differential.reset();
            }
            else {
                Claw.open();
                resetRobot();
            }
        }
    }
    public static void activateDifferentialBOG() {
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.A)){
            if (Differential.isReseted())
                Differential.collectSample();
            else
                Differential.reset();
        }
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.X)){
            if (Differential.isReseted())
                Differential.collectSpecimen();
            else
                Differential.reset();
        }
    }
    public static void activateLiftBOG() {
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.DPAD_DOWN) && !LiftArm.isHorizontal()){
            Lift.move(Lift.Pos.LOW_BASKET);
        }
        else if (gamepadEx.wasJustPressed(GamepadKeys.Button.DPAD_UP) && LiftArm.isHorizontal()){
            Lift.move(Lift.Pos.HIGH_BASKET);
        }
        else if (RIGHT_TRIGGER.isDown() && LiftArm.isHorizontal() && Lift.isMoveable(1)) {
            Lift.move(1);
        }
        else if (RIGHT_TRIGGER.isDown() && !LiftArm.isHorizontal() && Lift.isMoveable(1)) {
            Lift.move(1);
        }

        // Todo: KEEP GOING HERE
    }
    public static void activateLiftArmBOG() {

    }
    public static void activateDrivetrainBOG() {

    }
    public static void activateVisionProcessorBOG() {

    }


    private static void resetRobot() {
        if (!is_reset) {
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
