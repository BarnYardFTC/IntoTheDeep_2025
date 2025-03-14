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

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subSystems.Claw;
import org.firstinspires.ftc.teamcode.subSystems.Differential;
import org.firstinspires.ftc.teamcode.subSystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subSystems.Lift;
import org.firstinspires.ftc.teamcode.subSystems.LiftArm;
import org.firstinspires.ftc.teamcode.subSystems.LimeLight;
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
 * X: Differential prepare sample
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
    private static boolean isResetAutomating;
    public static boolean isHighBasketAutomating;
    public static boolean isSampleCollectionAutomating;
    public static boolean isSpecimenPreparationAutomating;
    public static boolean isSpecimenScoreAutomating;
    public static boolean isMaintainingPositionAutonomous;

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
//        VisionProcessor.initialize(opMode);
        LiftArm.initialize(opMode);
        Lift.initialize(opMode);
        Drivetrain.initialize(opMode);
        Differential.initialize(opMode);
        Claw.initialize(opMode);
        LimeLight.initialize(opMode);
        initializeOpMode(opMode);

        // reset flags
        isResetAutomating = false;
        isHighBasketAutomating = false;
        isSampleCollectionAutomating = false;
        isSpecimenPreparationAutomating = false;
        isSpecimenScoreAutomating = false;
        isMaintainingPositionAutonomous = false;
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

    public static boolean isInitialized(){
        return Robot.opMode != null;
    }

    /**
     * Move all the systems of the robot to where they should be at the beginning of the teleop
     */
    public static void teleopSetup(){
        Differential.collectSample();
        Claw.open();
    }
    /**
     * Move all the systems of the robot to where they should be at the beginning of the autonomous
     */
    public static void autonomousSetup(){
        Differential.collectSample();
        Claw.close();
    }

    /*
    ===========Flag functions===========
    */
    public static boolean is_reset_automating(){
        return isResetAutomating;
    }
    public static boolean is_high_basket_automating(){
        return isHighBasketAutomating;
    }
    public static boolean is_sample_collection_automating(){
        return isSampleCollectionAutomating;
    }
    public static boolean is_specimen_preparation_automating(){
        return isSpecimenPreparationAutomating;
    }
    public static boolean is_specimen_score_automating(){
        return isSpecimenScoreAutomating;
    }

    public static boolean isLiftAutomating(){
        return is_reset_automating() || is_high_basket_automating() || is_specimen_score_automating() || is_specimen_preparation_automating();
    }
    public static boolean isLiftArmAutomating(){
        return is_reset_automating() || is_high_basket_automating() || is_specimen_score_automating() || is_specimen_preparation_automating();
    }
    public static boolean isDifferentialAutomating(){
        return is_reset_automating() || is_sample_collection_automating() || is_specimen_preparation_automating() || is_specimen_score_automating() || is_high_basket_automating();
    }
    public static boolean isClawAutomating(){
        return is_sample_collection_automating();
    }

    /*
    ==========Automations==========
    */
    public static Action highBasketDeposit() {
        return new SequentialAction(
                setAutomationFlags(false, true, false, false, false),
                new ParallelAction(
                        LiftArm.liftArmVertical(),
                        Differential.moveToDefaultAction(),
                        Differential.rollScoreBasket()
                ),
                Lift.highBasketOverShootAction(),
                Lift.liftHighBasket()
        );
    }

    public static Action sampleCollectionForAutonomous(){
        return new SequentialAction(
                Differential.moveToDefaultAction(),
                Claw.openClaw(),
                Lift.sampleCollectionAction(),
                Differential.CollectSampleAction(),
                Robot.sleep(Differential.MOVEMENT_DURATION),
                Claw.closeClaw(),
                Robot.sleep(Claw.CLAW_MOVEMENT_DURATION),
                Differential.moveToDefaultAction(),
                Lift.moveLift(Lift.Pos.RESET)
        );
    }
    public static Action sampleCollectionAtAngleForAutonomous(){
        return new SequentialAction();
    }

    public static Action resetFromHighBasket(){
        return new SequentialAction(
                new ParallelAction(
                    Differential.moveToDefaultAction(),
                    Differential.resetRollAction(),
                    setAutomationFlags(true, false, false, false, false),
                    Lift.moveLift(Lift.Pos.RESET)
                ),
                LiftArm.liftArmHorizontal(),
                Robot.sleep(LiftArm.LIFT_ARM_HORIZONTAL_SETTLE_TIME),
                Lift.hardReset()
        );
    }

    public static Action regularReset(){
        return new SequentialAction(
                new ParallelAction(
                    Differential.moveToDefaultAction(),
                    Differential.resetRollAction(),
                    setAutomationFlags(true, false, false, false, false),
                    Lift.moveLift(Lift.Pos.RESET)
                ),
                LiftArm.liftArmHorizontal(),
                Lift.hardReset()
        );
    }
    public static Action reset() {
        if (LiftArm.isHorizontal()){
            return regularReset();
        }
        else {
            return resetFromHighBasket();
        }
    }

    public static Action collectSample(){
        return new SequentialAction(
                setAutomationFlags(false, false, true, false, false),
                Differential.CollectSampleAction(),
                sleep(Differential.MOVEMENT_DURATION),
                Claw.closeClaw(),
                sleep(Claw.CLAW_MOVEMENT_DURATION),
                Differential.moveToDefaultAction()
        );
    }

    public static Action prepareSpecimen(){
        return new SequentialAction(
                setAutomationFlags(false, false, false, false, true),
                new ParallelAction(
                        LiftArm.liftArmVertical(),
                        Differential.differentialPrepareSpecimen()
                ),
                Robot.sleep(LiftArm.LIFT_ARM_VERTICAL_SETTLE_TIME),
                Lift.hardReset(),
                Lift.moveLift(Lift.Pos.PREPARE_SPECIMEN)
        );
    }

    public static Action scoreSpecimen(){
        return new SequentialAction(
                setAutomationFlags(false, false, false, true, false),
                Differential.differentialScoreSpecimen(),
                Robot.sleep(Differential.MOVEMENT_DURATION),
                new ParallelAction(
                    LiftArm.liftArmHorizontal(),
                    new SequentialAction(
                            Robot.sleep(LiftArm.SPECIMEN_SCORE_TIME),
                            Claw.openClaw()
                    )
                ),
                regularReset()
        );
    }


    /*
    ========ACTIONS===========
     */

    public static Action setAutomationFlags(boolean is_reset_automating, boolean is_high_basket_automating, boolean is_sample_collection_automating, boolean is_specimen_score_automating, boolean is_specimen_preparation_automating){
        return new SetAutomationsFlags(is_reset_automating, is_high_basket_automating, is_sample_collection_automating, is_specimen_score_automating, is_specimen_preparation_automating);
    }
    private static class SetAutomationsFlags implements Action{
        private final boolean reset;
        private final boolean high_basket;
        private final boolean sample_collection;
        private final boolean specimen_score;
        private final boolean specimen_preparation;

        public SetAutomationsFlags(boolean is_reset_automating, boolean is_high_basket_automating, boolean is_sample_collection_automating, boolean is_specimen_score_automating, boolean is_specimen_preparation_automating){
            reset = is_reset_automating;
            high_basket = is_high_basket_automating;
            sample_collection = is_sample_collection_automating;
            specimen_score = is_specimen_score_automating;
            specimen_preparation = is_specimen_preparation_automating;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            Robot.isResetAutomating = reset;
            Robot.isHighBasketAutomating = high_basket;
            Robot.isSampleCollectionAutomating = sample_collection;
            Robot.isSpecimenScoreAutomating = specimen_score;
            Robot.isSpecimenPreparationAutomating = specimen_preparation;

            return false;
        }
    }


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
                if (gamepadEx1.wasJustPressed(GamepadKeys.Button.Y)) {
                    // Toggle the claw open or closed
                    if (Claw.isOpen()) {
                        if ((Differential.isPreSample() || Differential.isCollectSample()) && Lift.isDifferentialMoveable()) {
                            currentAutomation = collectSample();
                            isSampleCollectionAutomating = true;
                        }
                    }
                    else{
                        Claw.open();
                    }
                }
            }

            if (currentAutomation != null){
                if (!currentAutomation.run(packet) ){
                    isSampleCollectionAutomating = false;
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
            if (!isDifferentialAutomating() && Lift.isDifferentialMoveable()) {

                if (gamepadEx1.wasJustPressed(GamepadKeys.Button.A)){
                    Differential.preSample();
                }
                else if (gamepadEx1.wasJustPressed(GamepadKeys.Button.DPAD_UP)){
                    Differential.reset();
                } else if (gamepadEx1.wasJustPressed(GamepadKeys.Button.X)) {
                    Differential.moveToDefault();
                }
                else if (gamepadEx2.wasJustPressed(GamepadKeys.Button.X)){
                    Differential.preSample();
                    Claw.open();
                }
            }
            if (gamepadEx1.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT)) {
                if (Differential.currentRollAngle + 60 <= 180){
                    Differential.move(Differential.currentRollAngle + 60, Differential.currentPitchAngle);
                }
                else {
                    Differential.move(0, Differential.currentPitchAngle);
                }
            } else if (gamepadEx1.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)) {
                if (Differential.currentRollAngle - 60 >= 0){
                    Differential.move(Differential.currentRollAngle - 60, Differential.currentPitchAngle);
                }
                else {
                    Differential.move(180, Differential.currentPitchAngle);
                }
            }

            return true; // Keep this action running during TeleOp
        }
    }
    private static class ActivateLift implements Action {
        private Action currentAutomation;

        public ActivateLift(){
            currentAutomation = null;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            // Check if a new automation is requested
            if (gamepadEx2.wasJustPressed(GamepadKeys.Button.A)) {
                currentAutomation = reset();
            } else if (gamepadEx2.wasJustPressed(GamepadKeys.Button.Y)) {
                currentAutomation = highBasketDeposit();
            }


            // If a manual input is received, cancel all automations
            if (RIGHT_TRIGGER.isDown() || LEFT_TRIGGER.isDown() || gamepadEx1.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER) || gamepadEx1.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)) {
                Lift.enablePid();

                if (RIGHT_TRIGGER.isDown() && Lift.isMoveable(1)) {
                    Lift.move(1);
                } else if (LEFT_TRIGGER.isDown() && Lift.isMoveable(-1)) {
                    Lift.move(-1);
                }

                isResetAutomating = false;
                isSpecimenPreparationAutomating = false;
                isSpecimenScoreAutomating = false;
                isHighBasketAutomating = false;
            }

            // If an automation is running, execute it until completion
            if (currentAutomation != null) {
                if (!currentAutomation.run(packet)) {
                    // Mark respective automation as inactive when finished
                    isSpecimenPreparationAutomating = false;
                    isSpecimenScoreAutomating = false;
                    isResetAutomating = false;
                    isHighBasketAutomating = false;
                    currentAutomation = null; // Reset currentAutomation
                }
            }

            if (!is_reset_automating()&& !is_specimen_preparation_automating()){
                Lift.enablePid();
                if (currentAutomation == reset()){
                    currentAutomation = null;
                }
            }

            if (!is_reset_automating() && !is_high_basket_automating() && !is_specimen_score_automating() && !is_specimen_preparation_automating()){
                currentAutomation = null;
            }

            return true; // Keep this action running during TeleOp
        }
    }


    private static class ActivateLiftArm implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            // Check if the LiftArm is currently automating
            // If it is, we don't want to interfere with its movement

