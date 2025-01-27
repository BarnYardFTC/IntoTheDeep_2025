package org.firstinspires.ftc.teamcode.subSystems;

// Imports.

import com.acmerobotics.dashboard.config.Config;
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
    public static final double POST_SCORE_HIGH_CHAMBER_POS = 76 - ROBOT_LIFT_HEIGHT;
    // Lift limits
    private static final double HORIZONTAL_LIMIT = 44;
    private static final double VERTICAL_LIMIT = 72;
    public static final double HIGH_BASKET_POS = 66;
    //ToDo: set correct values.
    public static double p = 0.0085;
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
        return (motors[RIGHT].getCurrentPosition() / RIGHT_MOTOR.getENCODERS_PER_CM()) < 4;
    }

    public static void liftPID() {
        controller.setPID(p, i, d);

        // Sets the current and target position of the motor.
        int currentPos = motors[RIGHT].getCurrentPosition();
        targetPos = (int) RIGHT_MOTOR.getCmToEncoders(targetPosCm);

        // Calculate motor power.
        double power = controller.calculate(currentPos, targetPos);

        // Giving power to motors.
        motors[RIGHT].setPower(power);
        motors[LEFT].setPower(power);
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
            case POST_SCORE_HIGH_CHAMBER:
                targetPosCm = POST_SCORE_HIGH_CHAMBER_POS;
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
            case RESET:
                targetPosCm = 0;
                break;
        }
    }

    public static void move(double direction) {
        targetPosCm += LIFT_SPEED * direction;
    }

    public static boolean isMoveable(double direction) {
        return (LiftArm.isHorizontal() && targetPosCm + LIFT_SPEED * direction <= HORIZONTAL_LIMIT && targetPosCm + LIFT_SPEED * direction >= 0) || (!LiftArm.isHorizontal() && targetPosCm + LIFT_SPEED * direction <= VERTICAL_LIMIT && targetPosCm + LIFT_SPEED * direction >= 0);
    }

    public enum Pos {
        HIGH_CHAMBER, POST_SCORE_HIGH_CHAMBER, LOW_CHAMBER, HIGH_BASKET, LOW_BASKET, RESET
    }

    public static boolean arrivedTargetPos() {
        return getCurrentLength() <= targetPosCm + 2 && getCurrentLength() >= targetPosCm - 2;
    }
}
