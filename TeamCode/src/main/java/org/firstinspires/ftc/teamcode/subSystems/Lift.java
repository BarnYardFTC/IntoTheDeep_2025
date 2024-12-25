package org.firstinspires.ftc.teamcode.subSystems;

// Imports.

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.modules.LiftProps;

public class Lift {
    public static final DcMotorEx[] motors = new DcMotorEx[2];
    private static final int RIGHT = 0;
    private static final int LEFT = 1;

    private static final LiftProps RIGHT_MOTOR = new LiftProps(); // Right's motor props.
    private static final LiftProps LEFT_MOTOR = new LiftProps(); // Left's motor props.

//    private static final double HIGH_CHAMBER_POS = RIGHT_MOTOR.getCmToEncoders(66);
//    private static final double HIGH_BASKET_POS = RIGHT_MOTOR.getCmToEncoders(109.2);
//    private static final double LOW_BASKET_POS = RIGHT_MOTOR.getCmToEncoders(65.4);

    private static final double HIGH_CHAMBER_POS = 0;
    private static final double HIGH_BASKET_POS = 0;
    private static final double LOW_BASKET_POS = 0;

    //ToDo: set correct values.
    private static final double p = 0;
    private static final double i = 0;
    private static final double d = 0;
    private static final double f = 0;
    public static double targetPosCm; // Target position of the lift in cm.
    private static PIDController controller; // PID controller.
    private static int targetPos; // Target position of the right motor.

    public Lift(OpMode opMode) {
        motors[RIGHT] = opMode.hardwareMap.get(DcMotorEx.class, "rightLift");
        motors[LEFT] = opMode.hardwareMap.get(DcMotorEx.class, "leftLift");

        motors[LEFT].setDirection(DcMotorSimple.Direction.REVERSE);

        for (DcMotorEx motor : motors) {
            motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        }

        controller = new PIDController(p, i, d);
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

    public static void liftPID() {
        controller.setPID(p, i, d);

        // Sets the current and target position of the motor.
        int currentPos = (Math.abs(motors[RIGHT].getCurrentPosition()) + Math.abs(motors[LEFT].getCurrentPosition())) / 2;
//        targetPos = (int) RIGHT_MOTOR.getCmToEncoders(targetPosCm);

        // Calculate PIDF values.
        double pid = controller.calculate(currentPos, targetPos);
        double ff = Math.cos(LiftArm.getTargetAngle() - 90) * f;

        // Calculate motor power.
        double power = pid + ff;

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



    public static void move(Pos pos) {
        switch (pos) {
            case HIGH_CHAMBER:
                targetPos = (int) HIGH_CHAMBER_POS;
                break;
            case HIGH_BASKET:
                targetPos = (int) HIGH_BASKET_POS;
                break;
            case LOW_BASKET:
                targetPos = (int) LOW_BASKET_POS;
                break;
            case RESET:
                targetPos = 0;
                break;
        }
    }

    public enum Pos {
        HIGH_CHAMBER, HIGH_BASKET, LOW_BASKET, RESET
    }
}