//             If the right bumper was just pressed, move the LiftArm to the vertical position
            if (gamepadEx1.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)) {

                isSpecimenPreparationAutomating = false;
                isSpecimenScoreAutomating = false;
                isResetAutomating = false;
                isHighBasketAutomating = false;
                LiftArm.move(LiftArm.Angle.VERTICAL);

                // If the left bumper was just pressed, move the LiftArm to the horizontal position
            } else if (gamepadEx1.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)) {

                isSpecimenPreparationAutomating = false;
                isSpecimenScoreAutomating = false;
                isResetAutomating = false;
                isHighBasketAutomating = false;

                if (LiftArm.getTargetAngle() == LiftArm.VERTICAL_ANGLE){
                    Differential.resetRoll();
                }
                LiftArm.move(LiftArm.Angle.HORIZONTAL);
                Differential.moveToDefault();
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
           if (gamepadEx2.isDown(GamepadKeys.Button.DPAD_DOWN)){
               Drivetrain.regularMode();
           }
           else if (Lift.isReseted() && !LiftArm.isVertical() || !Lift.isReseted() && LiftArm.isVertical() && Claw.isOpen()){
               Drivetrain.regularMode();
           }
           else {
               Drivetrain.slowMode();
           }

           // Return true to indicate that the action should continue running and check conditions again
           return true;
        }
    }

    private static class ActivateLimeLight implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (gamepadEx2.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)) {
                LimeLight.pipeLineSwitch(LimeLight.pipeLine.RED);
            }
            if (gamepadEx2.wasJustPressed(GamepadKeys.Button.DPAD_UP)) {
                LimeLight.pipeLineSwitch(LimeLight.pipeLine.YELLOW);
            }
            if (gamepadEx2.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT)) {
                LimeLight.pipeLineSwitch(LimeLight.pipeLine.BLUE);
            }

            if (gamepadEx2.wasJustPressed(GamepadKeys.Button.B)) {
                LimeLight.startTracking();
                Differential.move(0, 20);
            }
            if (gamepadEx2.wasJustPressed(GamepadKeys.Button.Y)) {
                LimeLight.stopTracking();
                Differential.collectSample();
            }

            if (gamepadEx2.wasJustPressed(GamepadKeys.Button.A)) {
                Lift.move(Lift.getTargetPos() + LimeLight.getDistance());
                Differential.move(LimeLight.getAngle(), 0);
            }
            return true;
        }
    }

    private static class Sleep implements Action {
        private final TimerHelper timer;
        private final int durationMilliseconds;

        // Constructor to set the duration
        public Sleep(int durationMilliseconds) {
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
//            Lift.displayData(opMode.telemetry);
//            opMode.telemetry.addData("LiftArm angle", LiftArm.getCurrentAngle());
//            opMode.telemetry.addData("LiftArm power", LiftArm.getRightMotor().getPower());
            opMode.telemetry.addData("angle", LimeLight.getAngle());
            opMode.telemetry.addData("distance", LimeLight.getDistance());
            opMode.telemetry.addData("pipeLine", LimeLight.getPipeline());
            opMode.telemetry.update();
            return true;
        }
    }

    public static void printAutomationsData(Telemetry telemetry){
        telemetry.addData("reset automating?", is_reset_automating());
        telemetry.addData("high basket automating? ", is_high_basket_automating());
        telemetry.addData("sample collection automating?", is_sample_collection_automating());
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
    public static Action activateLimeLight() {
        return new ActivateLimeLight();
    }
    public static Action activateDrivetrain() {
        return new ActivateDrivetrain();
    }
    public static Action displayTelemetry(){
        return new DisplayTelemetry();
    }
    public static Action sleep(int durationMilliseconds) {
        return new Sleep(durationMilliseconds);
    }


}