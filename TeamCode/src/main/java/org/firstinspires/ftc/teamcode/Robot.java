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
    private static Gamepad gamepad;
    // Variables which are determined by the class based on the gamepad input
    private static GamepadEx gamepadEx;
    private static TriggerReader RIGHT_TRIGGER;
    private static TriggerReader LEFT_TRIGGER;
    private static boolean automating_intake = false;
    private static Deadline rate_limit_intake;
    private static boolean started_waiting_intake = false;

    private static boolean automating_discharge = false;
    private static Deadline rate_limit_discharge;
    private static boolean started_waiting_discharge = false;

    public static void initialize(OpMode opMode) {
        //VisionProcessor.initialize(opMode);
        LiftArm.initialize(opMode);
        Lift.initialize(opMode);
        Drivetrain.initialize(opMode);
        Differential.initialize(opMode);
        Claw.initialize(opMode);
        Robot.opMode = opMode;
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

    public static void initializeTeleop(OpMode opMode) {
        initialize(opMode);
        initializeGamepad(opMode.gamepad1);
    }

    public static void autonomousSetup(){
        Differential.reset();
        Claw.close();
    }

    public static void activateClaw() {
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.Y)) {
            if (Claw.isOpen()) {
                Claw.close();
                automating_intake = true;
            } else {
                Claw.open();
                if (LiftArm.isVertical()) {
                    automating_discharge = true;
                }
            }
        }
    }

    public static void activateDifferential() {
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.A)) {
            if (Differential.isReseted())
                Differential.collectSample();
            else
                Differential.reset();
        }
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.X)) {
            if (Differential.isReseted())
                Differential.collectSpecimen();
            else
                Differential.reset();
        }
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT)) {
            Differential.move(Differential.currentRollAngle + 20, 0);
        }
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)) {
            Differential.move(Differential.currentRollAngle - 20, 0);
        }
    }

    public static void activateLift() {
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.DPAD_DOWN) && !LiftArm.isHorizontal()) {
            Lift.move(Lift.Pos.LOW_BASKET);
        } else if (gamepadEx.wasJustPressed(GamepadKeys.Button.DPAD_UP) && !LiftArm.isHorizontal()) {
            Lift.move(Lift.Pos.HIGH_BASKET);
        } else if (RIGHT_TRIGGER.isDown() && Lift.isMoveable(1)) {
            Lift.move(1);
        } else if (LEFT_TRIGGER.isDown() && Lift.isMoveable(-1)) {
            Lift.move(-1);
        }
    }

    public static void activateLiftArm() {
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)) {
            LiftArm.move(LiftArm.Angle.VERTICAL);
        } else if (gamepadEx.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)) {
            LiftArm.move(LiftArm.Angle.HORIZONTAL);
        }
    }

    public static void activateDrivetrain() {
        Drivetrain.move(gamepad);
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.B))
            Drivetrain.resetImu();
    }

    public static void activateVisionProcessor() {
        // ToDo: Write a code for sample collection. Than add this method to activateAll() and remove the comment that prevents the HuskyLens' initialization
    }

    public static void activateAll() {
        activateDrivetrain();

        if (automating_intake) {
            if (finishedWaitingIntake()) {
                Differential.reset();
                automating_intake = false;
            }
        } else {
            activateClaw();
        }

        if (automating_discharge) {
            if (finishedWaitingDischarge()) {
                if (Differential.isReseted()) {
                    Differential.collectSample();
                }
                if (Lift.isReseted()) {
                    Differential.reset();
                    LiftArm.move(LiftArm.Angle.HORIZONTAL);
                } else {
                    Lift.move(Lift.Pos.RESET);
                }
                if (LiftArm.isHorizontal()) {
                    automating_discharge = false;
                }
            }
        } else {
            activateLift();
            activateLiftArm();
            activateDifferential();
        }

        // Shit that always keeps on working in the background
        Lift.liftPID();
        LiftArm.liftArmPID();
        gamepadEx.readButtons();
        RIGHT_TRIGGER.readValue();
        LEFT_TRIGGER.readValue();
    }

    public static void reset() {
        Differential.reset();
        LiftArm.move(LiftArm.Angle.HORIZONTAL);
        Lift.move(Lift.Pos.RESET);
    }

    public static void displayTelemetry() {
        opMode.telemetry.addData("heading", Drivetrain.getRobotHeading());
        opMode.telemetry.addData("posArm", LiftArm.getCurrentAngle());
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

    public static boolean finishedWaitingDischarge() {
        if (!started_waiting_discharge) {
            rate_limit_discharge = new Deadline(300, TimeUnit.MILLISECONDS);
            started_waiting_discharge = true;
            return false;
        }
        if (rate_limit_discharge.hasExpired()) {
            rate_limit_discharge = null;
            started_waiting_discharge = false;
            return true;
        }
        return false;
    }
}
