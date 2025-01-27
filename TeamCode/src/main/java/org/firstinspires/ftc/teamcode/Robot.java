package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.internal.system.Deadline;
import org.firstinspires.ftc.teamcode.subSystems.Claw;
import org.firstinspires.ftc.teamcode.subSystems.Differential;
import org.firstinspires.ftc.teamcode.subSystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subSystems.Lift;
import org.firstinspires.ftc.teamcode.subSystems.LiftArm;

import java.util.concurrent.TimeUnit;

/**
 * Gamepad Buttons Documentation:
 * A: Move differential to Collect-Sample/Reset position
 * B: Reset IMU's heading
 * X: Move differential to Collect-Specimen/Reset position
 * Y: Open/Close Claw. Claw opened – Reset every system of the robot | Claw closed – Reset only the differential
 * Dpad-Down: Move Lift to the Low Basket (When claw is opened the lift will automatically reset)
 * Dpad-Up: Move Lift to the High Basket (When claw is opened the lift will automatically reset)
 * Right-Trigger: Open the lift
 * Left-Trigger: Close the lift
 * Right-Bumper: Rotate the lift to it's vertical position
 * Left-Bumper: Rotate the lift to it's horizontal position
 */

public class Robot {

    // Final Variables
    private static final double TRIGGERS_THRESHOLD = 0.1; // When the trigger value exceeds this value, the trigger is considered active
    /*
    The only variable the class requires as an input.
    The other variable are variables which are initialized based on this input variable.
    */
    private static OpMode opMode;
    private static Gamepad gamepad1;
    private static Gamepad gamepad2;
    // Variables which are determined by the class based on the gamepad input
    private static GamepadEx gamepadEx1;
    private static GamepadEx gamepadEx2;
    private static TriggerReader RIGHT_TRIGGER;
    private static TriggerReader LEFT_TRIGGER;

    private static boolean automating_intake = false;
    private static Deadline rate_limit_intake;
    private static boolean started_waiting_intake = false;

    private static boolean automating_reset = false;
    private static Deadline rate_limit_reset;
    private static boolean started_waiting_reset = false;

    private static boolean automating_high_basket_deposit = false;


    public static void initialize(OpMode opMode) {
        //VisionProcessor.initialize(opMode);
        LiftArm.initialize(opMode);
        Lift.initialize(opMode);
        Drivetrain.initialize(opMode);
        Differential.initialize(opMode);
        Claw.initialize(opMode);
        Robot.opMode = opMode;

        automating_high_basket_deposit = false;
        automating_reset = false;
    }

    private static void initializeGamepadEx() {
        Robot.gamepadEx1 = new GamepadEx(gamepad1);
        Robot.gamepadEx2 = new GamepadEx(gamepad2);
    }

    private static void initializeRightTrigger() {
        RIGHT_TRIGGER = new TriggerReader(gamepadEx1, GamepadKeys.Trigger.RIGHT_TRIGGER, TRIGGERS_THRESHOLD);
    }

    private static void initializeLeftTrigger() {
        LEFT_TRIGGER = new TriggerReader(gamepadEx1, GamepadKeys.Trigger.LEFT_TRIGGER, TRIGGERS_THRESHOLD);
    }

    private static void initializeGamepad(Gamepad gamepad1, Gamepad gamepad2) {
        Robot.gamepad1 = gamepad1;
        Robot.gamepad2 = gamepad2;
        initializeGamepadEx();
        initializeRightTrigger();
        initializeLeftTrigger();
    }

    public static void initializeTeleop(OpMode opMode) {
        initialize(opMode);
        initializeGamepad(opMode.gamepad1, opMode.gamepad2);
    }

    public static void autonomousSetup(){
        Differential.reset();
        Claw.close();
    }

    public static void activateClaw() {
        if (gamepadEx1.wasJustPressed(GamepadKeys.Button.Y)) {
            if (Claw.isOpen()) {
                Claw.close();
                automating_intake = true;
            } else {
                Claw.open();
            }
        }
    }

