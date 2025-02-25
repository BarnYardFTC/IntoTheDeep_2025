package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
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

import java.util.Objects;

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


 *  -Gamepad1-
 *  This gamepad is used by the main driver and it gives access to every system of the robot.
 * A: Move Differential to collect-sample-position/score-position
 * B: Reset the IMU (used in case of electrical induction which disrupts proper driving)
 * Y: Close Claw, followed by an automation of the differential when sample/specimen is collected
 * X: Move Differential to collect-specimen/specimen-pre-score-position
 * Joysticks: Drive the robot
 * Dpad-up: Move Lift to high-basket position
 * Dpad-down: loosen grip automation
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
 * B: Score a specimen on the high chamber
 * Dpad-down: Slow mode
 */



public class Robot {

    /*
    =======FINAL VARIABLES (CONSTANTS)========
    */
    // When the trigger value exceeds this value, the trigger is considered active
    private static final double TRIGGERS_THRESHOLD = 0.1;
    // The time it takes between when a sample is closed and the robot moved away from the basket

    /*
    The only variable the class requires as an input.
    The other variable are variables which are initialized based on this input variable.
    */
    private static OpMode opMode;


    /*
    Gamepads we run the Teleop based on in the Teleop Period.
    TriggerReader is used only for gamepad1
    */
    private static GamepadEx gamepadEx1;
    private static GamepadEx gamepadEx2;
    private static TriggerReader RIGHT_TRIGGER;
    private static TriggerReader LEFT_TRIGGER;


    /*
    =========FLAGS=========
     */
    // Flags
    public static boolean is_reset_automating;
    public static boolean is_high_basket_automating;
    public static boolean is_specimen_automating;
    public static boolean is_grip_loosening_automating;
    public static boolean is_sample_collection_automating;
    public static boolean is_specimen_collection_automating;

    public static boolean is_resetting_from_high_basket;

    private static boolean is_manual_regular_mode;

    /*
    ========INITIALIZATION METHODS========
     */
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
        initializeOpMode(opMode);

        // reset flags
        is_reset_automating = false;
        is_high_basket_automating = false;
        is_specimen_automating = false;
        is_grip_loosening_automating = false;
        is_sample_collection_automating = false;
        is_specimen_collection_automating = false;

        is_resetting_from_high_basket = false;

