package org.firstinspires.ftc.teamcode.subSystems;

// Imports.

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.modules.LiftProps;

@Config
public class Lift {
    public static final DcMotorEx[] motors = new DcMotorEx[2];
    public static final double LOW_CHAMBER_POS = 0;
    public static final int LIFT_SPEED = 2;
    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final LiftProps RIGHT_MOTOR = new LiftProps(8, 4, 537.7, 1.4, 14); // Right's motor props.
    private static final LiftProps LEFT_MOTOR = new LiftProps(8, 4, 537.7, 1.4, 14); // Left's motor props.
    private static final double ROBOT_LIFT_HEIGHT = 50;
    public static final double HIGH_CHAMBER_POS = 66 - ROBOT_LIFT_HEIGHT;
    public static final double LOW_BASKET_POS = 67.4 - ROBOT_LIFT_HEIGHT;

    public static double SAMPLE_COLLECTION_POS = 10;

    public static final double ACCEPTED_RESETED_POSITION = 3;

    public static int LIFT_MOVEMENT_DURATION = 2000;

    // Lift limits
    private static final double HORIZONTAL_LIMIT = 44;
    private static final double VERTICAL_LIMIT = 71.5;

    public static double HIGH_BASKET_GOAL_POS = 68;
    public static double HIGH_BASKET_POS = 58;
    public static double HIGH_BASKET_ACCEPTED_POS = 58;

    public static double HIGH_BASKET_CURRENT_LENGTH_MIN = 48;

    public static double LIFT_HARD_RESET_POWER = 1;
    public static int LIFT_HARD_RESET_DURATION = 800;
    public static int LIFT_RESET_TIME_INTERVALS = 700;

    public static int LIFT_PREPARE_SPECIMEN = 20;
    public static boolean timer_finished_flag = false;

    public static int DIFFERENTIAL_MOVEABLE_POS = 5;
    public static int DISABLE_DIFEERENTIAL_LIFT_POS = 10;

    public static double p = 0.0075;
    public static double i = 0;
    public static double d = 0;
    public static double targetPosCm; // Target position of the lift in cm.
    public static int targetPos; // Target position of the right motor.
    private static PIDController controller; // PID controller.

    public static boolean pid_on;

    public static void initialize(OpMode opMode) {
        motors[RIGHT] = opMode.hardwareMap.get(DcMotorEx.class, "rightLift");
        motors[LEFT] = opMode.hardwareMap.get(DcMotorEx.class, "leftLift");

        motors[LEFT].setDirection(DcMotorSimple.Direction.REVERSE);

        for (DcMotorEx motor : motors) {
            motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        }

        controller = new PIDController(p, i, d);
        move(Pos.RESET);

        pid_on = true;
    }
    public static void resetEncoders(){
        for (DcMotorEx motor : motors) {
            motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    public static double getTargetPosCm() {
        return targetPosCm;
    }

    public static void setTargetPosCm(double targetPosCm) {
        Lift.targetPosCm = targetPosCm;
    }

    public static int getTargetPos() {
        return targetPos;
    }

    public static void setTargetPos(int targetPos) {
        Lift.targetPos = targetPos;
    }

    public static boolean isReseted() {
        return getCurrentLength() < ACCEPTED_RESETED_POSITION;
    }

    public static boolean isDifferentialMoveable(){
        return getCurrentLength() >= DIFFERENTIAL_MOVEABLE_POS;
    }
    public static boolean disableDifferentialLiftPos(){
        return getCurrentLength() <= DISABLE_DIFEERENTIAL_LIFT_POS;
    }

    public static boolean isHighBasket() {
        return getCurrentLength() > HIGH_BASKET_CURRENT_LENGTH_MIN;
    }

    public static double currentPos = 0;
    public static void PID() {
        controller.setPID(p, i, d);

        // Sets the current and target position of the motor.
        currentPos = motors[RIGHT].getCurrentPosition();
        targetPos = (int) RIGHT_MOTOR.getCmToEncoders(targetPosCm);

        // Calculate motor power.
        double power = controller.calculate(currentPos, targetPos);

        if (pid_on){
            motors[RIGHT].setPower(power);
            motors[LEFT].setPower(power);
        }
    }

    public static void disablePID(){
        pid_on = false;
        targetPosCm = 0;
    }
    public static void enablePID(){
        pid_on = true;
    }
    public static boolean isPIDEnabled(){
        return pid_on;
    }

    public static DcMotorEx getRightMotor() {
        return motors[RIGHT];
    }

    public static DcMotorEx getLeftMotor() {
        return motors[LEFT];
    }

    public static double getCurrentLength() {
        return Math.abs(motors[RIGHT].getCurrentPosition()) / RIGHT_MOTOR.getENCODERS_PER_CM();
    }

    public static void move(Pos pos) {
        switch (pos) {
            case HIGH_BASKET:
                targetPosCm = HIGH_BASKET_POS;
                break;
            case HIGH_BASKET_GOAL:
                targetPosCm = HIGH_BASKET_GOAL_POS;
                break;
            case LOW_BASKET:
                targetPosCm = LOW_BASKET_POS;
                break;
            case SAMPLE_COLLECTION:
                targetPosCm = SAMPLE_COLLECTION_POS;
            case RESET:
                targetPosCm = 0;
                break;
            case PREPARE_SPECIMEN:
                targetPosCm = LIFT_PREPARE_SPECIMEN;
                break;
        }
    }

    public static void move(double direction) {
        targetPosCm += LIFT_SPEED * direction;
    }

    public static boolean isMoveable(double direction) {
        if (!Differential.isDefault() && LiftArm.isHorizontal() && direction < 0 && disableDifferentialLiftPos()){
            return false;
        }
        return (LiftArm.isHorizontal() &&
                targetPosCm + LIFT_SPEED * direction <= HORIZONTAL_LIMIT
                &&  targetPosCm + LIFT_SPEED * direction >= 0
        ) ||
                (LiftArm.isVertical() &&
                        targetPosCm + LIFT_SPEED * direction <= VERTICAL_LIMIT
                        && targetPosCm + LIFT_SPEED * direction >= 0
                );
    }

    public enum Pos {
        PREPARE_SPECIMEN, HIGH_BASKET, HIGH_BASKET_GOAL, LOW_BASKET, RESET, SAMPLE_COLLECTION
    }

    public static boolean arrivedTargetPos() {
        return getCurrentLength() <= targetPosCm + LIFT_SPEED && getCurrentLength() >= targetPosCm - LIFT_SPEED;
    }


    /**
     * Autonomous Actions - Actions which can be used in the autonomous programs.
     */

    private static class LiftPID implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            PID();
            return true;
        }
    }
    public static Action liftPID(){
        return new LiftPID();
    }

