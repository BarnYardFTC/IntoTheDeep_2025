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
 *  A: Move differential to Collect-Sample/Reset position
 *  B: Reset IMU's heading
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
    private static OpMode opMode;
    private static Gamepad gamepad;

    // Variables which are determined by the class based on the gamepad input
    private static GamepadEx gamepadEx;
    private static TriggerReader RIGHT_TRIGGER;
    private static TriggerReader LEFT_TRIGGER;

    // Final Variables
    private static final double TRIGGERS_THRESHOLD = 0.1; // When the trigger value exceeds this value, the trigger is considered active

    private static boolean started_waiting = false;
    private static Deadline rate_limit;

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

    public static void initializeTeleop(OpMode opMode){
        initialize(opMode);
        initializeGamepad(opMode.gamepad1);
    }

    /**
     * BOG = Based On Gamepad
     * This means that the following functions are built ONLY for Teleop usage
     */
    public static void activateClaw() {
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.Y)){
            if (Claw.isOpen()) {
                Claw.close();
                // ToDo: Automate in future
            }
            else {
                Claw.open();
                // ToDo: Automate in future
            }
        }
    }
    public static void activateDifferential() {
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
    public static void activateLift() {
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.DPAD_DOWN) && !LiftArm.isHorizontal()){
            Lift.move(Lift.Pos.LOW_BASKET);
        }
        else if (gamepadEx.wasJustPressed(GamepadKeys.Button.DPAD_UP) && !LiftArm.isHorizontal()){
            Lift.move(Lift.Pos.HIGH_BASKET);
        }
        else if (RIGHT_TRIGGER.isDown() && Lift.isMoveable(1)) {
            Lift.move(1);
        }
        else if (LEFT_TRIGGER.isDown() && Lift.isMoveable(-1)) {
            Lift.move(-1);
        }
        Lift.liftPID();
    }
    public static void activateLiftArm() {
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)) {
            LiftArm.move(LiftArm.Angle.VERTICAL);
        }
        else if (gamepadEx.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)) {
            LiftArm.move(LiftArm.Angle.HORIZONTAL);
        }
        LiftArm.liftArmPID();
    }
    public static void activateDrivetrain() {
        Drivetrain.move(gamepad);
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.B))
            Drivetrain.resetImu();
    }
    public static void activateVisionProcessor() {
        // ToDo: Write a code for sample collection. Than add this method to activateAll() and remove the comment that prevents the HuskyLens' initialization
    }
    public static void activateAll(){
        activateClaw();
        activateDrivetrain();
        activateDifferential();
        activateLift();
        activateLiftArm();
        gamepadEx.readButtons();
        RIGHT_TRIGGER.readValue();
        LEFT_TRIGGER.readValue();
    }

    public static void reset() {
        Differential.reset();
        LiftArm.move(LiftArm.Angle.HORIZONTAL);
        Lift.move(Lift.Pos.RESET);
    }

    public static void displayTelemetry(){
        opMode.telemetry.addData("Differential left: ", Differential.servos[1].getPosition());
        opMode.telemetry.addData("Differential right: ", Differential.servos[0].getPosition());
        opMode.telemetry.addData("Claw pos: ", Claw.getPosition());
        opMode.telemetry.update();
    }

    public static boolean finishedWaiting(int milliseconds){
        if (!started_waiting){
            rate_limit = new Deadline(milliseconds, TimeUnit.MILLISECONDS);
            started_waiting = true;
            return false;
        }
        if (rate_limit.hasExpired()){
            rate_limit = null;
            started_waiting = false;
            return true;
        }
        return false;
    }
}