        is_manual_regular_mode = false;
    }
    public static void initializeOpMode(OpMode opMode){
        Robot.opMode = opMode;
    }
    /**
     * Assign the GamepadEx variables using the gamepads provided as an input.
     * GamepadEx is basically a better version of Gamepad
     * @param gamepad1 user1
     * @param gamepad2 user2
     */
    private static void initializeGamepadEx(Gamepad gamepad1, Gamepad gamepad2) {
        Robot.gamepadEx1 = new GamepadEx(gamepad1);
        Robot.gamepadEx2 = new GamepadEx(gamepad2);
    }
    /**
     * Initialize the two triggers of gamepad1 so that they are ready for usage (gamepad2 uses no triggers)
     */
    private static void initializeTriggers() {
        RIGHT_TRIGGER = new TriggerReader(gamepadEx1, GamepadKeys.Trigger.RIGHT_TRIGGER, TRIGGERS_THRESHOLD);
        LEFT_TRIGGER = new TriggerReader(gamepadEx1, GamepadKeys.Trigger.LEFT_TRIGGER, TRIGGERS_THRESHOLD);
    }
    /**
     * Prepare the gamepads for provision of inputs
     * @param gamepad1 gamepad1/user1
     * @param gamepad2 gamepad2/user2
     */
    private static void initializeGamepads(Gamepad gamepad1, Gamepad gamepad2) {
        initializeGamepadEx(gamepad1, gamepad2);
        initializeTriggers();
    }
    /**
     * Initialize everything needed to fully run a Teleop (driver controlled)
     * @param opMode Current opMode which is running
     */
    public static void initializeTeleop(OpMode opMode) {
        initialize(opMode);
        initializeGamepads(opMode.gamepad1, opMode.gamepad2);
    }
    /**
     * Move all the systems of the robot to where they should be at the beginning of the teleop
     */
    public static void teleopSetup(){
        Differential.reset();
        Claw.close();
    }
    /**
     * Move all the systems of the robot to where they should be at the beginning of the autonomous
     */
    public static void autonomousSetup(){
        Differential.reset();
        is_resetting_from_high_basket = true;
        Claw.close();
    }

    /*
    ===========Flag functions===========
    */
    // ToDo: Add is_specimen_automating when method is made
    public static boolean isLiftAutomating(){
        return is_reset_automating || is_high_basket_automating;
    }
    public static boolean isLiftArmAutomating(){
        return is_reset_automating || is_high_basket_automating;
    }
    public static boolean isDifferentialAutomating(){
        return is_reset_automating || is_sample_collection_automating || is_specimen_collection_automating || is_high_basket_automating;
    }
    public static boolean isClawAutomating(){
        return is_grip_loosening_automating || is_sample_collection_automating || is_specimen_collection_automating;
    }

    /*
    ==========Automations==========
    */
    public static Action highBasketDeposit() {
        return new SequentialAction(
                new ParallelAction(
                        LiftArm.liftArmVertical(),
                        Differential.differentialScore(),
                        Differential.moveDifferential90()
                ),
                Robot.hasElapsed(LiftArm.LIFT_ARM_VERTICAL_SETTLE_TIME),
                Lift.liftHighBasketGoal(),
                Lift.liftHighBasket()
        );
    }
    public static Action resetFromHighBasket(){
        return new SequentialAction(
                Lift.moveLift(Lift.Pos.RESET),
                new ParallelAction(
                        LiftArm.liftArmHorizontal(),
                        Differential.differentialUp()
                ),
                Robot.hasElapsed(LiftArm.LIFT_ARM_HORIZONTAL_SETTLE_TIME),
                Lift.hardReset()
        );
    }
    public static Action regularReset(){
        return new SequentialAction(
                Lift.moveLift(Lift.Pos.RESET),
                new ParallelAction(
                        LiftArm.liftArmHorizontal(),
                        Differential.differentialUp()
                ),
                Lift.hardReset()
        );
    }
    public static Action reset() {
        if (LiftArm.isVertical()){
            return resetFromHighBasket();
        }
        else {
            return regularReset();
        }
    }

    public static Action loosenClawGrip(){
        return new SequentialAction(
                Claw.loosenClawGrip(),
                hasElapsed(Claw.LOOSEN_GRIP_DURATION),
                Claw.closeClaw()
        );
    }
    public static Action collectSample(){
        return new SequentialAction(
                Claw.closeClaw(),
                hasElapsed(Claw.CLAW_MOVEMENT_DURATION),
                Differential.differentialScore()
        );
    }
    public static Action collectSpecimen(){
        return new SequentialAction(
                Claw.closeClaw(),
                hasElapsed(Claw.CLAW_MOVEMENT_DURATION),
                Differential.differentialReset()
        );
    }

    public static Action prepareSpecimen(){
        return new SequentialAction(
                new ParallelAction(
                        LiftArm.liftArmVertical(),
                        Differential.differentialPrepareSpecimen()
                ),
                Robot.hasElapsed(LiftArm.LIFT_ARM_VERTICAL_SETTLE_TIME)
        );
    }


    /*
    ========ACTIONS===========
     */
    private static class ActivateGamepads implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            // Read the inputs from both gamepads and triggers
            gamepadEx1.readButtons();
            gamepadEx2.readButtons();
            RIGHT_TRIGGER.readValue();
            LEFT_TRIGGER.readValue();

            return true; // Keep this action running during TeleOp
        }
    }
    private static class ActivateClaw implements Action {
        private Action currentAutomation = null;
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            // Check if the Y button was just pressed
            if (!isClawAutomating()){
//                if (gamepadEx1.wasJustPressed(GamepadKeys.Button.RIGHT_STICK_BUTTON)){
//                    currentAutomation = loosenClawGrip();
//                    is_claw_automating = true;
//                }
                if (gamepadEx1.wasJustPressed(GamepadKeys.Button.Y)) {
                    // Toggle the claw open or closed
                    if (Claw.isOpen()) {
                        Claw.close();
                        if (Differential.isCollectSample()){
                            currentAutomation = collectSample();
                            is_sample_collection_automating = true;
                        }
                        else if (Differential.isCollectSpecimen()){
                            currentAutomation = collectSpecimen();
                            is_specimen_collection_automating = true;
                        }
                    } else {
                        Claw.open();
                    }
                }
            }

            if (currentAutomation != null){
                if (!currentAutomation.run(packet)){
                    is_grip_loosening_automating = false;
                    is_sample_collection_automating = false;
                    is_specimen_collection_automating = false;
                    currentAutomation = null;
                }
            }

            return true; // Keep this action running during TeleOp
        }
    }
    private static class ActivateDifferential implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            // Only allow manual control if no automation is running
            if (!isDifferentialAutomating()) {
                if (gamepadEx1.wasJustPressed(GamepadKeys.Button.A)) {
                    if (Differential.isCollectSample()){
                        Differential.score();
                    }
                    else if (Differential.isReset() && LiftArm.isVertical()){
                        Differential.score();
                    }
                    else {
                        Differential.collectSample();
                    }
                } else if (gamepadEx1.wasJustPressed(GamepadKeys.Button.X)) {
                    if (Differential.isCollectSpecimen()){
                        Differential.reset();
                    }
                    else {
                        Differential.collectSpecimen();
                    }
                }

                else if (gamepadEx1.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT) && Differential.currentRollAngle + 60 <= 180) {
                    Differential.move(Differential.currentRollAngle + 60, Differential.currentPitchAngle);
                } else if (gamepadEx1.wasJustPressed(GamepadKeys.Button.DPAD_LEFT) && Differential.currentRollAngle - 60 >= 0) {
                    Differential.move(Differential.currentRollAngle - 60, Differential.currentPitchAngle);
                }
            }



            return true; // Keep this action running during TeleOp
        }
    }
    private static class ActivateLift implements Action {
        private Action currentAutomation = null;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            // Check if a new automation is requested
            if (gamepadEx2.wasJustPressed(GamepadKeys.Button.A)) {
                if (!LiftArm.isHorizontal()){
                    is_resetting_from_high_basket = true;
                }
                currentAutomation = reset();
                is_reset_automating = true;
                is_high_basket_automating = false;
                is_specimen_automating = false;
            } else if (gamepadEx2.wasJustPressed(GamepadKeys.Button.Y)) {
                currentAutomation = highBasketDeposit();
                is_high_basket_automating = true;
                is_reset_automating = false;
                is_specimen_automating = false;
            }
            else if (gamepadEx1.wasJustPressed(GamepadKeys.Button.DPAD_UP) && LiftArm.isVertical()) {
                currentAutomation = highBasketDeposit();
                is_high_basket_automating = true;
                is_reset_automating = false;
                is_specimen_automating = false;
            }
//        else if (gamepadEx2.wasJustPressed(GamepadKeys.Button.B)){
//            currentAutomation = scoreSpecimenAndReset();
//            is_specimen_automating = true;
//            is_reset_automating = false;
//            is_high_basket_automating = false;
//        }

            // If a manual input is received, cancel all automations
            if (RIGHT_TRIGGER.isDown() || LEFT_TRIGGER.isDown() || gamepadEx1.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER) || gamepadEx1.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)) {
                if (!Lift.pid_on){
                    Lift.pid_on = true;
                }
                if (RIGHT_TRIGGER.isDown() && Lift.isMoveable(1)) {
                    Lift.move(1);
                } else if (LEFT_TRIGGER.isDown() && Lift.isMoveable(-1)) {
                    Lift.move(-1);
                }
                if (!(Objects.equals(currentAutomation, reset()) && LiftArm.isHorizontal() && Lift.isReseted())){
                    currentAutomation = null;
                    is_reset_automating = false;
                    is_resetting_from_high_basket = false;
                    is_specimen_automating = false;
                }
            }

            // If an automation is running, execute it until completion
            if (currentAutomation != null) {
                if (!currentAutomation.run(packet)) {
                    // Mark respective automation as inactive when finished
                    is_reset_automating = false;
                    is_high_basket_automating = false;
                    is_specimen_automating = false;
                    currentAutomation = null; // Reset currentAutomation
                }
            }

            if (!is_reset_automating && !is_high_basket_automating && !is_specimen_automating){
                currentAutomation = null;
            }
            if (!is_reset_automating){
                is_resetting_from_high_basket = false;
            }

            return true; // Keep this action running during TeleOp
        }
    }


    private static class ActivateLiftArm implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            // Check if the LiftArm is currently automating
            // If it is, we don't want to interfere with its movement

            // If the right bumper was just pressed, move the LiftArm to the vertical position
            if (gamepadEx1.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)) {

                is_reset_automating = false;
                is_high_basket_automating = false;
                is_specimen_automating = false;
                LiftArm.move(LiftArm.Angle.VERTICAL);

                // If the left bumper was just pressed, move the LiftArm to the horizontal position
            } else if (gamepadEx1.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)) {

                is_reset_automating = false;
                is_high_basket_automating = false;
                is_specimen_automating = false;

                Differential.up();
                LiftArm.move(LiftArm.Angle.HORIZONTAL);
            }

            if (gamepadEx1.getButton(GamepadKeys.Button.DPAD_DOWN)){
                LiftArm.PID_on = false;
                LiftArm.motors[0].setPower(-1);
                LiftArm.motors[1].setPower(-1);
            }
            if (gamepadEx1.getButton(GamepadKeys.Button.DPAD_UP)){
                LiftArm.PID_on = false;
                LiftArm.motors[0].setPower(1);
                LiftArm.motors[1].setPower(1);
            }
            if (!gamepadEx1.getButton(GamepadKeys.Button.DPAD_DOWN) && !gamepadEx1.getButton(GamepadKeys.Button.DPAD_UP)){
                LiftArm.PID_on = true;
                LiftArm.motors[0].setPower(0);
                LiftArm.motors[1].setPower(0);
            }


            // Return true so this action keeps running throughout the TeleOp period,
            // allowing continuous input response
            return true;
        }
    }

    private static class ActivateDrivetrain implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            // Move the drivetrain based on the gamepad input
            Drivetrain.move(gamepadEx1);

            // Check if the B button is pressed to reset the IMU
            if (gamepadEx1.wasJustPressed(GamepadKeys.Button.B)) {
                Drivetrain.resetImu();
            }
            // Check if the lift arm is horizontal and lift is not reset, or if the lift arm is vertical
            // or if the X button is pressed and the drivetrain is not slowed
            if ((gamepadEx1.wasJustPressed(GamepadKeys.Button.DPAD_DOWN) || gamepadEx2.wasJustPressed(GamepadKeys.Button.DPAD_DOWN))) {
                if (Drivetrain.isSlowed()){
                    Drivetrain.regularMode();
                    is_manual_regular_mode = true;
                }
                else {
                    Drivetrain.slowMode();
                    is_manual_regular_mode = false;
                }
            }
            else if ((!Lift.isReseted() || Differential.isDown() || LiftArm.isVertical()) && !is_manual_regular_mode){
                Drivetrain.slowMode();
            }
            else {
                Drivetrain.regularMode();
            }

            // Return true to indicate that the action should continue running and check conditions again
            return true;
        }
    }
    private static class HasElapsed implements Action {
        private final TimerHelper timer;
        private final int durationMilliseconds;

        // Constructor to set the duration
        public HasElapsed(int durationMilliseconds) {
            this.durationMilliseconds = durationMilliseconds;
            this.timer = new TimerHelper();
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            // Return true if the specified duration has elapsed
            return !timer.hasElapsed(durationMilliseconds);
        }
    }
    private static class DisplayTelemetry implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            opMode.telemetry.addData("powerR", LiftArm.getRightMotor().getPower());
            opMode.telemetry.addData("powerL", LiftArm.getLeftMotor().getPower());
            opMode.telemetry.update();
            return true;
        }
    }

    public static Action activateGamepads() {
        return new ActivateGamepads();
    }
    public static Action activateDifferential() {
        return new ActivateDifferential();
    }
    public static Action activateClaw() {
        return new ActivateClaw();
    }
    public static Action activateLift() {
        return new ActivateLift();
    }
    public static Action activateLiftArm() {
        return new ActivateLiftArm();
    }
    public static Action activateDrivetrain() {
        return new ActivateDrivetrain();
    }
    public static Action displayTelemetry(){
        return new DisplayTelemetry();
    }
    public static Action hasElapsed(int durationMilliseconds) {
        return new HasElapsed(durationMilliseconds);
    }


}