    public static void activateDifferential() {
        if (gamepadEx1.wasJustPressed(GamepadKeys.Button.A)) {
            if (Differential.isReseted())
                Differential.collectSample();
            else
                Differential.reset();
        }
        if (gamepadEx1.wasJustPressed(GamepadKeys.Button.X)) {
            if (Differential.isReseted())
                Differential.collectSpecimen();
            else
                Differential.reset();
        }
        if (gamepadEx1.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT) && Differential.currentRollAngle + 60 <= 180) {
            Differential.move(Differential.currentRollAngle + 60, Differential.currentPitchAngle);
            Differential.currentRollAngle += 60;
        }
        if (gamepadEx1.wasJustPressed(GamepadKeys.Button.DPAD_LEFT) && Differential.currentRollAngle - 60 >= 0) {
            Differential.move(Differential.currentRollAngle - 60, Differential.currentPitchAngle);
            Differential.currentRollAngle -= 60;
        }
    }

    public static void activateLift() {
        if (gamepadEx1.wasJustPressed(GamepadKeys.Button.DPAD_DOWN) && LiftArm.isVertical()) {
            Lift.move(Lift.Pos.LOW_BASKET);
        } else if (gamepadEx1.wasJustPressed(GamepadKeys.Button.DPAD_UP) && LiftArm.isVertical()) {
            Lift.move(Lift.Pos.HIGH_BASKET);
        } else if (RIGHT_TRIGGER.isDown() && Lift.isMoveable(1)) {
            Lift.move(1);
        } else if (LEFT_TRIGGER.isDown() && Lift.isMoveable(-1)) {
            Lift.move(-1);
        }
    }

    public static void activateLiftArm() {
        if (gamepadEx1.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)) {
            LiftArm.move(LiftArm.Angle.VERTICAL);
        } else if (gamepadEx1.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)) {
            LiftArm.move(LiftArm.Angle.HORIZONTAL);
        }
    }

    public static void activateDrivetrain() {
        Drivetrain.move(gamepad1);
        if (gamepadEx1.wasJustPressed(GamepadKeys.Button.B))
            Drivetrain.resetImu();
        else if ((LiftArm.isHorizontal() && !Lift.isReseted()) || LiftArm.isVertical() || gamepadEx1.wasJustPressed(GamepadKeys.Button.X) && !Drivetrain.isSlowed()){
            Drivetrain.slowMode();
        }
        else if (gamepadEx1.wasJustPressed(GamepadKeys.Button.X) && Drivetrain.isSlowed()){
            Drivetrain.regularMode();
        }
        else {
            Drivetrain.regularMode();
        }
    }

    public static void activateVisionProcessor() {
        // ToDo: Write a code for sample collection. Than add this method to activateAll() and remove the comment that prevents the HuskyLens' initialization
    }

    public static void activateAll() {
        activateDrivetrain();

        if (!automating_reset && (LiftArm.isVertical() || LiftArm.isHorizontal()) && gamepadEx2.wasJustPressed(GamepadKeys.Button.A) && !Lift.isReseted()){
            automating_reset = true;
        }
        else if (automating_reset) {
            automating_reset = reset();
        }

        else if (!automating_high_basket_deposit && gamepadEx2.wasJustPressed(GamepadKeys.Button.Y)){
            automating_high_basket_deposit = true;
        }
        else if (automating_high_basket_deposit) {
            high_basket_deposit();
            if (Lift.getCurrentLength() > 40 && LiftArm.isVertical()) {
                automating_high_basket_deposit = false;
            }
        }
        else {
            activateLift();
            activateLiftArm();
            activateDifferential();

            if (automating_intake) {
                if (finishedWaitingIntake()) {
                    Differential.reset();
                    automating_intake = false;
                }
            }
            else {
                activateClaw();
            }
        }

        // Shit that always keeps on working in the background
        Lift.liftPID();
        LiftArm.liftArmPID();
        gamepadEx1.readButtons();
        gamepadEx2.readButtons();
        RIGHT_TRIGGER.readValue();
        LEFT_TRIGGER.readValue();
    }

    public static void high_basket_deposit(){
        if (LiftArm.isVertical()){
            Lift.move(Lift.Pos.HIGH_BASKET);
        }
        else {
            LiftArm.move(LiftArm.Angle.VERTICAL);
        }
    }

    public static boolean reset() {
        if (LiftArm.isVertical()) {
            if (Lift.isReseted()) {
                LiftArm.move(LiftArm.Angle.HORIZONTAL);
                Differential.reset();
            }
            else {
                Lift.move(Lift.Pos.RESET);
            }
            return !LiftArm.isHorizontal();
        }
        else if (LiftArm.isHorizontal()){
            Lift.move(Lift.Pos.RESET);
            return !Lift.isReseted();
        }
        else {
            return true;
        }
    }


    public static void displayTelemetry() {
        opMode.telemetry.addData("Lift reseted", Lift.isReseted());
        opMode.telemetry.addData("Lift target pos", Lift.getTargetPosCm());
        opMode.telemetry.addData("Lift current length", Lift.getCurrentLength());
        opMode.telemetry.addData("heading", Drivetrain.getRobotHeading());

        opMode.telemetry.update();
    }

    public static boolean finishedWaitingIntake() {
        if (!started_waiting_intake) {
            rate_limit_intake = new Deadline(200, TimeUnit.MILLISECONDS);
            started_waiting_intake = true;
            return false;
        }
        if (rate_limit_intake.hasExpired()) {
            rate_limit_intake = null;
            started_waiting_intake = false;
            return true;
        }
        return false;
    }

    public static boolean finishedWaitingReset() {
        if (!started_waiting_reset) {
            rate_limit_reset = new Deadline(200, TimeUnit.MILLISECONDS);
            started_waiting_reset = true;
            return false;
        }
        if (rate_limit_reset.hasExpired()) {
            rate_limit_reset = null;
            started_waiting_reset = false;
            return true;
        }
        return false;
    }
}