    public static class LiftHardReset implements Action {
        private final TimerHelper timerHelper;

        public LiftHardReset(){
            this.timerHelper = new TimerHelper();
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            disablePID();
            Lift.getRightMotor().setPower(-LIFT_HARD_RESET_POWER);
            Lift.getLeftMotor().setPower(-LIFT_HARD_RESET_POWER);
            if (timerHelper.hasElapsed(LIFT_HARD_RESET_DURATION) || pid_on) {
                timer_finished_flag = true;
                Lift.getRightMotor().setPower(0);
                Lift.getLeftMotor().setPower(0);
                enablePID();
                return false;
            }
            return true;
        }
    }
    private static Action liftHardReset(){
        return new LiftHardReset();
    }
    public static Action hardReset(){
        return new SequentialAction(
                liftHardReset(),
                liftResetEncoders(),
                Robot.hasElapsed(LIFT_RESET_TIME_INTERVALS),
                liftResetEncoders(),
                Robot.hasElapsed(LIFT_RESET_TIME_INTERVALS),
                liftResetEncoders(),
                Robot.hasElapsed(LIFT_RESET_TIME_INTERVALS),
                liftResetEncoders()

        );
    }


    public static class LiftHighBasketGoal implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            move(Pos.HIGH_BASKET_GOAL);
            return !(getCurrentLength() > HIGH_BASKET_ACCEPTED_POS);
        }
    }
    public static Action liftHighBasketGoal(){
        return new LiftHighBasketGoal();
    }
    public static class LiftHighBasket implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            move(Pos.HIGH_BASKET);
            return false;
        }
    }
    public static Action liftHighBasket(){
        return new LiftHighBasket();
    }
    public static class LiftLowBasket implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            move(Pos.LOW_BASKET);
            return !arrivedTargetPos();
        }
    }
    public static Action liftSampleCollection(){
        return new LiftSampleCollection();
    }
    public static class LiftSampleCollection implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            move(Pos.SAMPLE_COLLECTION);
            return !arrivedTargetPos();
        }
    }
    public static class LiftPrepareSpecimen implements Action {
        private final TimerHelper timerHelper;

        public LiftPrepareSpecimen(){
            timerHelper = new TimerHelper();
        }
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            move(Pos.PREPARE_SPECIMEN);
            return !arrivedTargetPos() && !timerHelper.hasElapsed(LIFT_MOVEMENT_DURATION);
        }
    }
    public static class LiftReset implements Action {
        private final TimerHelper moveTimer;

        public LiftReset() {
            moveTimer = new TimerHelper(); // Always create a new TimerHelper
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            move(Pos.RESET);
            return !isReseted() && !moveTimer.hasElapsed(LIFT_MOVEMENT_DURATION);
        }
    }


    public static Action moveLift(Lift.Pos pos){
        if (pos == Pos.PREPARE_SPECIMEN){
            return new LiftPrepareSpecimen();
        }
        else if (pos == Pos.RESET){
            return new LiftReset();
        }
        else if (pos == Pos.LOW_BASKET){
            return new LiftLowBasket();
        }
        else if (pos == Pos.HIGH_BASKET){
            return new SequentialAction(
                    liftHighBasketGoal(),
                    liftHighBasket()
            );
        }
        else if (pos == Pos.SAMPLE_COLLECTION){
            return new LiftSampleCollection();
        }
        else {
            return new LiftPID();
        }
    }

    private static class LiftResetEncoders implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            resetEncoders();
            return false;
        }
    }
    public static Action liftResetEncoders(){
        return new LiftResetEncoders();
    }
}