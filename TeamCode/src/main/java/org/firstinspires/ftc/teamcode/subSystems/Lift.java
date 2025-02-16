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
    public static final double SPECIMEN_SCORE_POS = 66 - ROBOT_LIFT_HEIGHT;
    public static double SAMPLE_COLLECTION_POS = 6; // ToDo: Find value for autonomous
    public static final double ACCEPTED_RESETED_POSITION = 4;

    // Lift limits
    private static final double HORIZONTAL_LIMIT = 44;
    private static final double VERTICAL_LIMIT = 71.5;

    public static double HIGH_BASKET_GOAL_POS = 58;
    public static double HIGH_BASKET_POS = 48;
    public static double HIGH_BASKET_ACCEPTED_POS = 50;

    public static double p = 0.005;
    public static double i = 0;
    public static double d = 0;
    public static double targetPosCm; // Target position of the lift in cm.
    public static int targetPos; // Target position of the right motor.
    private static PIDController controller; // PID controller.

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

//    public static boolean manual_power_requested = false;
//    public static double manual_power = 0.05;
    public static void PID() {
        controller.setPID(p, i, d);

        // Sets the current and target position of the motor.
        int currentPos = motors[RIGHT].getCurrentPosition();
        targetPos = (int) RIGHT_MOTOR.getCmToEncoders(targetPosCm);

        // Calculate motor power.
        double power = controller.calculate(currentPos, targetPos);

        // Giving power to motors.
//        if (manual_power_requested){
//            motors[RIGHT].setPower(manual_power);
//            motors[LEFT].setPower(manual_power);
//        }
//        else{
            motors[RIGHT].setPower(power);
            motors[LEFT].setPower(power);
//        }

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
            case HIGH_CHAMBER:
                targetPosCm = HIGH_CHAMBER_POS;
                break;
            case SPECIMEN_SCORE:
                targetPosCm = SPECIMEN_SCORE_POS;
                break;
            case LOW_CHAMBER:
                targetPosCm = LOW_CHAMBER_POS;
                break;
            case HIGH_BASKET:
                targetPosCm = HIGH_BASKET_POS;
                break;
            case LOW_BASKET:
                targetPosCm = LOW_BASKET_POS;
                break;
            case SAMPLE_COLLECTION:
                targetPosCm = SAMPLE_COLLECTION_POS;
            case RESET:
                targetPosCm = 0;
                break;
        }
    }

    public static void move(double direction) {
        targetPosCm += LIFT_SPEED * direction;
    }

    public static boolean isMoveable(double direction) {
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
        HIGH_CHAMBER, SPECIMEN_SCORE, LOW_CHAMBER, HIGH_BASKET, HIGH_BASKET_GOAL, LOW_BASKET, RESET, SAMPLE_COLLECTION
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

    public static class LiftHighChamber implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            move(Pos.HIGH_CHAMBER);
            return !arrivedTargetPos();
        }
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
    public static class LiftSampleCollection implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            move(Lift.Pos.LOW_BASKET);
            return !arrivedTargetPos();
        }
    }
    public static class LiftSpecimenScore implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            move(Pos.SPECIMEN_SCORE);
            return !arrivedTargetPos();
        }
    }
    public static class LiftReset implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            move(Pos.RESET);
            return !isReseted();
        }
    }
    public static Action moveLift(Lift.Pos pos){
        if (pos == Pos.HIGH_CHAMBER){
            return new LiftHighChamber();
        }
        else if (pos == Pos.SPECIMEN_SCORE){
            return new LiftSpecimenScore();
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
        return new LiftReset();
    }
}