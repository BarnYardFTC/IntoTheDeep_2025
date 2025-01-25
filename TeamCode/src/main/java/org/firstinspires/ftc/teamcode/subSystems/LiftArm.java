package org.firstinspires.ftc.teamcode.subSystems;

import com.acmerobotics.dashboard.config.Config;
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

    private static final int VERTICAL_POS = 135; // Angle for moving the lift arm to a vertical position.
    private static final int HORIZONTAL_POS_START = 60;
    private static final int HORIZONTAL_POS_END = 5; // Angle for moving the lift arm to a horizontal position.
    private static final double MIN_LIFT_LENGTH = 30;

    private static final int ACCEPTED_VERTICAL_ANGLE = 130;
    private static final int ACCEPTED_HORIZONTAL_ANGLE = 20;

    //ToDo: set correct values.
    public static double p = 0.02;
    public static double i = 0;
    public static double d = 0;
    public static double f = 0.24;
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

        move(Angle.HORIZONTAL_END);
    }

    public static int getTargetPos() {
        return targetPos;
    }

    public static void setTargetPos(int targetPos) {
        LiftArm.targetPos = targetPos;
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

    public static double getCurrentAngle() {
        return Math.abs(motors[RIGHT].getCurrentPosition()) / RIGHT_MOTOR.getENCODERS_PER_DEGREE();
    }

    public static void liftArmPID() {
        controller.setPID(p, i, d);

        // Sets the current and target position of the motor.
        int currentPos = motors[RIGHT].getCurrentPosition();
        targetPos = RIGHT_MOTOR.getAngleToEncoder(targetAngle);

        // Calculate PIDF values.
        double pid = controller.calculate(currentPos, targetPos);
        double ff;
        if (targetAngle > 90) {
            if (Lift.getTargetPosCm() == Lift.HIGH_BASKET_POS) {
                ff = Math.cos(Math.toRadians(75)) * f * (MIN_LIFT_LENGTH + Lift.getTargetPosCm()) / (MIN_LIFT_LENGTH) * 1.35;
            } else {
                ff = Math.cos(Math.toRadians(75)) * f * (MIN_LIFT_LENGTH + Lift.getTargetPosCm()) / (MIN_LIFT_LENGTH);
            }
        } else {
            ff = Math.cos(Math.toRadians(targetAngle)) * f * (MIN_LIFT_LENGTH + Lift.getTargetPosCm()) / (MIN_LIFT_LENGTH);
        }

        double power;

        if (isArmInPos() && targetAngle == HORIZONTAL_POS_START) {
            move(Angle.HORIZONTAL_END);
        }

        if (!isArmInPos() && targetAngle == HORIZONTAL_POS_END) {
            // Calculate motor power.
            power = ff * 0.6;
        }
        else {
            // Calculate motor power.
            power = pid + ff;
        }

        // Giving power to motors.
        motors[RIGHT].setPower(power);
        motors[LEFT].setPower(power);
    }

    public static void move(Angle angle) {
        switch (angle) {
            case VERTICAL:
                targetAngle = VERTICAL_POS;
                break;
            case HORIZONTAL:
                targetAngle = HORIZONTAL_POS_START;
                break;
            case HORIZONTAL_END:
                targetAngle = HORIZONTAL_POS_END;
                break;
        }
    }

    public enum Angle {
        VERTICAL, HORIZONTAL, HORIZONTAL_END
    }

    public static boolean isArmInPos() {
        return getCurrentAngle() <= targetAngle + 5 && getCurrentAngle() >= targetAngle - 5;
    }
}
