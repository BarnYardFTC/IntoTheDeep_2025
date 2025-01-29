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
import org.firstinspires.ftc.teamcode.subSystems.TimerHelper;

/**
 * ========General Description=======
 * This class connects between the different classes of the project in order
 * to bring the software of the robot to life.
 * Here, you will see final and changing variables which are needed in order to connect
 * between the different classes.
 * This Robot class has functions & variables which are designed for the Autonomous programs,
 * and functions & variables which are designed for the teleop for the Teleop program.
 * The only input this class requires is the current opMode which is running.
 * Using that input only, the class is able to fully run the robot, both in the Teleop
 * and Autonomous periods.

 * =========Teleop========
 * In the Teleop period, the software runs based on inputs from two Gamepads which are
 * run by our Team's drivers. Following is a button guide:

 *  -Gamepad1-
 *  This gamepad is used by the main driver and it gives access to every system of the robot.
 * A: Move Differential down to collect-sample position
 * B: Reset the IMU (used in case of electrical induction which disrupts proper driving)
 * Y: Close Claw, followed by an automation of the differential when sample/specimen is collected
 * X: Activate/Deactivate slow mode
 * Joysticks: Drive the robot
 * Dpad-up: Move Lift to high-basket position
 * Dpad-down: Move Lift low-basket position
 * Dpad-right: Adjust Differential for sample intake
 * Dpad-left: Adjust Differential for sample intake
 * Right-Bumper: Move Arm to a vertical position
 * Left-Bumper: Move Arm to a horizontal position
 * Right-Trigger: Open the Lift 'Manually'
 * Left-Trigger: Close the Lift 'Manually'

 * -Gamepad2-
 * This gamepad serves an improvement by giving option for different automations of the robot.
 * This is an improvement because it takes tasks away from the main driver and by doing that
 * makes the operation of driving the robot faster.
 * A: Reset the Lift and Arm (=close lift & bring Arm down if necessary)
 * Y: Bring the Lift and Arm to a high-basket-discharge position

 * =========Autonomous========
 * // ToDo: Finish renovating the Autonomous and document here
 */



public class Robot {

    /**
     * Final Variables (Constants)
     */
    // When the trigger value exceeds this value, the trigger is considered active
    private static final double TRIGGERS_THRESHOLD = 0.1;

    // Waiting times
    private static final int INTAKE_DURATION = 200;

    /**
    The only variable the class requires as an input.
    The other variable are variables which are initialized based on this input variable.
    */
    private static OpMode opMode;

    /**
     * Gamepads we run the Teleop based on in the Teleop Period.
     * TriggerReader is used only for gamepad1
     */
    private static GamepadEx gamepadEx1;
    private static GamepadEx gamepadEx2;
    private static TriggerReader RIGHT_TRIGGER;
    private static TriggerReader LEFT_TRIGGER;

    /**
     * Flags - variables that we use in order to keep track of certain information about the robot
     */
    private static boolean automating_intake;
    private static boolean automating_reset;
    private static boolean automating_high_basket_deposit;


    /**
     * Time Tracking - variable used to keep track of time while an opMode runs
     */
    private static TimerHelper timer;



    /**
     * Initialize the Robot.
     * Usage: Teleop & Autonomous
     * @param opMode Current opMode which runs on the robot
     */
    public static void initialize(OpMode opMode) {

        // Initialize every system of the robot
        //VisionProcessor.initialize(opMode);
        LiftArm.initialize(opMode);
        Lift.initialize(opMode);
        Drivetrain.initialize(opMode);
        Differential.initialize(opMode);
        Claw.initialize(opMode);
        Robot.opMode = opMode;

        // reset flags
        automating_intake = false;
        automating_reset = false;
        automating_high_basket_deposit = false;

        // Create a timer
        timer = new TimerHelper();
    }

    private static void initializeGamepadEx(Gamepad gamepad1, Gamepad gamepad2) {
        Robot.gamepadEx1 = new GamepadEx(gamepad1);
        Robot.gamepadEx2 = new GamepadEx(gamepad2);
    }

    private static void initializeRightTrigger() {
        RIGHT_TRIGGER = new TriggerReader(gamepadEx1, GamepadKeys.Trigger.RIGHT_TRIGGER, TRIGGERS_THRESHOLD);
    }

    private static void initializeLeftTrigger() {
        LEFT_TRIGGER = new TriggerReader(gamepadEx1, GamepadKeys.Trigger.LEFT_TRIGGER, TRIGGERS_THRESHOLD);
    }

    private static void initializeGamepads(Gamepad gamepad1, Gamepad gamepad2) {
        initializeGamepadEx(gamepad1, gamepad2);
        initializeRightTrigger();
        initializeLeftTrigger();
    }

    public static void initializeTeleop(OpMode opMode) {
        initialize(opMode);
        initializeGamepads(opMode.gamepad1, opMode.gamepad2);
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
        Drivetrain.move(gamepadEx1);
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
                if (hasElapsed(INTAKE_DURATION)) {
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

    public static boolean hasElapsed(int durationMilliseconds){
        return timer.hasElapsed(durationMilliseconds);
    }


    public static int getIntakeDuration(){
        return INTAKE_DURATION;
    }
}
