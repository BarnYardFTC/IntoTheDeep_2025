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
    private static boolean automated_intake;
    private static boolean automated_reset;
    private static boolean automated_high_basket_deposit;


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
        automated_intake = false;
        automated_reset = false;
        automated_high_basket_deposit = false;

        // Create a timer
        timer = new TimerHelper();
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
     * Move all the systems of the robot to where they should be at the beginning of the autonomous
     */
    public static void autonomousSetup(){
        Differential.reset();
        Claw.close();
    }

    /**
     * Activate the gamepads
     */
    public static void activateGamepads(){
        gamepadEx1.readButtons();
        gamepadEx2.readButtons();
        RIGHT_TRIGGER.readValue();
        LEFT_TRIGGER.readValue();
    }

    /**
     * Activate the claw according to gamepad inputs
     */
    public static void activateClaw() {
        if (!automated_intake){
            intake();
            automated_intake = didIntake();
        }
        else if (gamepadEx1.wasJustPressed(GamepadKeys.Button.Y) && notAutomating()) {
            if (Claw.isOpen()) {
                Claw.close();
                automated_intake = false;
            } else {
                Claw.open();
            }
        }
    }

    /**
     * Active the differential according to gamepad inputs
     */
    public static void activateDifferential() {
        if (!automated_reset){
            reset();
            automated_reset = didReset();
        }
        else if (gamepadEx1.wasJustPressed(GamepadKeys.Button.A)) {
            if (Differential.isReseted())
                Differential.collectSample();
            else
                Differential.reset();
        }
        else if (gamepadEx1.wasJustPressed(GamepadKeys.Button.X)) {
            if (Differential.isReseted())
                Differential.collectSpecimen();
            else
                Differential.reset();
        }
        else if (gamepadEx1.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT) && Differential.currentRollAngle + 60 <= 180) {
            Differential.move(Differential.currentRollAngle + 60, Differential.currentPitchAngle);
            Differential.currentRollAngle += 60;
        }
        else if (gamepadEx1.wasJustPressed(GamepadKeys.Button.DPAD_LEFT) && Differential.currentRollAngle - 60 >= 0) {
            Differential.move(Differential.currentRollAngle - 60, Differential.currentPitchAngle);
            Differential.currentRollAngle -= 60;
        }
    }

    /**
     * Activate the lift according to gamepad inputs
     */
    public static void activateLift() {
        if (!automated_reset){
            reset();
            automated_reset = didReset();
        }
        else if (!automated_high_basket_deposit){
            highBasketDeposit();
            automated_high_basket_deposit = didArriveHighBasket();
        }
        else if (gamepadEx2.wasJustPressed(GamepadKeys.Button.A) && notAutomating()){
            automated_reset = false;
        }
        else if (gamepadEx2.wasJustPressed(GamepadKeys.Button.Y)) {
            automated_high_basket_deposit = false;
        }
        else if (gamepadEx1.wasJustPressed(GamepadKeys.Button.DPAD_DOWN) && LiftArm.isVertical()) {
            Lift.move(Lift.Pos.LOW_BASKET);
        } else if (gamepadEx1.wasJustPressed(GamepadKeys.Button.DPAD_UP) && LiftArm.isVertical()) {
            Lift.move(Lift.Pos.HIGH_BASKET);
        } else if (RIGHT_TRIGGER.isDown() && Lift.isMoveable(1)) {
            Lift.move(1);
        } else if (LEFT_TRIGGER.isDown() && Lift.isMoveable(-1)) {
            Lift.move(-1);
        }

        Lift.liftPID();
    }

    /**
     * Activate the liftArm according to gamepad inputs
     */
    public static void activateLiftArm() {
        if (!automated_reset){
            reset();
            automated_reset = didReset();
        }
        if (!automated_high_basket_deposit){
            highBasketDeposit();
            automated_high_basket_deposit = didArriveHighBasket();
        }
        else if (gamepadEx1.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)) {
            LiftArm.move(LiftArm.Angle.VERTICAL);
        } else if (gamepadEx1.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)) {
            LiftArm.move(LiftArm.Angle.HORIZONTAL);
        }

        LiftArm.liftArmPID();
    }

    /**
     * Activate the driveTrain according to gamepad inputs
     */
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

    /**
     * Activate all of the systems of the bot at once
     */
    public static void activateAll() {
        activateDrivetrain();
        activateLift();
        activateLiftArm();
        activateClaw();
        activateDifferential();
        activateGamepads();
    }

    /**
     * An automation function for a high basket deposit
     */
    public static void highBasketDeposit(){
        if (LiftArm.isVertical()){
            Lift.move(Lift.Pos.HIGH_BASKET);
        }
        else {
            LiftArm.move(LiftArm.Angle.VERTICAL);
        }
    }

    /**
     * An automation for a reset of the robot (lift and liftArm)
     */
    public static void reset() {
        if (Lift.isReseted()){
            Differential.reset(); //ToDo: Add a break here if needed
            LiftArm.move(LiftArm.Angle.HORIZONTAL);
        }
        else {
            Lift.move(Lift.Pos.RESET);
        }
    }

    /**
     * Intake a sample/specimen from the floor
     */
    public static void intake() {
        if (hasElapsed(INTAKE_DURATION)){
            Differential.reset();
        }
    }

    /**
     * @return true if the lift and liftArm are in the position for a high-basket-deposit
     */
    public static boolean didArriveHighBasket(){
        return Lift.getCurrentLength() > Lift.ARRIVED_HIGH_BASKET_POS && LiftArm.isVertical();
    }
    /**
     * @return true if the robot has reset already
     */
    public static boolean didReset(){
        return Lift.arrivedTargetPos() && LiftArm.isHorizontal();
    }

    /**
     * @return true if the robot finished the intake of a sample/specimen from the floor
     */
    public static boolean didIntake(){
        return Differential.isReseted();
    }

    /**
     * @return true if the robot is automating
     */
    public static boolean notAutomating(){
        return !(automated_reset || automated_intake || automated_high_basket_deposit);
    }

    /**
     * Display whatever is needed on telemetry
     */
    public static void displayTelemetry() {
        opMode.telemetry.addData("Lift reset", Lift.isReseted());
        opMode.telemetry.addData("Lift target pos", Lift.getTargetPosCm());
        opMode.telemetry.addData("Lift current length", Lift.getCurrentLength());
        opMode.telemetry.addData("heading", Drivetrain.getRobotHeading());

        opMode.telemetry.update();
    }

    /**
     * Keep track of a specific amount of time
     * @param durationMilliseconds the amount of time in milliseconds
     * @return true when the specified time has elapsed
     */
    public static boolean hasElapsed(int durationMilliseconds){
        return timer.hasElapsed(durationMilliseconds);
    }

    /**
     * Getter Methods
     */
    public static int getIntakeDuration(){
        return INTAKE_DURATION;
    }
}
