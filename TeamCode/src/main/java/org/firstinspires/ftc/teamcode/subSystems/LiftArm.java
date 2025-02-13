package org.firstinspires.ftc.teamcode.subSystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.modules.MotorProps;

@Config
public class LiftArm {
    public static final DcMotorEx[] motors = new DcMotorEx[2]; // Motors array.
    public static final MotorProps RIGHT_MOTOR = new MotorProps(1425.1, 1); // Right's motor props.
    private static final int RIGHT = 0; // Right's motor index.
    private static final int LEFT = 1; // Left's motor index.
    private static final MotorProps LEFT_MOTOR = new MotorProps(1425.1, 1); // Left's motor props.

    private static final int VERTICAL_ANGLE = 135; // Angle for moving the lift arm to a vertical position.
    private static final int HORIZONTAL_ANGLE = 15;
    private static final double MIN_LIFT_LENGTH = 30;

    private static final int ACCEPTED_VERTICAL_ANGLE = 100;
    private static final int ACCEPTED_HORIZONTAL_ANGLE = 20;

    //ToDo: set correct values.
    public static double p = 0.02;
    public static double i = 0;
    public static double d = 0;
    public static double f = 0.55;
    public static int targetAngle; // Target angle of the arm.
    private static PIDController controller; // PID controller.
    private static int targetPos; // Target position of the right motor.

    public static void initialize(OpMode opMode) {
        motors[RIGHT] = opMode.hardwareMap.get(DcMotorEx.class, "rightArm");
        motors[LEFT] = opMode.hardwareMap.get(DcMotorEx.class, "leftArm");

        motors[RIGHT].setDirection(DcMotorEx.Direction.REVERSE);

        // Setting motors attributes
        for (DcMotorEx motor : motors) {
            motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        }

        controller = new PIDController(p, i, d);

        move(Angle.HORIZONTAL);
    }

    public static int getTargetPos() {
        return targetPos;
    }

    public static void setTargetPos(int targetPos) {
        LiftArm.targetPos = targetPos;
    }

    public static int getVerticalAngle() {
        return VERTICAL_ANGLE;
    }
    public static int getHorizontalAngle() {
        return HORIZONTAL_ANGLE;
    }
    /**
     * Informs if the arm is horizontal.
     *
     * @return - If the current arm's position is horizontal.
     */
    public static boolean isHorizontal() {
        return getCurrentAngle() < ACCEPTED_HORIZONTAL_ANGLE;
    }
    public static boolean isVertical() {
        return getCurrentAngle() > ACCEPTED_VERTICAL_ANGLE;
    }

    public static int getTargetAngle() {
        return targetAngle;
    }

    public static DcMotorEx getRightMotor() {
        return motors[RIGHT];
    }

    public static DcMotorEx getLeftMotor() {
        return motors[LEFT];
    }

    public static int getCurrentPosition(){
        return motors[RIGHT].getCurrentPosition();
    }

    public static double getCurrentAngle() {
        return getCurrentPosition() / RIGHT_MOTOR.getENCODERS_PER_DEGREE();
    }

    public static void PID() {
        controller.setPID(p, i, d);

        // Sets the current and target position of the motor.
        int currentPos = motors[RIGHT].getCurrentPosition();
        targetPos = RIGHT_MOTOR.getAngleToEncoder(targetAngle);

        // Calculate PIDF values.
        double pid = controller.calculate(currentPos, targetPos);
        double ff;
        double power;
        if (targetAngle == VERTICAL_ANGLE) {
            if (Lift.getTargetPosCm() == Lift.HIGH_BASKET_POS) {
                ff = Math.cos(Math.toRadians(15)) * f * (MIN_LIFT_LENGTH + Lift.getTargetPosCm()) / (MIN_LIFT_LENGTH);
                power = pid + ff;
            }
            else {
                ff = Math.cos(Math.toRadians(80)) * f * (MIN_LIFT_LENGTH + Lift.getTargetPosCm()) / (MIN_LIFT_LENGTH);
                power = pid + ff;
            }
        }
        else {
            ff = Math.cos(Math.toRadians(targetAngle)) * f * (MIN_LIFT_LENGTH + Lift.getTargetPosCm()) / (MIN_LIFT_LENGTH);
            power = pid + ff;
        }

        // Giving power to motors.
        if (getTargetAngle() == getHorizontalAngle() && isHorizontal()){
            motors[RIGHT].setPower(0);
            motors[LEFT].setPower(0);
        }
        else {
            motors[RIGHT].setPower(power);
            motors[LEFT].setPower(power);
        }
    }

    public static void move(Angle angle) {
        switch (angle) {
            case VERTICAL:
                targetAngle = VERTICAL_ANGLE;
                break;
            case HORIZONTAL:
                targetAngle = HORIZONTAL_ANGLE;
                break;
        }
    }

    public enum Angle {
        VERTICAL, HORIZONTAL
    }

    public static boolean isArmInPos() {
        return getCurrentAngle() <= targetAngle + 10 && getCurrentAngle() >= targetAngle - 10;
    }


    /**
     * Autonomous Actions - Actions which can be used in the autonomous programs.
     */

    public static class LiftArmVertical implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            move(LiftArm.Angle.VERTICAL);
            return !isVertical();
        }
    }
    public static Action liftArmVertical(){
        return new LiftArmVertical();
    }

    public static class LiftArmHorizontal implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            move(LiftArm.Angle.HORIZONTAL);
            return !isHorizontal();
        }
    }
    public static Action liftArmHorizontal(){
        return new LiftArmHorizontal();
    }

    public static class LiftArmPID implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            LiftArm.PID();
            return true;
        }
    }
    public static Action liftArmPID(){
        return new LiftArmPID();
    }
}