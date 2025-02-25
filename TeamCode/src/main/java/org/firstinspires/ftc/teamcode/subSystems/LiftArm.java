package org.firstinspires.ftc.teamcode.subSystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.modules.MotorProps;

@Config
public class LiftArm {
    public static final DcMotorEx[] motors = new DcMotorEx[2]; // Motors array.
    public static final MotorProps RIGHT_MOTOR = new MotorProps(1425.1, 1.4); // Right's motor props.
    private static final int RIGHT = 0; // Right's motor index.
    private static final int LEFT = 1; // Left's motor index.
    private static final MotorProps LEFT_MOTOR = new MotorProps(1425.1, 1.4); // Left's motor props.

    private static final int VERTICAL_ANGLE = 120; // Angle for moving the lift arm to a vertical position.
    private static final int HORIZONTAL_ANGLE = 0;
    private static final double MIN_LIFT_LENGTH = 30;

    private static final int ACCEPTED_VERTICAL_ANGLE = 110;
    private static final int ACCEPTED_HORIZONTAL_ANGLE = 20;

    private static final int POWER_OFF_HORIZONTAL_ANGLE = 20;

    public static int LIFT_ARM_VERTICAL_SETTLE_TIME = 500;
    public static int LIFT_ARM_HORIZONTAL_SETTLE_TIME = 800;

    public static boolean PIDOn = true;

    public static int lengthOfLiftForPIEDChang = 40;

    //ToDo: set correct values.
    public static double p = 0.027;
    public static double i = 0;
    public static double d = 0;
    public static double f = 0.16;
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

    public static void resetEncoders(){
        for (DcMotorEx motor : motors) {
            motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        }
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
    public static boolean isPowerRequired() {
        return !(getCurrentAngle() < POWER_OFF_HORIZONTAL_ANGLE && getTargetAngle() == HORIZONTAL_ANGLE) || Robot.is_reset_automating;
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

//    public static boolean manual_power_requested = false;
//    public static double manual_power = 0.4;
//
//    public static double LIFT_ARM_UP_POWER = 0.4;
//    public static double lift_arm_maintain = 0.4/55;

    public static double pid = 0;
    public static double ff = 0;
    public static void PID() {
        controller.setPID(p, i, d);

        // Sets the current and target position of the motor.
        int currentPos = motors[RIGHT].getCurrentPosition();
        targetPos = RIGHT_MOTOR.getAngleToEncoder(targetAngle);

        // Calculate PIDF values.
        pid = controller.calculate(currentPos, targetPos);
        double power;
        ff = f * (MIN_LIFT_LENGTH + Lift.getTargetPosCm()) / (MIN_LIFT_LENGTH);
        power = pid + Math.cos(Math.toRadians(targetAngle)) * ff;

        // Giving power to motors.
        if (isPowerRequired()){
            motors[RIGHT].setPower(power);
            motors[LEFT].setPower(power);
        }
        else {
            motors[RIGHT].setPower(0);
            motors[LEFT].setPower(0);
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
            Differential.reset();
            return !isHorizontal();
        }
    }
    public static Action liftArmHorizontal(){
        return new LiftArmHorizontal();
    }

    public static class LiftArmPID implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (LiftArm.PIDOn) {
                LiftArm.PID();
            }
            return true;
        }
    }
    public static Action liftArmPID(){
        return new LiftArmPID();
    }

    public static class LiftArmResetEncoders implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            resetEncoders();
            return false;
        }
    }
    public static Action liftArmResetEncoders(){
        return new LiftArmResetEncoders();
    }
}